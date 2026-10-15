import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Матрицы комплексных чисел");

        Matrix A = readMatrix("A");
        Matrix B = readMatrix("B");

        while (true) {
            System.out.println("\nВыберите действие");
            System.out.println("1 - A + B");
            System.out.println("2 - A - B");
            System.out.println("3 - A * B");
            System.out.println("4 - A / B");
            System.out.println("5 - Транспонировать A");
            System.out.println("6 - Транспонировать B");
            System.out.println("7 - Определитель A");
            System.out.println("8 - Определитель B");
            System.out.println("9 - Ввести матрицы заново");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.println("A + B =");
                        A.plus(B).print();
                        break;
                    case 2:
                        System.out.println("A - B =");
                        A.minus(B).print();
                        break;
                    case 3:
                        System.out.println("A * B =");
                        A.times(B).print();
                        break;
                    case 4:
                        System.out.println("A / B =");
                        A.div(B).print();
                        break;
                    case 5:
                        System.out.println("A^T =");
                        A.transpose().print();
                        break;
                    case 6:
                        System.out.println("B^T =");
                        B.transpose().print();
                        break;
                    case 7:
                        System.out.println("det(A) = " + A.determinant());
                        break;
                    case 8:
                        System.out.println("det(B) = " + B.determinant());
                        break;
                    case 9:
                        A = readMatrix("A");
                        B = readMatrix("B");
                        break;
                    case 0:
                        System.out.println("Пока!");
                        return;
                    default:
                        System.out.println("Такой команды нет.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    // Прочитать матрицу с клавиатуры
    static Matrix readMatrix(String name) {
        System.out.println("\nВвод матрицы " + name);

        System.out.print("Число строк: ");
        int rows = Integer.parseInt(sc.nextLine());

        System.out.print("Число столбцов: ");
        int cols = Integer.parseInt(sc.nextLine());

        Matrix m = new Matrix(rows, cols);

        System.out.println("Вводите элементы в виде: re im   (например: 2 -3)");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(name + "[" + i + "][" + j + "] = ");
                String line = sc.nextLine();
                String[] parts = line.split(" ");
                double re = Double.parseDouble(parts[0]);
                double im = Double.parseDouble(parts[1]);
                m.set(i, j, new Complex(re, im));
            }
        }

        System.out.println("Матрица " + name + ":");
        m.print();
        return m;
    }
}