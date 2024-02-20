import java.text.DecimalFormat;

public class GaussianEliminator {
    private static final double EPSILON = 1e-16;
    private double[][] coefficients;
    private double[] constants;
    private double[] unknowns;
    private double[] residuals;
    private double determinant;
    private int equationCount;
    public GaussianEliminator(double[][] coefficients, double[] constants, int equationCount) {
        this.equationCount = equationCount;
        this.coefficients = coefficients;
        this.constants = constants;
        unknowns = new double[equationCount];
        residuals = new double[equationCount];
    }
    public void solve() {
        determinant = 1.0;

        for (int pivot = 0; pivot < equationCount; pivot++) {
            int maxPivot = pivot;
            for (int i = pivot + 1; i < equationCount; i++) {
                if (Math.abs(coefficients[i][pivot]) > Math.abs(coefficients[maxPivot][pivot])) {
                    maxPivot = i;
                }
            }
            if (pivot != maxPivot) {
                rowsSwap(pivot, maxPivot);
                determinant *= -1;
            }

            if (Math.abs(coefficients[pivot][pivot]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular.\n");
            }

            determinant *= coefficients[pivot][pivot];

            for (int i = pivot + 1; i < equationCount; i++) {
                double factor = coefficients[i][pivot] / coefficients[pivot][pivot];
                constants[i] -= factor * constants[pivot];
                for (int j = pivot; j < equationCount; j++) {
                    coefficients[i][j] -= factor * coefficients[pivot][j];
                }
            }
        }

        for (int i = equationCount - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < equationCount; j++) {
                sum += coefficients[i][j] * unknowns[j];
            }
            unknowns[i] = (constants[i] - sum) / coefficients[i][i];
            residuals[i] = constants[i] - (sum + coefficients[i][i] * unknowns[i]);
        }
    }
    private void rowsSwap(int i, int j) {
        double[] aTemp = coefficients[i];
        coefficients[i] = coefficients[j];
        coefficients[j] = aTemp;
        double bTemp = constants[i];
        constants[i] = constants[j];
        constants[j] = bTemp;
    }
    public void matrixPrint() {
        int gap = 4;
        int maxCoefficientLength = getMaxCoefficientLength();
        for (int i = 0; i < equationCount; i++) {
            for (int j = 0; j < equationCount; j++) {
                System.out.printf("%-" + (maxCoefficientLength + gap) + "s", format(coefficients[i][j]));
            }
            System.out.println("|    " + format(constants[i]));
        }
        System.out.println();
    }
    private int getMaxCoefficientLength() {
        int maxLength = 0;
        for (double[] row : coefficients) {
            for (double coefficient : row) {
                String formattedCoefficient = format(coefficient);
                maxLength = Math.max(maxLength, formattedCoefficient.length());
            }
        }
        return maxLength;
    }
    public void printResults() {
        System.out.println("Determinant = " + format(determinant) + "\n");
        System.out.println("Triangle matrix: ");
        this.matrixPrint();
        System.out.println("Solution: ");
        for (int i = 0; i < equationCount; i++) {
            System.out.println("x[" + i + "] = " + format(unknowns[i]));
        }
        System.out.println();
        System.out.println("Residuals: ");
        for (int i = 0; i < equationCount; i++) {
            System.out.println("r[" + i + "] = " + format(residuals[i]));
        }
        System.out.println();
    }
    private String format(double number) {
        double roundedNumber = Math.round(number * 10E3) / 10E3;
        return Double.toString(roundedNumber);
    }
}
