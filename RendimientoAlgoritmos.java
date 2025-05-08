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
   private JPanel panelTabla, panelBotones, panelIzquierdo, panelDerecho, panelInformacion;
   private JComboBox<String> comboBoxAlgoritmos, comboBoxColumna, comboBoxOrdenamiento;
   private JButton botonOrdenar, botonOrdenarTodo;
   private JLabel labelInformacion, labelEncabezado, labelColumna;
   private JTable tablaDatos;

   public RendimientoAlgoritmos()
   {
      csv = new LecturaArchivos();
      csv.leer("C:\\Users\\Usuario\\IdeaProjects\\RendimientoAlgoritmos\\archive\\weatherHistory.csv");
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
      botonOrdenar.setBounds(110, 25,83,25);
      botonOrdenar.setFocusPainted(false);

      botonOrdenarTodo = new JButton("Ordenar con todos");
      botonOrdenarTodo.addActionListener(action -> botonOrdenarTodo());
      botonOrdenarTodo.setBounds(60, 170,170,25);
      botonOrdenarTodo.setFocusPainted(false);

      String[] ordenamientos = {"Ascendente", "Descendente"};
      comboBoxOrdenamiento = new JComboBox<>(ordenamientos);
      comboBoxOrdenamiento.setBounds(200,25,90,25);
      comboBoxOrdenamiento.setFocusable(false);

      String[] elementos = {"quickSort", "mergeSort", "shellSort", "seleccion", "radixSort", "sort", "parallelSort"};
      comboBoxAlgoritmos = new JComboBox<>(elementos);
      comboBoxAlgoritmos.setBounds(13,25,90,25);
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
      panelBotones.add(comboBoxOrdenamiento);
      panelBotones.add(botonOrdenarTodo);

      panelIzquierdo = new JPanel();
      panelIzquierdo.setBackground(Color.WHITE);
      panelIzquierdo.setPreferredSize(new Dimension(300,350));
      panelIzquierdo.setLayout(new BorderLayout());
      panelIzquierdo.setBorder(BorderFactory.createLineBorder(Color.GRAY,5));

      panelInformacion = new JPanel();
      panelInformacion.setBackground(Color.WHITE);
      panelInformacion.setLayout(null);

      labelEncabezado = new JLabel("Datos algoritmo", SwingConstants.CENTER);
      labelEncabezado.setFont(new Font("Arial", Font.BOLD,16));
      labelEncabezado.setOpaque(true);

      labelInformacion = new JLabel();
      labelInformacion.setFont(new Font("arial", Font.ITALIC, 12));
      labelInformacion.setBounds(5,0,300,300);

      panelIzquierdo.add(labelEncabezado, BorderLayout.NORTH);
      panelIzquierdo.add(panelInformacion, BorderLayout.CENTER);
      panelInformacion.add(labelInformacion);


      panelDerecho.add(panelIzquierdo, BorderLayout.NORTH);
      panelDerecho.add(panelBotones, BorderLayout.SOUTH);


      JLabel labelGraficado = new JLabel("Mostrar grafica");
      labelGraficado.setFont(new Font("Arial", Font.BOLD, 14));
      labelGraficado.setBounds(90,105,192,25);

      JComboBox<String> comboBoxGraficas = new JComboBox<>(encabezados);
      comboBoxGraficas.setBounds(50,130,192,25);
      comboBoxGraficas.setFocusable(false);

      panelBotones.add(labelGraficado);
      panelBotones.add(comboBoxGraficas);
      panelBotones.repaint();
      panelBotones.revalidate();


      frame.add(panelTabla, BorderLayout.CENTER);
      frame.add(panelDerecho, BorderLayout.EAST);

      frame.setSize(1500,750);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   private void botonOrdenar()
   {
      String algoritmo = (String) comboBoxAlgoritmos.getSelectedItem();
      String columna = (String) comboBoxColumna.getSelectedItem();
      String ordenamiento = (String) comboBoxOrdenamiento.getSelectedItem();

      String[] encabezados = csv.regresarFilaString(0);
      ArrayList<String> encebzadosArray = new ArrayList<>(encabezados.length);
      encebzadosArray.addAll(Arrays.asList(encabezados));
      int indice = encebzadosArray.indexOf(columna);

      String tipo;
      if (indice > 2 && indice < 11) {
         tipo = "numerico";
      } else {
         tipo = "string";
      }

      long startTime = 0;
      long endTime = 0;
      long time = 0;
      Object[][] nuevosDatos = null;

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


                  startTime = System.nanoTime();
                  algoritmos.quickSort(columnaArreglo, indices, columnaArreglo.length, ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices[i]];
                  }
                  break;
               case "string":
                  String[] columnaArreglo1 = new String[datos.length];
                  int[] indices1 = new int[datos.length];

                  for (int i = 0; i < columnaArreglo1.length; ++i) {
                     columnaArreglo1[i] = datos[i][indice].toString();
                     indices1[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.quickSort(columnaArreglo1, indices1, columnaArreglo1.length, ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices1[i]];
                  }
                  break;
            }
            break;
         case "mergeSort":
            switch (tipo) {
               case "numerico":
                  double[] columnaArreglo = new double[datos.length];
                  int[] indices = new int[datos.length];

                  for (int i = 0; i < columnaArreglo.length; ++i) {
                     columnaArreglo[i] = Double.parseDouble(datos[i][indice].toString());
                     indices[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.mergeSort(columnaArreglo, indices, ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices[i]];
                  }
                  break;
               case "string":
                  String[] columnaArreglo1 = new String[datos.length];
                  int[] indices1 = new int[datos.length];

                  for (int i = 0; i < columnaArreglo1.length; ++i) {
                     columnaArreglo1[i] = datos[i][indice].toString();
                     indices1[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.mergeSort(columnaArreglo1, indices1, ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices1[i]];
                  }
                  break;
            }
            break;
         case "shellSort":
            switch (tipo) {
               case "numerico":
                  double[] columnaArreglo = new double[datos.length];
                  int[] indices = new int[datos.length];

                  for (int i = 0; i < columnaArreglo.length; ++i) {
                     columnaArreglo[i] = Double.parseDouble(datos[i][indice].toString());
                     indices[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.shell(columnaArreglo, indices, columnaArreglo.length, ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices[i]];
                  }
                  break;
               case "string":
                  String[] columnaArreglo1 = new String[datos.length];
                  int[] indices1 = new int[datos.length];

                  for (int i = 0; i < columnaArreglo1.length; ++i) {
                     columnaArreglo1[i] = datos[i][indice].toString();
                     indices1[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.shell(columnaArreglo1, indices1, columnaArreglo1.length, ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices1[i]];
                  }
                  break;
            }
            break;
         case "seleccion":
            switch (tipo) {
               case "numerico":
                  double[] columnaArreglo = new double[datos.length];
                  int[] indices = new int[datos.length];

                  for (int i = 0; i < columnaArreglo.length; ++i) {
                     columnaArreglo[i] = Double.parseDouble(datos[i][indice].toString());
                     indices[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.seleccion(columnaArreglo, indices, columnaArreglo.length, ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices[i]];
                  }
                  break;
               case "string":
                  String[] columnaArreglo1 = new String[datos.length];
                  int[] indices1 = new int[datos.length];

                  for (int i = 0; i < columnaArreglo1.length; ++i) {
                     columnaArreglo1[i] = datos[i][indice].toString();
                     indices1[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.seleccion(columnaArreglo1, indices1, ordenamiento, columnaArreglo1.length);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices1[i]];
                  }
                  break;
            }
            break;
         case "radixSort":
            double[] columnaArreglo1 = new double[datos.length];
            int[] indices1 = new int[datos.length];

            for (int i = 0; i < columnaArreglo1.length; ++i) {
               columnaArreglo1[i] = Double.parseDouble(datos[i][indice].toString());
               indices1[i] = i;
            }


            startTime = System.nanoTime();
            algoritmos.radixSort(columnaArreglo1, indices1, ordenamiento);
            endTime = System.nanoTime();

            time = endTime - startTime;

            nuevosDatos = new Object[datos.length][datos[0].length];
            for (int i = 0; i < datos.length; i++) {
               nuevosDatos[i] = datos[indices1[i]];
            }
            break;
         case "sort":
            switch (tipo) {
               case "numerico":
                  Double[] columnaArreglo = new Double[datos.length];
                  Integer[] indices = new Integer[datos.length];

                  for (int i = 0; i < columnaArreglo.length; ++i) {
                     columnaArreglo[i] = Double.parseDouble(datos[i][indice].toString());
                     indices[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.sort(columnaArreglo,indices,ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices[i]];
                  }
                  break;
               case "string":
                  String[] columnaArreglo2 = new String[datos.length];
                  Integer[] indices2 = new Integer[datos.length];

                  for (int i = 0; i < columnaArreglo2.length; ++i) {
                     columnaArreglo2[i] = datos[i][indice].toString();
                     indices2[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.sort(columnaArreglo2, indices2, ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices2[i]];
                  }
                  break;
            }
            break;
         case "parallelSort":
            switch (tipo) {
               case "numerico":
                  Double[] columnaArreglo = new Double[datos.length];
                  Integer[] indices = new Integer[datos.length];

                  for (int i = 0; i < columnaArreglo.length; ++i) {
                     columnaArreglo[i] = Double.parseDouble(datos[i][indice].toString());
                     indices[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.parallelSort(columnaArreglo,indices,ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices[i]];
                  }
                  break;
               case "string":
                  String[] columnaArreglo2 = new String[datos.length];
                  Integer[] indices2 = new Integer[datos.length];

                  for (int i = 0; i < columnaArreglo2.length; ++i) {
                     columnaArreglo2[i] = datos[i][indice].toString();
                     indices2[i] = i;
                  }


                  startTime = System.nanoTime();
                  algoritmos.parallelSort(columnaArreglo2, indices2, ordenamiento);
                  endTime = System.nanoTime();

                  time = endTime - startTime;

                  nuevosDatos = new Object[datos.length][datos[0].length];
                  for (int i = 0; i < datos.length; i++) {
                     nuevosDatos[i] = datos[indices2[i]];
                  }
                  break;
            }
            break;
      }
      double nuevoTime = time / 1000000000.0;
      if (nuevoTime > 60) {
         nuevoTime = nuevoTime / 60;
      }
      setTabla(nuevosDatos,encabezados);
      mostrarInformacion(nuevoTime, algoritmo, columna);
   }

   public void botonOrdenarTodo()
   {
      String columna = (String) comboBoxColumna.getSelectedItem();
      String ordenamiento = (String) comboBoxOrdenamiento.getSelectedItem();
      String[] elementos = {"quickSort", "mergeSort", "shellSort", "seleccion", "radixSort", "sort", "parallelSort"};

      String[] encabezados = csv.regresarFilaString(0);
      ArrayList<String> encebzadosArray = new ArrayList<>(encabezados.length);
      encebzadosArray.addAll(Arrays.asList(encabezados));
      int indice = encebzadosArray.indexOf(columna);

      String tipo;
      if (indice > 2 && indice < 11) {
         tipo = "numerico";
      } else {
         tipo = "string";
      }

      StringBuilder builder = new StringBuilder();
      long time = 0;
      Object[][] nuevosDatos = new Object[datos.length][datos[0].length];

      for (int i = 0; i < elementos.length; i++) {
         long startTime = 0;
         long endTime = 0;
         nuevosDatos = new Object[datos.length][datos[0].length];
         switch (i) {
            case 0:
               switch (tipo) {
                  case "numerico":
                     double[] columnaArreglo = new double[datos.length];
                     int[] indices = new int[datos.length];

                     for (int j = 0; j < columnaArreglo.length; ++j) {
                        columnaArreglo[j] = Double.parseDouble(datos[j][indice].toString());
                        indices[j] = j;
                     }


                     startTime = System.nanoTime();
                     algoritmos.quickSort(columnaArreglo, indices, columnaArreglo.length, ordenamiento);
                     endTime = System.nanoTime();

                     time = endTime - startTime;

                     nuevosDatos = new Object[datos.length][datos[0].length];
                     for (int j = 0; j < datos.length; j++) {
                        nuevosDatos[j] = datos[indices[j]];
                     }
                     break;
                  case "string":
                     break;
               }
               break;
            case 1:
               switch (tipo) {
                  case "numerico":
                     double[] columnaArreglo = new double[datos.length];
                     int[] indices = new int[datos.length];

                     for (int j = 0; j < columnaArreglo.length; ++j) {
                        columnaArreglo[j] = Double.parseDouble(datos[j][indice].toString());
                        indices[j] = j;
                     }


                     startTime = System.nanoTime();
                     algoritmos.mergeSort(columnaArreglo, indices , ordenamiento);
                     endTime = System.nanoTime();

                     time = endTime - startTime;

                     nuevosDatos = new Object[datos.length][datos[0].length];
                     for (int j = 0; j < datos.length; j++) {
                        nuevosDatos[j] = datos[indices[j]];
                     }
                     break;
                  case "string":
                     break;
               }
               break;
            case 2:
               switch (tipo) {
                  case "numerico":
                     double[] columnaArreglo = new double[datos.length];
                     int[] indices = new int[datos.length];

                     for (int j = 0; j < columnaArreglo.length; ++j) {
                        columnaArreglo[j] = Double.parseDouble(datos[j][indice].toString());
                        indices[j] = j;
                     }


                     startTime = System.nanoTime();
                     algoritmos.shell(columnaArreglo, indices , columnaArreglo.length,ordenamiento);
                     endTime = System.nanoTime();

                     time = endTime - startTime;

                     nuevosDatos = new Object[datos.length][datos[0].length];
                     for (int j = 0; j < datos.length; j++) {
                        nuevosDatos[j] = datos[indices[j]];
                     }
                     break;
                  case "string":
                     break;
               }
               break;
            case 3:
               switch (tipo) {
                  case "numerico":
                     double[] columnaArreglo = new double[datos.length];
                     int[] indices = new int[datos.length];

                     for (int j = 0; j < columnaArreglo.length; ++j) {
                        columnaArreglo[j] = Double.parseDouble(datos[j][indice].toString());
                        indices[j] = j;
                     }


                     startTime = System.nanoTime();
                     algoritmos.seleccion(columnaArreglo, indices , columnaArreglo.length, ordenamiento);
                     endTime = System.nanoTime();

                     time = endTime - startTime;

                     nuevosDatos = new Object[datos.length][datos[0].length];
                     for (int j = 0; j < datos.length; j++) {
                        nuevosDatos[j] = datos[indices[j]];
                     }
                     break;
                  case "string":
                     break;
               }
               break;
            case 4:
               double[] columnaArreglo = new double[datos.length];
               int[] indices = new int[datos.length];

               for (int j = 0; j < columnaArreglo.length; ++j) {
                  columnaArreglo[j] = Double.parseDouble(datos[j][indice].toString());
                  indices[j] = j;
               }


               startTime = System.nanoTime();
               algoritmos.radixSort(columnaArreglo, indices, ordenamiento);
               endTime = System.nanoTime();

               time = endTime - startTime;

               nuevosDatos = new Object[datos.length][datos[0].length];
               for (int j = 0; j < datos.length; j++) {
                  nuevosDatos[j] = datos[indices[j]];
               }
               break;
            case 5:
               Double[] columnaArreglo2 = new Double[datos.length];
               Integer[] indices2 = new Integer[datos.length];

               for (int j = 0; j < columnaArreglo2.length; ++j) {
                  columnaArreglo2[j] = Double.parseDouble(datos[j][indice].toString());
                  indices2[j] = j;
               }


               startTime = System.nanoTime();
               algoritmos.sort(columnaArreglo2, indices2, ordenamiento);
               endTime = System.nanoTime();

               time = endTime - startTime;

               nuevosDatos = new Object[datos.length][datos[0].length];
               for (int j = 0; j < datos.length; j++) {
                  nuevosDatos[j] = datos[indices2[j]];
               }
               break;
            case 6:
               Double[] columnaArreglo1 = new Double[datos.length];
               Integer[] indices1 = new Integer[datos.length];

               for (int j = 0; j < columnaArreglo1.length; ++j) {
                  columnaArreglo1[j] = Double.parseDouble(datos[j][indice].toString());
                  indices1[j] = j;
               }


               startTime = System.nanoTime();
               algoritmos.parallelSort(columnaArreglo1, indices1, ordenamiento);
               endTime = System.nanoTime();

               time = endTime - startTime;

               nuevosDatos = new Object[datos.length][datos[0].length];
               for (int j = 0; j < datos.length; j++) {
                  nuevosDatos[j] = datos[indices1[j]];
               }
               break;
         }
         double nuevoTime = time / 1000000000.0;
         if (nuevoTime > 60) {
            nuevoTime = nuevoTime / 60;
         }
         builder.append("<html> Columna:  " + columna + "<br> Algoritmo:  " +
                 elementos[i] + "<br> Tiempo transcurrido:  " + nuevoTime + " s <br> <html>");

      }
      setTabla(nuevosDatos, encabezados);
      labelInformacion.setText(builder.toString());

      panelInformacion.repaint();
      panelInformacion.revalidate();

   }

   private void ordenarColumnas(String columna)
   {

   }

   private Object obtenerColumna(String columna)
   {
      return null;
   }

   private void setTabla(Object[][] nuevosDatos, String[] encabezados)
   {
      DefaultTableModel modelo = new DefaultTableModel(nuevosDatos, encabezados) {
         @Override
         public boolean isCellEditable(int row, int column) {
            return false; // Ninguna celda editable
         }
      };

      tablaDatos = new JTable(modelo);
      tablaDatos.setRowHeight(20);

      DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
      centrado.setHorizontalAlignment(SwingConstants.CENTER);

      for (int i = 0; i < tablaDatos.getColumnCount(); i++) {
         tablaDatos.getColumnModel().getColumn(i).setPreferredWidth(250);
         tablaDatos.getColumnModel().getColumn(i).setCellRenderer(centrado);
      }

      panelTabla.removeAll();
      panelTabla.add(new JScrollPane(tablaDatos), BorderLayout.CENTER);
      panelTabla.repaint();
      panelTabla.revalidate();
   }

   private void mostrarInformacion(double tiempoAbsoluto, String algoritmo, String columna)
   {
      labelInformacion.setText("<html> Columna:  " + columna + "<br> Algoritmo:  " +
              algoritmo + "<br> Tiempo transcurrido:  " + tiempoAbsoluto + " s <html>");

      panelInformacion.repaint();
      panelInformacion.revalidate();
   }
}
