public class Main {

    public static void main(String[] args)
    {
        LecturaArchivos lectura = new LecturaArchivos();
        lectura.leer("C:\\Users\\sgsg_\\IdeaProjects\\RendimientoAlgoritmos\\archive\\weatherHistory.csv");
        lectura.mostrarFila(3);
    }
}
