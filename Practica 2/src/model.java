
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.jacop.core.BooleanVar;
import org.jacop.core.Store;
import org.jacop.jasat.utils.structures.IntVec;
import org.jacop.satwrapper.SatWrapper;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;
import org.jacop.search.SmallestDomain;

public class model{

	
	public static void main(String args[]){
		
		
		 Store store = new Store();
		  SatWrapper satWrapper = new SatWrapper();
		  store.impose(satWrapper);					/* Importante: sat problem */
		  
		  laberinto miLaberinto= new laberinto(7,11); /* Creación del objeto laberinto */
		  char mapa[][]=miLaberinto.mapa;
		  int numFilas=miLaberinto.numFilas();
		  int numColumnas=miLaberinto.numColumnas();
		  mapa=miLaberinto.cargarMapa();
		 
		  
		  
		  
		  
		  
		  // 1. DECLARACION DE VARIABLES

		  BooleanVar[][] A = new BooleanVar[miLaberinto.filas][miLaberinto.columnas];
		  BooleanVar[][] S = new BooleanVar[miLaberinto.filas][miLaberinto.columnas];
		  
		  
		  int [][] ALiteral = new int [miLaberinto.filas][miLaberinto.columnas];
		  int [][] SLiteral = new int [miLaberinto.filas][miLaberinto.columnas];
		  
		  int contadorVariables=0;
		  for(int i=0; i<numFilas;i++){
			    for(int j=0;j<numColumnas;j++){

			      if(mapa[i][j]==' ' ){
			    	
			    	A[i][j]=new BooleanVar(store,"\n Al");
			    	S[i][j]=new BooleanVar(store,"\n Snake");
			    	satWrapper.register(A[i][j]);
					satWrapper.register(S[i][j]);
					contadorVariables=contadorVariables+2;
					
					ALiteral[i][j] = satWrapper.cpVarToBoolVar(A[i][j], 1, true);
					SLiteral[i][j] = satWrapper.cpVarToBoolVar(S[i][j], 1, true);
					
			      }
			      
			   
			    }
			  }
		  
		
		  int contador=0;
		  BooleanVar[] allVariables = new BooleanVar[contadorVariables];
		  
		  for(int i=0; i<numFilas;i++){
			    for(int j=0;j<numColumnas;j++){
			    	
			    	if(A[i][j]!=null ) {
			    		allVariables[contador]= A[i][j];
				    	contador++;
			    	}
			    	
			    }
		  }
		 
		  
		  for(int i=0; i<numFilas;i++){
			    for(int j=0;j<numColumnas;j++){
			    	
			    	if(S[i][j]!=null) {
			    		allVariables[contador]= S[i][j];
				    	contador++;
			    	}
			    	
			    }
		  }
		  
		  
		  
		//Al o la serpiente sólo pueden estar en casillas vacías.
		 /* for(int i=0; i<numFilas;i++){
			    for(int j=0;j<numColumnas;j++){
			      if(mapa[i][j]==' ' ){
			    	  addClause(satWrapper,-ALiteral[i][j],-SLiteral[i][j]); //A->-S
			    	  addClause(satWrapper,-SLiteral[i][j],-ALiteral[i][j]); //S->-A
			    	 
			    	  
			      	}
			    }
		  }
		 
		
		  
		  //Las serpientes no pueden estar en la misma fila
		  for(int i=0; i<numFilas;i++){
			    for(int j=0;j<numColumnas ;j++){
			    	for(int k=0; k<numColumnas;k++){
			    		if(j!=k) {
			    		addClause(satWrapper,-SLiteral[i][j],-SLiteral[i][k]);
			    		
			    		
			    		}
			    		
			    	}
			    	
			    }
		  }
		  
		  
		  //Las serpientes no pueden estar en la misma fila o columna que Al
		  for(int i=0; i<numFilas;i++){
			    for(int j=0;j<numColumnas ;j++){
			    	for(int k=0; k<numColumnas;k++){
			    	for(int z=0; z<numFilas;z++) {
			    		if(j!=k || z!=i )
			    		addClause(satWrapper,-SLiteral[i][j],-ALiteral[z][k]);
			    		addClause(satWrapper,-ALiteral[i][j],-SLiteral[z][k]);
			    		
			    		}
			    	}
			    	
			    }
		  }*/		  
		 
		  //Solo puede haber un Al
		  for(int i=0; i<numFilas;i++){
			    for(int j=0;j<numColumnas ;j++){
			    	for(int k=0; k<numColumnas;k++){
			    	for(int z=0; z<numFilas;z++) {
			    		if(j!=k && z!=i ) { 
			    		addClause(satWrapper,-ALiteral[i][j],-ALiteral[z][k]);
			    		addClause(satWrapper,ALiteral[i][j],ALiteral[z][k]);
			    		}
			    	}
			    	}
			    	
			    }
		  }		  
		 
		  
		  
		  
		
			// 4. INVOCAR AL SOLUCIONADOR

		    Search<BooleanVar> search = new DepthFirstSearch<BooleanVar>();
			SelectChoicePoint<BooleanVar> select = new SimpleSelect<BooleanVar>(allVariables,new SmallestDomain<BooleanVar>(), new IndomainMin<BooleanVar>());
			Boolean result = search.labeling(store, select);

			
			if (result) {
				System.out.println("Solution: ");
				
				for(int i=0;i<numFilas;i++) {
					for(int j=0;j<numColumnas;j++) {
						
						if( A[i][j]!=null && A[i][j].value()==1) {
							
							mapa[i][j]='A';
							
						}
						if(A[i][j]!=null && S[i][j].value()==1) {
							mapa[i][j]='S';
						}
					}
				}
				
				for(int i=0;i<numFilas;i++) {
					for(int j=0;j<numColumnas;j++) {
						
						System.out.print(mapa[i][j]+ " ");
					}
					System.out.println();
				}
				
				
			} else{
				System.out.println("*** No solution");
			}

			System.out.println();
		}


		public static void addClause(SatWrapper satWrapper, int literal1, int literal2){
			IntVec clause = new IntVec(satWrapper.pool);
			clause.add(literal1);
			clause.add(literal2);
			satWrapper.addModelClause(clause.toArray());
		}


		public static void addClause(SatWrapper satWrapper, int literal1, int literal2, int literal3){
			IntVec clause = new IntVec(satWrapper.pool);
			clause.add(literal1);
			clause.add(literal2);
			clause.add(literal3);
			satWrapper.addModelClause(clause.toArray());
		}
		
		public static void addClause(SatWrapper satWrapper, int literal1){
			IntVec clause = new IntVec(satWrapper.pool);
			clause.add(literal1);
			satWrapper.addModelClause(clause.toArray());
		}
	


	}


	

 
