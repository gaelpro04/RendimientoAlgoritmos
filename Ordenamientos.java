import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

//Clase que contiene dinstintos metodos de ordenamiento
public class Ordenamientos {

    public void quickSort(double[] a, int n)
    {
        --n;
        recursivo(0,n,a);
    }

    private void recursivo(int inicio, int fin, double[] a) {
        int izq = inicio;
        int der = fin;
        int pos = inicio;
        boolean bandera = true;

        while (bandera) {
            bandera = false;
            // usar compareTo en lugar de <=
            while ((a[pos] <= a[der]) && pos != der) {
                --der;
            }

            if (pos != der) {
                double aux = a[pos];
                a[pos] = a[der];
                a[der] = aux;
                pos = der;

                // usar compareTo en lugar de >=
                while ((a[pos] >= a[izq]) && pos != izq) {
                    ++izq;
                }

                if (pos != izq) {
                    bandera = true;
                    aux = a[pos];
                    a[pos] = a[izq];
                    a[izq] = aux;
                    pos = izq;
                }
            }
        }

        if ((pos - 1) > inicio) {
            recursivo(inicio, pos - 1, a);
        }
        if (fin > (pos + 1)) {
            recursivo(pos + 1, fin, a);
        }
    }

    public void seleccion(double[] a, int n)
    {
        for (int i = 0; i < n-1; i++) {
            double menor = a[i];
            int k = i;
            for (int j = i+1; j < n; j++) {
                if (a[i] < menor) {
                    menor = a[j];
                    k = j;
                }
            }
            a[k] = a[i];
            a[i] = menor;
        }
    }

    public void shell(double[] a, int n)
    {
        int inter = n;
        while (inter > 1) {
            inter = inter / 2;
            boolean bandera = true;
            while (bandera) {
                bandera = false;
                int i = 0;
                while ((i + inter) < n) {

                    if (a[i] > a[i + inter]) {
                        double aux = a[i];
                        a[i] = a[i + inter];
                        a[i + inter] = aux;
                        bandera = true;
                    }
                    ++i;
                }
            }
        }
    }

