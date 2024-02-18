import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private int n;
    private double[][] a;
    private double[] b;
    public void run() {
        while (true) {
            System.out.println("0. Input from console");
            System.out.println("1. Input from file");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            String command = scanner.next();

            switch (command) {
                case "0" -> inputFromConsole();
                case "1" -> {
                        System.out.print("Enter the filename: ");
                        String filename = scanner.next();
                        inputFromFile(new File(filename));
                }
                case "2" -> {
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    private void inputFromConsole() {
        while (true) {
            System.out.print("Enter the number of equations (n): ");
            try {
                n = scanner.nextInt();
                if (n <= 20) break;
                System.out.println("The number of equations must be less than or equal to 20.");
            } catch (InputMismatchException e) {
                System.out.println("Input mismatch. Please enter a valid numeric value.");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("Exiting input process.");
                scanner.close();
                System.exit(0);
            }
        }

        a = new double[n][n];
        b = new double[n];

        System.out.println("Enter the coefficients of the linear system: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("a[%d][%d]: ", i, j);
                try {
                    a[i][j] = scanner.nextDouble();
                } catch ( InputMismatchException e) {
                    System.out.println("Input mismatch. Please enter a valid numeric value.");
                    scanner.nextLine();
                    j--;
                }
            }
        }

        System.out.println("Enter the constants of the linear system: ");
        for (int i = 0; i < n; i++) {
            System.out.printf("b[%d]: ", i);
            try {
                b[i] = scanner.nextDouble();
            } catch ( InputMismatchException e) {
                System.out.println("Input mismatch. Please enter a valid numeric value.");
                scanner.nextLine();
                i--;
            }
        }

        GaussianEliminator gaussianEliminator = new GaussianEliminator(a, b, n);
        formResponse(gaussianEliminator);
    }
    private void inputFromFile(File file) {
        try {
            Scanner fileScanner = new Scanner(file);
            n = fileScanner.nextInt();
            if (n > 20) {
                System.out.println("The number of equations must be less than or equal to 20.");
                return;
            }

            a = new double[n][n];
            b = new double[n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    a[i][j] = fileScanner.nextDouble();
                }
            }

            for (int i = 0; i < n; i++) {
                b[i] = fileScanner.nextDouble();
            }

            GaussianEliminator gaussianEliminator = new GaussianEliminator(a, b, n);
            formResponse(gaussianEliminator);

            fileScanner.close();
        } catch (InputMismatchException e) {
            System.out.println("Input mismatch. Please use a valid file format.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
    private static void formResponse(GaussianEliminator gaussianEliminator) {
        System.out.println();
        System.out.println("Original matrix: ");
        gaussianEliminator.matrixPrint();
        try {
            gaussianEliminator.solve();
            gaussianEliminator.printResults();
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
