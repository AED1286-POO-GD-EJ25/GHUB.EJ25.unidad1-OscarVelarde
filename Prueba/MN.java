package Prueba;
public class MN {
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int opcionSeleccionada;

    do {
        System.out.println("--------------------------------------------------");
        System.out.println("                 Instituto Tecnológico de Culiacán");
        System.out.println("                          Ing. en Sistemas");
        System.out.println("                   Benitez Rodelo Rut A.");
        System.out.println("       Solución de Sistemas de Ecuaciones de 14:00 a 15:00 horas");
        System.out.println("--------------------------------------------------\n");
        System.out.println("Seleccione el método para calcular el precio estimado de venta de cada carrito el día del niño:");
        System.out.println("1. Método de Gauss-Seidel");
        System.out.println("2. Método de Gauss-Jordan");
        System.out.println("3. Salir");
        System.out.print("Ingrese su opción: ");
        opcionSeleccionada = scanner.nextInt();

        switch (opcionSeleccionada) {
            case 1:
                metodoGaussSeidel(scanner);
                break;
            case 2:
                metodoGaussJordan(scanner);
                break;
            case 3:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }
    } while (opcionSeleccionada != 3);

    scanner.close();
}

public static void metodoGaussSeidel(Scanner scanner) {
    System.out.println("--------------------------------------------------");
    System.out.println("                   Método de Gauss-Seidel");
    System.out.println("--------------------------------------------------");
    System.out.println("Precio estimado de venta de cada carrito el día del niño\n");

    double[] ventasTotales = new double[4];
    int[][] cantidadCarritosVendidos = new int[4][4];
    String[] sucursales = {"Culiacán", "Mazatlán", "Guasave", "Mochis"};

    System.out.println("Ingrese las ventas por sucursal:");
    for (int i = 0; i < 4; i++) {
        System.out.print("Venta en sucursal " + sucursales[i] + ": ");
        ventasTotales[i] = scanner.nextDouble();
    }

    System.out.println("Ingrese la cantidad de carritos vendidos por tipo en cada sucursal:");
    for (int i = 0; i < 4; i++) {
        System.out.println("Sucursal " + sucursales[i] + ": ");
        for (int j = 0; j < 4; j++) {
            System.out.print("Venta del carrito tipo " + (j + 1) + ": ");
            cantidadCarritosVendidos[i][j] = scanner.nextInt();
        }
    }

    double[] preciosIniciales = {100, 100, 100, 100};
    double[] preciosCalculados = new double[4];
    double margenError = 0.1;  // Cambio del margen de error
    int maximoIteraciones = 50;
    int iteracionActual = 0;
    boolean continuarCalculando;

    System.out.println("\nIteración | Precio 1 | Precio 2 | Precio 3 | Precio 4 | Margen de Error");
    System.out.println("-----------------------------------------------------------------------");

    do {
        continuarCalculando = false;
        double[] sumaVentasEstimadas = new double[4];
        double[] sumaCantidadCarritos = new double[4];
        double errorMaximo = 0;

        for (int i = 0; i < 4; i++) {
            double totalEstimado = 0;
            for (int j = 0; j < 4; j++) {
                totalEstimado += cantidadCarritosVendidos[i][j] * preciosIniciales[j];
            }

            if (totalEstimado > 0) {
                double factorAjuste = ventasTotales[i] / totalEstimado;
                for (int j = 0; j < 4; j++) {
                    sumaVentasEstimadas[j] += cantidadCarritosVendidos[i][j] * preciosIniciales[j] * factorAjuste;
                    sumaCantidadCarritos[j] += cantidadCarritosVendidos[i][j];
                }
            }
        }

        for (int j = 0; j < 4; j++) {
            if (sumaCantidadCarritos[j] > 0) {
                preciosCalculados[j] = sumaVentasEstimadas[j] / sumaCantidadCarritos[j];
            } else {
                preciosCalculados[j] = preciosIniciales[j];
            }

            double errorCalculado = Math.abs(preciosCalculados[j] - preciosIniciales[j]);
            if (errorCalculado > errorMaximo) {
                errorMaximo = errorCalculado;
            }

            if (errorCalculado > margenError) {
                continuarCalculando = true;
            }
            preciosIniciales[j] = preciosCalculados[j];
        }

        System.out.printf("%9d | %8.2f | %8.2f | %8.2f | %8.2f | %8.4f\n", iteracionActual + 1, preciosIniciales[0], preciosIniciales[1], preciosIniciales[2], preciosIniciales[3], errorMaximo);
        iteracionActual++;
    } while (continuarCalculando && iteracionActual < maximoIteraciones);

    System.out.println("--------------------------------------------------");
    System.out.println("                  Resultados obtenidos");
    System.out.println("--------------------------------------------------");
    System.out.println("Precio estimado de venta de cada carrito el día del niño:");
    for (int j = 0; j < 4; j++) {
        System.out.printf("Carrito Tipo %d: %.2f\n", j + 1, preciosIniciales[j]);
    }
    System.out.println("--------------------------------------------------");
}

