import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class laberinto {

	int filas;
	int columnas;
	char mapa[][];
	
	public laberinto(int filas,int columnas) {
		
		this.filas=filas;
		this.columnas=columnas;
		mapa=new char[filas][columnas];
	}
	
	public BufferedReader getBuffered(String link){

	    FileReader lector  = null;
	    BufferedReader br = null;
	    try {
	         File Arch=new File(link);
	        if(!Arch.exists()){
	           System.out.println("No existe el archivo");
	        }else{
	           lector = new FileReader(link);
	           br = new BufferedReader(lector);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return br;
	}
	
	public int numFilas() {
		try {
	        //ruta de tu archivo
	        String ruta = "src/lab1_parte1.lab";
	        BufferedReader br = getBuffered(ruta);
	        //leemos la primera linea
	        String linea =  br.readLine();
	        
	        //contador
	        int filas = 1;
	        while(linea != null){
	        	
	        	linea = br.readLine();
	            filas++;    
	        }
	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }
		return filas;
	}
	
	public int numColumnas() {
		 try {
		        //ruta de tu archivo
		        String ruta = "src/lab1_parte1.lab";
		        BufferedReader br = getBuffered(ruta);
		        //leemos la primera linea
		        String linea =  br.readLine();
		        
		        
		        String values[]= linea.split("");
		        int columnas= values.length;
		            
		            
		    } catch (IOException | NumberFormatException e) {
		        e.printStackTrace();
		    }
			return columnas;
		}
	
	
	public char[][] cargarMapa(){
	    try {
	        //ruta de tu archivo
	        String ruta = "src/lab1_parte1.lab";
	        BufferedReader br = getBuffered(ruta);
	        //leemos la primera linea
	        String linea =  br.readLine();
	        
	        //contador
	        int contador = 0;
	        String values[];
	        while(linea != null){
	            values = linea.split("");
	            //recorremos el arrar de string
	            for (int i = 0; i<values.length; i++) {
	                //se obtiene el primer caracter de el arreglo de strings
	                mapa[contador][i] = values[i].charAt(0);
	            }
	            contador++;
	            linea = br.readLine();
	        }
	    } catch (IOException | NumberFormatException e) {
	        e.printStackTrace();
	    }
		return mapa;
	}
}
