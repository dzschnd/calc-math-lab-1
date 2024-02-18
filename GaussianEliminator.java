public class GaussianEliminator {
    private static final double EPSILON = 1e-16;
    private double[][] a;
    private double[] b;
    private double[] x;
    private double[] r;
    private double determinant;
    private int n;
    public GaussianEliminator(double[][] a, double[] b, int n) {
        this.n = n;
        this.a = a;
        this.b = b;
        x = new double[n];
        r = new double[n];
    }
    public void solve() {
        determinant = 1.0;

        for (int pivot = 0; pivot < n; pivot++) {
            int maxPivot = pivot;
            for (int i = pivot + 1; i < n; i++) {
                if (Math.abs(a[i][pivot]) > Math.abs(a[maxPivot][pivot])) {
                    maxPivot = i;
                }
            }
            rowsSwap(pivot, maxPivot);
            determinant *= -1;

            if (Math.abs(a[pivot][pivot]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular.\n");
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
    }
    public void matrixPrint() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println("| " + b[i]);
        }
        System.out.println();
    }
    public double getDeterminant() {
        return this.determinant;
    }
    public double[] getSolution() {
        return x;
    }
    public double[] getResiduals() {
        return r;
    }
    public int getN() {
        return n;
    }
    private void rowsSwap(int i, int j) {
        if (i != j) {
            double[] aTemp = a[i];
            a[i] = a[j];
            a[j] = aTemp;
            double bTemp = b[i];
            b[i] = b[j];
            b[j] = bTemp;
        }
    }
    public void printResults() {
        System.out.println("Determinant = " + determinant + "\n");
        System.out.println("Triangle matrix: ");
        this.matrixPrint();
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
}
