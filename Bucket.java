import java.util.ArrayList;
import java.util.List;

public class Bucket{

    public static void sort(Comparable[] a) {
        if (a.length < 2) return;
        int k = 5;

        System.out.println("Estado inicial:");
        show(a);

        Comparable min = a[0], max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], min)) min = a[i];
            if (less(max, a[i])) max = a[i];
        }
        double minV = valor(min), maxV = valor(max);
        System.out.println("\nPaso 1: minimo = " + min + ", maximo = " + max
                + ", cubetas = " + k);

        List<List<Comparable>> cubetas = new ArrayList<>();
        for (int i = 0; i < k; i++) cubetas.add(new ArrayList<>());

        System.out.println("\nPaso 2: distribuir en cubetas");
        for (Comparable x : a) {
            int idx = indice(valor(x), minV, maxV, k);
            cubetas.get(idx).add(x);
        }
        mostrarCubetas(cubetas);

        System.out.println("\nPaso 3: ordenar cada cubeta");
        for (List<Comparable> cubeta : cubetas) {
            Comparable[] c = cubeta.toArray(new Comparable[0]);
            insertionSort(c);
            for (int j = 0; j < c.length; j++) cubeta.set(j, c[j]);
        }
        mostrarCubetas(cubetas);

        System.out.println("\nPaso 4: concatenar las cubetas");
        int pos = 0;
        for (int b = 0; b < k; b++) {
            for (Comparable x : cubetas.get(b)) a[pos++] = x;
            System.out.println("Despues de la cubeta " + b + ":");
            parcial(a, pos);
        }

        System.out.println("\nArreglo final:");
        show(a);
    }

    private static void insertionSort(Comparable[] c) {
        for (int i = 1; i < c.length; i++)
            for (int j = i; j > 0 && less(c[j], c[j - 1]); j--)
                exch(c, j, j - 1);
    }

    private static int indice(double v, double min, double max, int k) {
        if (max == min) return 0;
        return Math.min((int) ((v - min) / (max - min) * k), k - 1);
    }

    private static double valor(Comparable x) {
        if (!(x instanceof Number))
            throw new IllegalArgumentException("Se necesitan elementos numericos: " + x);
        return ((Number) x).doubleValue();
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++)
            System.out.print(a[i] + " ");
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    private static void mostrarCubetas(List<List<Comparable>> cubetas) {
        for (int b = 0; b < cubetas.size(); b++)
            System.out.println("Bucket " + b + ": " + cubetas.get(b));
    }

    private static void parcial(Comparable[] a, int llenos) {
        for (int i = 0; i < a.length; i++)
            System.out.print((i < llenos ? a[i] : "_") + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        Integer[] a = {29, 25, 3, 49, 9, 37, 21, 43};
        sort(a);
        assert isSorted(a);
        System.out.println("\nEsta ordenado? " + isSorted(a));
    }
}