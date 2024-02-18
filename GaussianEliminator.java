public class GaussianEliminator {
    private static final double EPSILON = 1e-16;
    public static void solve(double[][] a, double[] b, int n) {
        double determinant = 1.0;
        double[] x = new double[n];
        double[] r = new double[n];

        System.out.println("Original matrix: ");
        matrixPrint(a, b, n);

        for (int pivot = 0; pivot < n; pivot++) {
            maxPivotChoose(a, b, n, pivot);

            if (Math.abs(a[pivot][pivot]) <= EPSILON) {
                System.out.println("Matrix is singular\n");
                return;
            }

            determinant *= a[pivot][pivot];

            for (int i = pivot + 1; i < n; i++) {
                double factor = a[i][pivot] / a[pivot][pivot];
                b[i] -= factor * b[pivot];
                for (int j = pivot; j < n; j++) {
                    a[i][j] -= factor * a[pivot][j];
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += a[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / a[i][i];
            r[i] = b[i] - (sum + a[i][i] * x[i]);
        }

        System.out.println("Determinant = " + determinant + "\n");

        System.out.println("Triangle matrix: ");
        matrixPrint(a, b, n);

        System.out.println("Solution: ");
        for (int i = 0; i < n; i++) {
            System.out.print("x[" + i + "] = " + x[i] + " ");
        }
        System.out.println("\n");

        System.out.println("Residuals: ");
        for (int i = 0; i < n; i++) {
            System.out.print("r[" + i + "] = " + r[i] + " ");
        }
        System.out.println("\n");
    }
    private static void matrixPrint(double[][] a, double[] b, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println("| " + b[i]);
        }
        System.out.println();
    }
    private static void rowsSwap(double[][] a, double[] b, int i, int j) {
        if (i != j) {
            double[] aTemp = a[i];
            a[i] = a[j];
            a[j] = aTemp;
            double bTemp = b[i];
            b[i] = b[j];
            b[j] = bTemp;
        }
    }
    private static void maxPivotChoose(double[][] a, double[] b, int n, int pivot) {
        int maxPivot = pivot;
        for (int i = pivot + 1; i < n; i++) {
            if (Math.abs(a[i][pivot]) > Math.abs(a[maxPivot][pivot])) {
                maxPivot = i;
            }
        }
        rowsSwap(a, b, pivot, maxPivot);
    }
}
