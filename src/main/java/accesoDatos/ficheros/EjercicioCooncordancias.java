package accesoDatos.ficheros;

import java.io.*;
import java.util.Scanner;
import java.util.*;

public class EjercicioCooncordancias {
    static void main(String[] args) throws  InterruptedException {

        int maxBytes = 10000;
        String nombreFichero;
        String nombre;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce tu nombre: ");
        nombre = scanner.nextLine();

        System.out.printf("Buenas %s\n" +
                           "Introduce el nombre de tu fichero", nombre);
        nombreFichero = scanner.nextLine();
        nombreFichero.toString();

        File file = new File(nombreFichero);

        if (file.length() >= maxBytes) {

            System.out.printf("El fichero no se puede leer ni escribir porque supera los %d Bytes", maxBytes);

        } else {

            try (FileWriter fileWriter = new FileWriter(nombreFichero, true)) {

                fileWriter.write(scanner.nextLine());

            } catch (Exception e) {

                throw new RuntimeException(e);

            }

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nombreFichero))) {

                String linea;

                while ((linea = bufferedReader.readLine()) != null) {

                    System.out.println(linea);

                }

            } catch (FileNotFoundException e) {

                throw new RuntimeException(e);

            } catch (IOException e) {

                throw new RuntimeException(e);

            }

        }

    }

}