public static void metodoGaussJordan(Scanner scanner) {
    System.out.println("--------------------------------------------------");
    System.out.println("                   Método de Gauss-Jordan");
    System.out.println("--------------------------------------------------");
    System.out.println("Precio estimado de venta de cada carrito el día del niño\n");

    double[][] matrizCoeficientes = new double[4][5];
    String[] sucursales = {"Culiacán", "Mazatlán", "Guasave", "Mochis"};

    System.out.println("Ingrese los datos de la matriz (4x5):");
    for (int i = 0; i < 4; i++) {
        System.out.println("Sucursal " + sucursales[i] + ": ");
        for (int j = 0; j < 5; j++) {
            if (j < 4) {
                System.out.print("Venta del carrito tipo " + (j + 1) + ": ");
            } else {
                System.out.print("Total vendido: ");
            }
            matrizCoeficientes[i][j] = scanner.nextDouble();
        }
    }

    imprimirMatriz("Matriz de Datos", matrizCoeficientes);

    int n = matrizCoeficientes.length;

    for (int i = 0; i < n; i++) {
        double pivote = matrizCoeficientes[i][i];

        if (pivote == 0) {
            System.out.println("Error: pivote es cero en la fila " + (i + 1));
            return;
        }

        for (int j = 0; j < n + 1; j++) {
            matrizCoeficientes[i][j] /= pivote;
        }

        for (int k = i + 1; k < n; k++) {
            double factorEliminacion = matrizCoeficientes[k][i];
            for (int j = 0; j < n + 1; j++) {
                matrizCoeficientes[k][j] -= factorEliminacion * matrizCoeficientes[i][j];
            }
        }
    }

    imprimirMatriz("Matriz con ceros debajo de la diagonal", matrizCoeficientes);

    for (int i = n - 1; i >= 0; i--) {
        for (int k = i - 1; k >= 0; k--) {
            double factorEliminacion = matrizCoeficientes[k][i];
            for (int j = 0; j < n + 1; j++) {
                matrizCoeficientes[k][j] -= factorEliminacion * matrizCoeficientes[i][j];
            }
        }
    }

    imprimirMatriz("Matriz con ceros debajo y arriba de la diagonal", matrizCoeficientes);
    imprimirMatriz("Matriz Identidad", matrizCoeficientes);

    System.out.println("--------------------------------------------------");
    System.out.println("                  Resultados obtenidos");
    System.out.println("--------------------------------------------------");
    for (int i = 0; i < n; i++) {
        System.out.printf("Carrito Tipo %d: %.2f\n", i + 1, matrizCoeficientes[i][n]);
    }
    System.out.println("--------------------------------------------------");
}

public static void imprimirMatriz(String titulo, double[][] matriz) {
    System.out.println("--------------------------------------------------");
    System.out.println("                  " + titulo);
    System.out.println("--------------------------------------------------");
    for (double[] fila : matriz) {
        for (double valor : fila) {
            System.out.printf("%10.2f ", valor);
        }
        System.out.println();
    }
}
}