    public void radixSort(double[] arr) {
        // Encuentra el valor máximo en el array
        double max = Arrays.stream(arr).max().getAsDouble();

        // Trabajamos con la parte entera del número, asegurándonos de que sea positivo
        int decimalPlaces = getMaxDecimalPlaces(arr);

        // Multiplicamos por 10^decimalPlaces para eliminar los decimales
        int factor = (int) Math.pow(10, decimalPlaces);

        // Primero, aplicamos Radix Sort sobre los enteros
        int maxInt = (int) (max * factor); // Obtenemos el máximo valor ajustado por el factor
        for (int exp = 1; maxInt / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp, factor);
        }
    }

    // Método auxiliar para contar los dígitos y ordenar
    private void countingSortByDigit(double[] arr, int exp, int factor) {
        int n = arr.length;
        double[] output = new double[n];
        int[] count = new int[10];

        // Calculamos el índice de cada número en el array de acuerdo al dígito en la posición exp
        for (int i = 0; i < n; i++) {
            int index = (int) ((arr[i] * factor) / exp) % 10;
            count[index]++;
        }

        // Calculamos las posiciones acumuladas
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Construimos el array de salida (output) con los elementos ordenados
        for (int i = n - 1; i >= 0; i--) {
            int index = (int) ((arr[i] * factor) / exp) % 10;
            output[count[index] - 1] = arr[i];
            count[index]--;
        }

        // Copiamos el array de salida de vuelta al original
        System.arraycopy(output, 0, arr, 0, n);
    }

    // Método para obtener la cantidad máxima de decimales en los números del array
    private static int getMaxDecimalPlaces(double[] arr) {
        int maxDecimals = 0;
        for (double num : arr) {
            String[] parts = String.valueOf(num).split("\\.");
            if (parts.length > 1) {
                maxDecimals = Math.max(maxDecimals, parts[1].length());
            }
        }
        return maxDecimals;
    }

    public void sort(double[] a)
    {
        Arrays.sort(a);
    }

    public void parallelSort(double[] a)
    {
        Arrays.parallelSort(a);
    }

    private void merge(double[] a, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        double[] L = new double[n1];
        double[] R = new double[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = a[l + i];
        }

        for (int i = 0; i < n2; i++) {
            R[i] = a[m + 1 + i];
        }

        int i = 0, j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                a[k] = L[i];
                i++;
            } else {
                a[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            a[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            a[k] = R[j];
            j++;
            k++;
        }
    }

    public void sort(double[] a, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            sort(a, l, m);
            sort(a, m + 1, r);

            merge(a, l, m, r);
        }
    }

    public void mergeSort(double[] a) {
        if (a != null && a.length > 1) {
            sort(a, 0, a.length - 1);
        }
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Genericos

    public <T extends  Comparable<T>> void quickSort(T[] a, int n)
    {
        --n;
        recursivo(0,n,a);
    }

    private <T extends  Comparable<T>> void recursivo(int inicio, int fin, T[] a) {
        int izq = inicio;
        int der = fin;
        int pos = inicio;
        boolean bandera = true;

        while (bandera) {
            bandera = false;
            // usar compareTo en lugar de <=
            while (((Comparable<T>) a[pos]).compareTo(a[der]) <= 0 && pos != der) {
                --der;
            }

            if (pos != der) {
                T aux = a[pos];
                a[pos] = a[der];
                a[der] = aux;
                pos = der;

                // usar compareTo en lugar de >=
                while (((Comparable<T>) a[pos]).compareTo(a[izq]) >= 0 && pos != izq) {
                    ++izq;
                }

                if (pos != izq) {
                    bandera = true;
                    aux = a[pos];
                    a[pos] = a[izq];
                    a[izq] = aux;
                    pos = izq;
                }
            }
        }

        if ((pos - 1) > inicio) {
            recursivo(inicio, pos - 1, a);
        }
        if (fin > (pos + 1)) {
            recursivo(pos + 1, fin, a);
        }
    }

    public <T extends  Comparable<T>> void seleccion(T[] a, int n)
    {
        for (int i = 0; i < n-1; i++) {
            T menor = a[i];
            int k = i;
            for (int j = i+1; j < n; j++) {
                if (((Comparable<T>) a[j]).compareTo(menor) < 0) {
                    menor = a[j];
                    k = j;
                }
            }
            a[k] = a[i];
            a[i] = menor;
        }
    }

    public <T extends  Comparable<T>> void shell(T[] a, int n)
    {
        int inter = n + 1;
        while (inter > 1) {
            inter = inter / 2;
            boolean bandera = true;
            while (bandera) {
                bandera = false;
                int i = 0;
                while ((i + inter) <= n) {

                    if (((Comparable<T>) a[i]).compareTo(a[i + inter]) > 0) {
                        T aux = a[i];
                        a[i] = a[i + inter];
                        a[i + inter] = aux;
                        bandera = true;
                    }
                    ++i;
                }
            }
        }
    }

    private int obtenerMax(int[] arr, int n)
    {
        int max = arr[0];
        for (int i = 1; i < n; ++i) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }

    /**
     *
     * @param arr
     * @param n
     * @param exp
     */
    private void countSort(int[] arr, int n, int exp)
    {
        int[] salida = new int[n];
        int[] contador = new int[10];
        Arrays.fill(contador, 0);

        for (int j = 0; j < n; j++) {
            ++contador[arr[j] / exp % 10];
        }

        for (int j = 1; j < 10; j++) {
            contador[j] += contador[j-1];
        }

        for (int j = n - 1; j >= 0; j--) {
            salida[contador[(arr[j] / exp) % 10] - 1] = arr[j];
            --contador[(arr[j] / exp) % 10];
        }

        for (int j = 0; j < n; j++) {
            arr[j] = salida[j];
        }
    }

    /**
     * Metodo de ordenamiento radixSort
     * @param arr
     * @param n
     */
    public void radixSort(int[] arr, int n)
    {
        int max = obtenerMax(arr, n);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, n, exp);
        }
    }

    public <T extends  Comparable<T>> void sort(T[] a)
    {
        Arrays.sort(a);
    }

    public <T extends  Comparable<T>> void parallelSort(T[] a)
    {
        Arrays.parallelSort(a);
    }

    private <T extends  Comparable<T>> void merge(T[] a, int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        T[] L = (T[]) new Object[n1];
        T[] R = (T[]) new Object[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = a[l + i];
        }

        for (int i = 0; i < n2; i++) {
            R[i] = a[m + 1 + i];
        }

        int i = 0;
        int j = 0;

        int k = l;

        while (i < n1 && j < n2) {

            if (((Comparable<T>) L[i]).compareTo(R[j]) <= 0) {
                a[k] = L[i];
                ++i;
            } else {
                a[k] = R[j];
                ++j;
            }
            ++k;
        }

        while (i < n1)
        {
           a[k] = L[i];
           ++i;
           ++k;
        }

        while (j < n2)
        {
            a[k] = R[j];
            ++j;
            ++k;
        }
    }

    public <T extends  Comparable<T>> void sort(T[] a, int l, int r)
    {
        if (l < r)
        {
            int m = l + (r - l) / 2;

            sort(a, l, m);
            sort(a, m + 1, r);

            merge(a, l, m, r);
        }
    }

    public <T extends  Comparable<T>> void mergeSort(T[] a)
    {
        if (a != null && a.length > 1) {
            sort(a, 0, a.length - 1);
        }
    }

}
