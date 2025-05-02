import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

//Clase que modela la lectura de archivos enfocada en arreglos
//que estaran almacenado en un HashMap, con su contenido e indice, ademas,
//el arrayList es para cada arreglo
public class LecturaArchivos {

    //Atributo que almacenara todo el contenido del archivo csv
    private ArrayList<HashMap<Integer, Object>> filas;
    private long lineas;
    private Arrays array;

    /**
     * Constructor preterminado donde inicializa el ArrayList
     */
    public LecturaArchivos()
    {
        filas = new ArrayList<>();
        lineas = 0;
    }

    /**
     * Metodo que permite leer un archivo CSV enfocado a weatherHistory
     * @param ruta
     */
    public void leer(String ruta)
    {
        //Se uso try - catch para manejar posibles errores
        try {

            //Se declara una linea para poder leer cada fila
            String linea;
            //Se crea el vinculo al archivo para poder leerlo
            BufferedReader read = new BufferedReader(new FileReader(ruta));

            //Mientas que sigan habiendo lineas que leer seguira el ciclo
            while ((linea = read.readLine()) != null) {

                //Se separa el contenido almacenado con comas
                String[] contenido = linea.split(",");

                //Se agrega un HashMap donde se guardar el contenido
                filas.add(new HashMap<>());

                //Se guarda todo el contenido
                for (int i = 0; i < contenido.length; i++) {

                    filas.getLast().put(i, contenido[i]);
                }
                ++lineas;
            }

            read.close();
        } catch (FileNotFoundException exception) {
            System.err.println("No se encontro el archivo");
        } catch (IOException exception) {
            System.err.println("Error al leer el archivo");
        }
    }

    /**
     * Metodo que muestra todo el contenido del archivo
     */
    public void mostrarContenido()
    {
        long contador = 0;
        if (!filas.isEmpty()) {
            for (HashMap<Integer, Object> fila : filas) {
                for (Object values : fila.values()) {
                    System.out.print(values + ", ");

                }
                ++contador;
                System.out.println();
            }
        }
        System.out.println("Filas obtenidas: " + contador + " Filas reales : " + lineas);
    }

    /**
     * Metodo que muestra todo el contenido de una fila especificada
     * @param indice
     */
    public void mostrarFila(int indice)
    {
        long contador = 0;
        HashMap<Integer, Object> fila = filas.get(indice);
        if (!fila.isEmpty()) {
            for (Object value : fila.values()) {
                System.out.print(value + ", ");
            }
            ++contador;
        }
        System.out.println("Filas obtenidas: " + contador + " Filas reales : " + lineas);
    }

    /**
     * Metodo que muestra todo el contenido de una columna especificada
     * @param indice
     */
    public void mostrarColumna(int indice)
    {
        long contador = 0;
        if (!filas.isEmpty()) {
            for (HashMap<Integer, Object> fila : filas) {
                if (fila.containsKey(indice)) {
                    System.out.println(fila.get(indice));
                    ++contador;
                }
            }
        }
        System.out.println("Filas obtenidas: " + contador + " Filas reales : " + lineas);
    }

    /**
     * Metodo que regresa todo el contenido del archivo csv
     * @return
     */
    public ArrayList<HashMap<Integer, Object>> regresar()
    {
        if (!filas.isEmpty()) {
            return filas;
        }

        return null;
    }

    /**
     * Metodo que regresa todo el contenido de una fila del archivo csv
     * @return
     */
    public HashMap<Integer, Object> regresarFila(int indice)
    {
        return filas.get(indice);
    }

    /**
     * Metodo que regresa todo el contenido de una columna del archivo csv
     * @param indice
     * @return
     */
    public ArrayList<Object> regresarColumna(int indice)
    {
        ArrayList<Object> columna = null;
        
        return columna;
    }

}
