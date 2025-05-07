import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

public class RendimientoAlgoritmos {

   private Ordenamientos algoritmos;
   private LecturaArchivos csv;
   private JFreeChart graficas;

   private JFrame frame;
   private JPanel panelTabla, panelBotones, panelInformacion, panelIzquierdo;
   private JComboBox<String> comboBoxAlgoritmos;
   private JButton botonOrdenar;
   private JLabel labelInformacion, labelEncabezado;

   public RendimientoAlgoritmos()
   {
      frame = new JFrame("Prueba de algoritmos");
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());

      panelTabla = new JPanel();
      panelTabla.setBackground(Color.GREEN);
      panelTabla.setPreferredSize(new Dimension(1100, 750));

      panelIzquierdo = new JPanel();
      panelIzquierdo.setBackground(Color.RED);
      panelIzquierdo.setPreferredSize(new Dimension(300, 750));
      panelIzquierdo.setLayout(new BorderLayout());

      panelBotones = new JPanel();
      panelBotones.setBackground(Color.BLUE);
      panelBotones.setPreferredSize(new Dimension(300,375));
      panelBotones.setLayout(new GridBagLayout());

      GridBagConstraints gbc = new GridBagConstraints();

      botonOrdenar = new JButton("Ordenar");
      botonOrdenar.addActionListener(action -> botonOrdenar());
      botonOrdenar.setPreferredSize(new Dimension(40,100));

      String[] elementos = {"quickSort", "mergeSort", "shellSort", "seleccion", "radixSort", "sort", "parallelSort"};
      comboBoxAlgoritmos = new JComboBox<>(elementos);

      panelBotones.add(botonOrdenar);
      panelBotones.add(comboBoxAlgoritmos);

      panelInformacion = new JPanel();
      panelInformacion.setBackground(Color.YELLOW);
      panelInformacion.setPreferredSize(new Dimension(300,350));


      panelIzquierdo.add(panelInformacion, BorderLayout.NORTH);
      panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

      frame.add(panelTabla, BorderLayout.CENTER);
      frame.add(panelIzquierdo, BorderLayout.EAST);

      frame.setSize(1400,750);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   private void botonOrdenar()
   {

   }
}
