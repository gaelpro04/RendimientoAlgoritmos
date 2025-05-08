import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graficacion {

    public static JFreeChart graficar(double[] columna)
    {
        int cantidad = columna.length;
        int cantidadRangos = (int) Math.sqrt(cantidad);

        double max = columna[columna.length - 1];
        double min = columna[0];

        double rango = (max - min) / cantidadRangos;

        int[] frecuencias = new int[cantidadRangos];
        int indice = 0;
        double intervaloMenor = columna[0];
        boolean bandera = true;



        HistogramDataset dataSet = new HistogramDataset();
        dataSet.addSeries("Frecuencia", columna, 311);

        JFreeChart histograma = ChartFactory.createHistogram("Valores", "Valor", "Frecuencia",
                dataSet, PlotOrientation.VERTICAL, true, true, false);

        return histograma;
    }




}
