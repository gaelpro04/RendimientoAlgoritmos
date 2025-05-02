import java.util.Arrays;

//Clase que contiene dinstintos metodos de ordenamiento
public class Ordenamientos<T> {

    public void quickSort(T[] a, int n)
    {
        --n;
        recursivo(0,n,a);
    }

    private void recursivo(int inicio, int fin, T[] a) {
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

    public void seleccion(T[] a, int n)
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

    public void shell(T[] a, int n)
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

    public void sort(T[] a)
    {
        Arrays.sort(a);
    }

    public <T extends Comparable<T>> void parallelSort(T[] a)
    {
        Arrays.parallelSort(a);
    }

    private void merge(T[] a, int l, int m, int r)
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

    public void sort(T[] a, int l, int r)
    {
        if (l < r)
        {
            int m = l + (r - l) / 2;

            sort(a, l, m);
            sort(a, m + 1, r);

            merge(a, l, m, r);
        }
    }

    public void mergeSort(T[] a)
    {
        if (a != null && a.length > 1) {
            sort(a, 0, a.length - 1);
        }
    }
}
