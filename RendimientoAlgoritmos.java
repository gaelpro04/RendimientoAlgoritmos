import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.jfree.chart.*;

public class RendimientoAlgoritmos {

   private Ordenamientos algoritmos;
   private LecturaArchivos csv;
   private JFreeChart graficas;
   private Object[][] datos;

   private JFrame frame;
   private JPanel panelTabla, panelBotones, panelInformacion, panelDerecho;
   private JComboBox<String> comboBoxAlgoritmos, comboBoxColumna;
   private JButton botonOrdenar;
   private JLabel labelInformacion, labelEncabezado, labelColumna;
   private JTable tablaDatos;

   public RendimientoAlgoritmos()
   {
      csv = new LecturaArchivos();
      csv.leer("C:\\Users\\sgsg_\\IdeaProjects\\RendimientoAlgoritmos\\archive\\weatherHistory.csv");
      String[] encabezados = csv.regresarFilaString(0);
      algoritmos = new Ordenamientos();


      frame = new JFrame("Prueba de algoritmos");
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());

      panelTabla = new JPanel();
      panelTabla.setPreferredSize(new Dimension(1200, 750));
      panelTabla.setLayout(new BorderLayout());

      datos = new Object[csv.regresar().size() - 1][];
      for (int i = 1; i < csv.regresar().size(); i++) {
         datos[i - 1] = csv.regresarFila(i);

      }

      DefaultTableModel modelo = new DefaultTableModel(datos, encabezados) {
         @Override
         public boolean isCellEditable(int row, int column) {
            return false; // Ninguna celda editable
         }
      };

      tablaDatos = new JTable(modelo);
      tablaDatos.setRowHeight(20);
      tablaDatos.setEnabled(false);

      DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
      centrado.setHorizontalAlignment(SwingConstants.CENTER);

      for (int i = 0; i < tablaDatos.getColumnCount(); i++) {
         tablaDatos.getColumnModel().getColumn(i).setPreferredWidth(250);
         tablaDatos.getColumnModel().getColumn(i).setCellRenderer(centrado);
      }





      panelDerecho = new JPanel();
      panelDerecho.setBackground(Color.RED);
      panelDerecho.setPreferredSize(new Dimension(300, 750));
      panelDerecho.setLayout(new BorderLayout());

