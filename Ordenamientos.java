import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

//Clase que contiene dinstintos metodos de ordenamiento
public class Ordenamientos {

    public void quickSort(double[] a, int[] indices, int n, String ord)
    {
        --n;
        recursivo(0,n,a, indices, ord);
    }

    private void recursivo(int inicio, int fin, double[] a, int[] indices, String ord) {

        if (ord.equals("Ascendente")) {
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

                    int auxDouble = indices[pos];
                    indices[pos] = indices[der];
                    indices[der] = auxDouble;

                    pos = der;

                    // usar compareTo en lugar de >=
                    while ((a[pos] >= a[izq]) && pos != izq) {
                        ++izq;
                    }

                    if (pos != izq) {
                        aux = a[pos];
                        a[pos] = a[izq];
                        a[izq] = aux;

                        auxDouble = indices[pos];
                        indices[pos] = indices[izq];
                        indices[izq] = auxDouble;

                        pos = izq;
                        bandera = true;
                    }
                }
            }

            if ((pos - 1) > inicio) {
                recursivo(inicio, pos - 1, a, indices, ord);
            }
            if (fin > (pos + 1)) {
                recursivo(pos + 1, fin, a, indices, ord);
            }
        } else {
            int izq = inicio;
            int der = fin;
            int pos = inicio;
            boolean bandera = true;

            while (bandera) {
                bandera = false;
                while ((a[pos] >= a[der]) && pos != der) {
                    --der;
                }

                if (pos != der) {
                    double aux = a[pos];
                    a[pos] = a[der];
                    a[der] = aux;

                    int auxDouble = indices[pos];
                    indices[pos] = indices[der];
                    indices[der] = auxDouble;

                    pos = der;

                    while ((a[pos] <= a[izq]) && pos != izq) {
                        ++izq;
                    }

                    if (pos != izq) {
                        aux = a[pos];
                        a[pos] = a[izq];
                        a[izq] = aux;

                        auxDouble = indices[pos];
                        indices[pos] = indices[izq];
                        indices[izq] = auxDouble;

                        pos = izq;
                        bandera = true;
                    }
                }
            }

            if ((pos - 1) > inicio) {
                recursivo(inicio, pos - 1, a, indices, ord);
            }
            if (fin > (pos + 1)) {
                recursivo(pos + 1, fin, a, indices, ord);
            }
        }

    }

    public void seleccion(double[] a, int[] indices, int n, String ord) {
        if (ord.equals("Ascendente")) {
            for (int i = 0; i < n - 1; i++) {
                int k = i;
                for (int j = i + 1; j < n; j++) {
                    if (a[j] < a[k]) {
                        k = j;
                    }
                }

                double tempA = a[i];
                a[i] = a[k];
                a[k] = tempA;

                int tempIndex = indices[i];
                indices[i] = indices[k];
                indices[k] = tempIndex;
            }
        } else {
            for (int i = 0; i < n - 1; i++) {
                int k = i;
                for (int j = i + 1; j < n; j++) {
                    if (a[j] > a[k]) {
                        k = j;
                    }
                }
                double tempA = a[i];
                a[i] = a[k];
                a[k] = tempA;

                int tempIndex = indices[i];
                indices[i] = indices[k];
                indices[k] = tempIndex;
            }
        }
    }

    public void shell(double[] a, int[] indices, int n, String ord)
    {
        if (ord.equals("Ascendente")) {
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

                            int auxIndice = indices[i];
                            indices[i] = indices[i + inter];
                            indices[i + inter] = auxIndice;

                            bandera = true;
                        }
                        ++i;
                    }
                }
            }
        } else {
            int inter = n;
            while (inter > 1) {
                inter = inter / 2;
                boolean bandera = true;
                while (bandera) {
                    bandera = false;
                    int i = 0;
                    while ((i + inter) < n) {

                        if (a[i] < a[i + inter]) {
                            double aux = a[i];
                            a[i] = a[i + inter];
                            a[i + inter] = aux;

                            int auxIndice = indices[i];
                            indices[i] = indices[i + inter];
                            indices[i + inter] = auxIndice;

                            bandera = true;
                        }
                        ++i;
                    }
                }
            }
        }

    }

    public void radixSort(double[] arr, int[] indices, String ord) {
        int n = arr.length;
        int decimalPlaces = 9;
        long factor = (long) Math.pow(10, decimalPlaces);

        long[] scaled = new long[n];
        for (int i = 0; i < n; i++) {
            scaled[i] = (long) Math.round(arr[i] * factor);
            indices[i] = i;
        }

        // Separar negativos y positivos
        List<Long> neg = new ArrayList<>();
        List<Integer> negIdx = new ArrayList<>();
        List<Long> pos = new ArrayList<>();
        List<Integer> posIdx = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (scaled[i] < 0) {
                neg.add(-scaled[i]); // Negativos se hacen positivos para ordenar
                negIdx.add(indices[i]);
            } else {
                pos.add(scaled[i]);
                posIdx.add(indices[i]);
            }
        }

        radixSortLong(pos, posIdx);
        radixSortLong(neg, negIdx);

        // Si es descendente, invertir positivos y mantener negativos al final
        if (ord.equalsIgnoreCase("Ascendente")) {
            Collections.reverse(neg); // Volver a negativos correctamente ordenados
            Collections.reverse(negIdx);
        } else {
            Collections.reverse(pos);
            Collections.reverse(posIdx);
        }

        // Combinar resultados
        List<Long> combined = new ArrayList<>();
        List<Integer> combinedIdx = new ArrayList<>();

        if (ord.equalsIgnoreCase("Ascendente")) {
            for (int i = 0; i < neg.size(); i++) {
                combined.add(-neg.get(i));
                combinedIdx.add(negIdx.get(i));
            }
            combined.addAll(pos);
            combinedIdx.addAll(posIdx);
        } else {
            for (int i = 0; i < pos.size(); i++) {
                combined.add(pos.get(i));
                combinedIdx.add(posIdx.get(i));
            }
            for (int i = 0; i < neg.size(); i++) {
                combined.add(-neg.get(i));
                combinedIdx.add(negIdx.get(i));
            }
        }

        // Convertir de nuevo a double
        for (int i = 0; i < n; i++) {
            arr[i] = combined.get(i) / (double) factor;
            indices[i] = combinedIdx.get(i);
        }
    }

    // Radix Sort para long (base 10)
    private void radixSortLong(List<Long> arr, List<Integer> indices) {
        int maxDigits = 18; // hasta 18 dígitos para long
        long exp = 1;
        int n = arr.size();

        for (int i = 0; i < maxDigits; i++) {
            List<List<Long>> buckets = new ArrayList<>();
            List<List<Integer>> idxBuckets = new ArrayList<>();

            for (int b = 0; b < 10; b++) {
                buckets.add(new ArrayList<>());
                idxBuckets.add(new ArrayList<>());
            }

            for (int j = 0; j < n; j++) {
                long num = arr.get(j);
                int digit = (int) ((num / exp) % 10);
                buckets.get(digit).add(num);
                idxBuckets.get(digit).add(indices.get(j));
            }

            arr.clear();
            indices.clear();

            for (int b = 0; b < 10; b++) {
                arr.addAll(buckets.get(b));
                indices.addAll(idxBuckets.get(b));
            }

            exp *= 10;
        }
    }



    private void merge(double[] a, int[] indices, int l, int m, int r, String ord) {
        int n1 = m - l + 1;
        int n2 = r - m;

        if (ord.equals("Ascendente")) {
            double[] L = new double[n1];
            double[] R = new double[n2];

            int[] Lint = new int[n1];
            int[] Rint = new int[n2];

            for (int i = 0; i < n1; i++) {
                L[i] = a[l + i];
                Lint[i] = indices[l + i];
            }

            for (int i = 0; i < n2; i++) {
                R[i] = a[m + 1 + i];
                Rint[i] = indices[m + 1+ i];
            }

            int i = 0, j = 0;
            int k = l;

            while (i < n1 && j < n2) {
                if (L[i] <= R[j]) {
                    a[k] = L[i];
                    indices[k] = Lint[i];
                    i++;
                } else {
                    a[k] = R[j];
                    indices[k] = Rint[j];
                    j++;
                }
                k++;
            }

            while (i < n1) {
                a[k] = L[i];
                indices[k] = Lint[i];
                i++;
                k++;
            }

            while (j < n2) {
                a[k] = R[j];
                indices[k] = Rint[j];
                j++;
                k++;
            }
        } else {
            double[] L = new double[n1];
            double[] R = new double[n2];

            int[] Lint = new int[n1];
            int[] Rint = new int[n2];

            for (int i = 0; i < n1; i++) {
                L[i] = a[l + i];
                Lint[i] = indices[l + i];
            }

            for (int i = 0; i < n2; i++) {
                R[i] = a[m + 1 + i];
                Rint[i] = indices[m + 1+ i];
            }

            int i = 0, j = 0;
            int k = l;

            while (i < n1 && j < n2) {
                if (L[i] >= R[j]) {
                    a[k] = L[i];
                    indices[k] = Lint[i];
                    i++;
                } else {
                    a[k] = R[j];
                    indices[k] = Rint[j];
                    j++;
                }
                k++;
            }

            while (i < n1) {
                a[k] = L[i];
                indices[k] = Lint[i];
                i++;
                k++;
            }

            while (j < n2) {
                a[k] = R[j];
                indices[k] = Rint[j];
                j++;
                k++;
            }
        }
    }

    public void sort(double[] a,int [] indices, int l, int r, String ord) {
        if (l < r) {
            int m = l + (r - l) / 2;

            sort(a, indices, l, m, ord);
            sort(a, indices, m + 1, r, ord);

            merge(a, indices, l, m, r, ord);
        }
    }

    public void mergeSort(double[] a, int[] indices, String ord) {
        if (a != null && a.length > 1) {
            sort(a, indices, 0, a.length - 1, ord);
        }
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Genericos

    public <T extends  Comparable<T>> void quickSort(T[] a, int[] indices, int n, String ord)
    {
        --n;
        recursivo(0,n,a, indices, ord);
    }

    private <T extends Comparable<T>> void recursivo(int inicio, int fin, T[] a, int[] indices, String ord) {
        if (inicio >= fin) return;

        int izq = inicio;
        int der = fin;
        T pivote = a[inicio];

        while (izq <= der) {
            if (ord.equals("Ascendente")) {
                while (izq <= fin && a[izq].compareTo(pivote) < 0) izq++;
                while (der >= inicio && a[der].compareTo(pivote) > 0) der--;
            } else {
                while (izq <= fin && a[izq].compareTo(pivote) > 0) izq++;
                while (der >= inicio && a[der].compareTo(pivote) < 0) der--;
            }

            if (izq <= der) {
                // Intercambiar valores en el arreglo principal
                T temp = a[izq];
                a[izq] = a[der];
                a[der] = temp;

                // Intercambiar índices asociados
                int tempIdx = indices[izq];
                indices[izq] = indices[der];
                indices[der] = tempIdx;

                izq++;
                der--;
            }
        }

        // Llamadas recursivas a subarreglos
        recursivo(inicio, der, a, indices, ord);
        recursivo(izq, fin, a, indices, ord);
    }

    public <T extends  Comparable<T>> void seleccion(T[] a, int[] indices, String ord, int n)
    {
        if (ord.equals("Ascendente")) {
            for (int i = 0; i < n - 1; i++) {
                int k = i;
                for (int j = i + 1; j < n; j++) {
                    if (a[j].compareTo(a[k]) < 0) {
                        k = j;
                    }
                }

                T tempA = a[i];
                a[i] = a[k];
                a[k] = tempA;

                int tempIndex = indices[i];
                indices[i] = indices[k];
                indices[k] = tempIndex;
            }
        } else {
            for (int i = 0; i < n - 1; i++) {
                int k = i;
                for (int j = i + 1; j < n; j++) {
                    if (a[j].compareTo(a[k]) > 0) {
                        k = j;
                    }
                }
                T tempA = a[i];
                a[i] = a[k];
                a[k] = tempA;

                int tempIndex = indices[i];
                indices[i] = indices[k];
                indices[k] = tempIndex;
            }
        }
    }

    public <T extends  Comparable<T>> void shell(T[] a, int[] indices, int n, String ord)
    {
        if (ord.equals("Ascendente")) {
            int inter = n;
            while (inter > 1) {
                inter = inter / 2;
                boolean bandera = true;
                while (bandera) {
                    bandera = false;
                    int i = 0;
                    while ((i + inter) < n) {

                        if (a[i].compareTo(a[i + inter]) > 0) {
                            T aux = a[i];
                            a[i] = a[i + inter];
                            a[i + inter] = aux;

                            int auxIndice = indices[i];
                            indices[i] = indices[i + inter];
                            indices[i + inter] = auxIndice;

                            bandera = true;
                        }
                        ++i;
                    }
                }
            }
        } else {
            int inter = n;
            while (inter > 1) {
                inter = inter / 2;
                boolean bandera = true;
                while (bandera) {
                    bandera = false;
                    int i = 0;
                    while ((i + inter) < n) {

                        if (a[i].compareTo(a[i + inter]) < 0) {
                            T aux = a[i];
                            a[i] = a[i + inter];
                            a[i + inter] = aux;

                            int auxIndice = indices[i];
                            indices[i] = indices[i + inter];
                            indices[i + inter] = auxIndice;

                            bandera = true;
                        }
                        ++i;
                    }
                }
            }
        }

    }

    public <T extends Comparable<T>> void sort(T[] a, Integer[] indices, String ord) {
        if (ord.equalsIgnoreCase("Ascendente")) {
            Arrays.sort(indices, (i1, i2) -> a[i1].compareTo(a[i2]));
        } else {
            Arrays.sort(indices, (i1, i2) -> a[i2].compareTo(a[i1]));
        }

        T[] aOrdenado = Arrays.copyOf(a, a.length);
        for (int i = 0; i < a.length; i++) {
            aOrdenado[i] = a[indices[i]];
        }

        // Copia de vuelta a
        System.arraycopy(aOrdenado, 0, a, 0, a.length);
    }

    public <T extends  Comparable<T>> void parallelSort(T[] a, Integer[] indices, String ord)
    {
        if (ord.equalsIgnoreCase("Ascendente")) {
            Arrays.parallelSort(indices, (i1, i2) -> a[i1].compareTo(a[i2]));
        } else {
            Arrays.parallelSort(indices, (i1, i2) -> a[i2].compareTo(a[i1]));
        }

        T[] aOrdenado = Arrays.copyOf(a, a.length);
        for (int i = 0; i < a.length; i++) {
            aOrdenado[i] = a[indices[i]];
        }

        // Copia de vuelta a
        System.arraycopy(aOrdenado, 0, a, 0, a.length);
    }

    private <T extends  Comparable<T>> void merge(T[] a, int [] indices, int l, int m, int r, String ord)
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        if (ord.equals("Ascendente")) {
            T[] L = (T[]) Array.newInstance(a.getClass().getComponentType(), n1);

            T[] R = (T[]) Array.newInstance(a.getClass().getComponentType(), n2);
            int[] Lint = new int[n1];
            int[] Rint = new int[n2];

            for (int i = 0; i < n1; i++) {
                L[i] = a[l + i];
                Lint[i] = indices[l + i];
            }

            for (int i = 0; i < n2; i++) {
                R[i] = a[m + 1 + i];
                Rint[i] = indices[m + 1+ i];
            }

            int i = 0, j = 0;
            int k = l;

            while (i < n1 && j < n2) {
                if (L[i].compareTo(R[j]) <= 0) {
                    a[k] = L[i];
                    indices[k] = Lint[i];
                    i++;
                } else {
                    a[k] = R[j];
                    indices[k] = Rint[j];
                    j++;
                }
                k++;
            }

            while (i < n1) {
                a[k] = L[i];
                indices[k] = Lint[i];
                i++;
                k++;
            }

            while (j < n2) {
                a[k] = R[j];
                indices[k] = Rint[j];
                j++;
                k++;
            }
        } else {
            T[] L = (T[]) Array.newInstance(a.getClass().getComponentType(), n1);

            T[] R = (T[]) Array.newInstance(a.getClass().getComponentType(), n2);

            int[] Lint = new int[n1];
            int[] Rint = new int[n2];

            for (int i = 0; i < n1; i++) {
                L[i] = a[l + i];
                Lint[i] = indices[l + i];
            }

            for (int i = 0; i < n2; i++) {
                R[i] = a[m + 1 + i];
                Rint[i] = indices[m + 1+ i];
            }

            int i = 0, j = 0;
            int k = l;

            while (i < n1 && j < n2) {
                if (L[i].compareTo(R[j]) >= 0) {
                    a[k] = L[i];
                    indices[k] = Lint[i];
                    i++;
                } else {
                    a[k] = R[j];
                    indices[k] = Rint[j];
                    j++;
                }
                k++;
            }

            while (i < n1) {
                a[k] = L[i];
                indices[k] = Lint[i];
                i++;
                k++;
            }

            while (j < n2) {
                a[k] = R[j];
                indices[k] = Rint[j];
                j++;
                k++;
            }
        }
    }

    public <T extends  Comparable<T>> void sort(T[] a, int l, int r, String ord, int[] indices)
    {
        if (l < r)
        {
            int m = l + (r - l) / 2;

            sort(a, l, m, ord, indices);
            sort(a, m + 1, r, ord, indices);

            merge(a, indices,l, m, r, ord);
        }
    }

    public <T extends  Comparable<T>> void mergeSort(T[] a, int[] indices, String ord)
    {
        if (a != null && a.length > 1) {
            sort(a, 0, a.length - 1, ord, indices);
        }
    }

}
