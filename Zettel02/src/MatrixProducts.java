import java.util.Arrays;
import java.util.Random;

public class MatrixProducts {

    public static Integer[][] res;
    public static int Calls = 0;

    private static int[] createArray(int n) {
        Random random = new Random();
        return random.ints(n, 5, 35).toArray();
    }

    private static int minMatrixCost(int[] p, int i, int j) {
        if (j > 20) return 0; // Sicherheitsfall wegen langen Laufzeiten
        if (i == j) return 0;
        int c = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            c = Math.min(c, minMatrixCost(p, i, k) + minMatrixCost(p, k+1, j) + p[i-1]*p[k]*p[j]);
        }
        return c;
    }

    private static int minMatrixCostMemo(int[] p, int i, int j) {
        // Ergebnis-Tabelle erstellen
        res = new Integer[j+1][j+1];

        // Diagonale auffüllen
        for (int l=0; l<=j; l++) {
            res[l][l] = 0;
        }

        return lookupmatrixcost(p, i, j);
    }

    private static int lookupmatrixcost(int[] p, int i, int j) {
        Calls += 1;
        if (res[i][j] != null) {
            return res[i][j];
        } else {
            // fehlende Werte berechnen
            int c = Integer.MAX_VALUE;
            for (int k = i; k < j; k++) {
                c = Math.min(c, lookupmatrixcost(p, i, k) + lookupmatrixcost(p, k+1, j) + p[i-1]*p[k]*p[j]);
            }

            // Matrix füllen
            res[i][j] = c;

            return c;
        }
    }

    public static void main(String[] args) {

        int[] tests = new int[]{5,10,15,20,25,30,35,40};

        for (int n : tests) {

            int[] p = createArray(n);
            System.out.println("n = "+n);
            System.out.println("p = "+Arrays.toString(p));

            long start;
            long end;

            System.out.println("Naive:");
            start = System.nanoTime();
            System.out.println("\tResult: "+minMatrixCost(p, 1, n-1));
            end = System.nanoTime();
            System.out.println("\tTime: "+(end-start)/1000);

            System.out.println("Memoization:");
            start = System.nanoTime();
            System.out.println("\tResult: "+minMatrixCostMemo(p, 1, n-1));
            end = System.nanoTime();
            System.out.println("\tTime: "+(end-start)/1000);
            System.out.println("\tCalls: "+Calls);
            System.out.println("\tPower of 2: "+String.format("%.2f", (Math.log(Calls) / Math.log(2)))+"\n");

        }

    }

}