      panelBotones = new JPanel();
      panelBotones.setPreferredSize(new Dimension(300,375));
      panelBotones.setLayout(null);
      panelBotones.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));



      botonOrdenar = new JButton("Ordenar");
      botonOrdenar.addActionListener(action -> botonOrdenar());
      botonOrdenar.setBounds(150, 25,90,25);
      botonOrdenar.setFocusPainted(false);

      String[] elementos = {"quickSort", "mergeSort", "shellSort", "seleccion", "radixSort", "sort", "parallelSort"};
      comboBoxAlgoritmos = new JComboBox<>(elementos);
      comboBoxAlgoritmos.setBounds(50,25,90,25);
      comboBoxAlgoritmos.setFocusable(false);

      labelColumna = new JLabel("Selecciona la columna");
      labelColumna.setBounds(67,55,192,25);
      labelColumna.setFont(new Font("Arial", Font.BOLD, 14));

      comboBoxColumna = new JComboBox<>(encabezados);
      comboBoxColumna.setBounds(50,80,192,25);
      comboBoxColumna.setFocusable(false);


      panelTabla.add(new JScrollPane(tablaDatos), BorderLayout.CENTER);

      panelBotones.add(comboBoxAlgoritmos);
      panelBotones.add(botonOrdenar);
      panelBotones.add(labelColumna);
      panelBotones.add(comboBoxColumna);

      panelInformacion = new JPanel();
      panelInformacion.setBackground(Color.WHITE);
      panelInformacion.setPreferredSize(new Dimension(300,350));
      panelInformacion.setLayout(new BorderLayout());
      panelInformacion.setBorder(BorderFactory.createLineBorder(Color.GRAY,5));

      labelEncabezado = new JLabel("Datos algoritmo", SwingConstants.CENTER);
      labelEncabezado.setFont(new Font("Arial", Font.BOLD,16));
      labelEncabezado.setOpaque(true);

      panelInformacion.add(labelEncabezado, BorderLayout.NORTH);


      panelDerecho.add(panelInformacion, BorderLayout.NORTH);
      panelDerecho.add(panelBotones, BorderLayout.SOUTH);

      frame.add(panelTabla, BorderLayout.CENTER);
      frame.add(panelDerecho, BorderLayout.EAST);

      frame.setSize(1500,750);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   private void botonOrdenar()
   {
      String algoritmo = (String) comboBoxAlgoritmos.getSelectedItem();

      String[] encabezados = csv.regresarFilaString(0);
      ArrayList<String> encebzadosArray = new ArrayList<>(encabezados.length);
      encebzadosArray.addAll(Arrays.asList(encabezados));
      int indice = encebzadosArray.indexOf((String) comboBoxColumna.getSelectedItem());

      // String[] elementos = {"quickSort", "mergeSort", "shellSort", "seleccion", "radixSort", "sort", "parallelSort"};
      String tipo;
      if (indice > 2 && indice < 11) {
         tipo = "numerico";
      } else {
         tipo = "string";
      }

      System.out.println("Algoritmo: " + algoritmo + " Columna: " + indice);

      switch (algoritmo) {
         case "quickSort":
            switch (tipo) {
               case "numerico":
                  double[] columnaArreglo = new double[datos.length];
                  int[] indices = new int[datos.length];

                  for (int i = 0; i < columnaArreglo.length; ++i) {
                     columnaArreglo[i] = Double.parseDouble(datos[i][indice].toString());
                     indices[i] = i;
                  }

                  long startTime = System.nanoTime();
                  algoritmos.quickSort(columnaArreglo, indices, columnaArreglo.length);
                  long endTime = System.nanoTime();

                  Object[][] nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices[i]];
                  }

                  DefaultTableModel modelo = new DefaultTableModel(nuevosDatos, encabezados) {
                     @Override
                     public boolean isCellEditable(int row, int column) {
                        return false; // Ninguna celda editable
                     }
                  };
                  JTable nuevaTabla = new JTable(modelo);

                  tablaDatos = nuevaTabla;
                  tablaDatos.setRowHeight(20);
                  tablaDatos.setEnabled(false);

                  DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
                  centrado.setHorizontalAlignment(SwingConstants.CENTER);

                  for (int i = 0; i < tablaDatos.getColumnCount(); i++) {
                     tablaDatos.getColumnModel().getColumn(i).setPreferredWidth(250);
                     tablaDatos.getColumnModel().getColumn(i).setCellRenderer(centrado);
                  }

                  panelTabla.repaint();
                  panelTabla.revalidate();

                  break;
               case "string":
                  break;
            }








            break;
         case "mergeSort":
            switch (tipo) {
               case "numerico":
                  break;
               case "string":
                  break;
            }
            break;
         case "shellSort":
            switch (tipo) {
               case "numerico":
                  break;
               case "string":
                  break;
            }
            break;
         case "seleccion":
            switch (tipo) {
               case "numerico":
                  break;
               case "string":
                  break;
            }
            break;
         case "radixSort":
            switch (tipo) {
               case "numerico":
                  break;
               case "string":
                  break;
            }
            break;
         case "sort":
            switch (tipo) {
               case "numerico":
                  break;
               case "string":
                  break;
            }
            break;
         case "parallelSort":
            switch (tipo) {
               case "numerico":
                  break;
               case "string":
                  break;
            }
            break;
      }
      desbloqueoGraficas();
   }

   private void desbloqueoGraficas()
   {
      JLabel labelGraficado = new JLabel("Mostrar grafica");
      labelGraficado.setFont(new Font("Arial", Font.BOLD, 14));
      labelGraficado.setBounds(90,105,192,25);

      String[] encabezados = csv.regresarFilaString(0);
      JComboBox comboBoxGraficas = new JComboBox<>(encabezados);
      comboBoxGraficas.setBounds(50,130,192,25);
      comboBoxGraficas.setFocusable(false);

      panelBotones.add(labelGraficado);
      panelBotones.add(comboBoxGraficas);
      panelBotones.repaint();
      panelBotones.revalidate();
   }

   private void ordenarColumnas(String columna)
   {

   }

   private Object obtenerColumna(String columna)
   {
      return null;
   }
}
