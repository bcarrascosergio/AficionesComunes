package accesoDatos.ficheros;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class EjercicioCooncordancias {
    static void main(String[] args) throws  InterruptedException {

        int maxBytes = 10000;
        String nombreFichero;
        String nombre;
        int opcion;

        ArrayList<String> listaUsuarios = new ArrayList<>();
        Scanner teclado = new Scanner(System.in);

        System.out.println("Introduce tu nombre: ");
        nombre = teclado.nextLine();//Pido el texto directamente por consola

        System.out.printf("Buenas %s.\n" +
                          "Introduce el nombre de tu fichero: ", nombre);
        nombreFichero = teclado.nextLine();
        File fichero = new File(nombreFichero);

        if (!fichero.exists()) {
            System.out.println("Error, el fichero indicado no existe.");
            return;
        } else if (fichero.length() > maxBytes) {
            System.out.printf("Error, el fichero supera el máximo de bytes permitido (%s bytes).", maxBytes);
        } else {
            System.out.println("El fichero se ha cargado correctamente.");
        }

        do {
            System.out.println("========== MENÚ PRINCIPAL ==========");
            System.out.println("1. Añadir usuario");
            System.out.println("2. Mostrar usuarios introducidos");
            System.out.println("3. Generar fichero de concordancias");
            System.out.println("4. Salir");
            System.out.println("====================================");
            System.out.println("Seleccione una opción: ");

            opcion = Integer.parseInt(teclado.nextLine()); //Pido el numero int por consola y lee lo que escribe el usuario.

            switch (opcion) {
                case 1:
                    System.out.println("Introduce el nombre del nuevo usuario: ");
                    String nuevoUsuario = teclado.nextLine();

                    if (listaUsuarios.contains(nuevoUsuario)) {
                        throw new IllegalArgumentException("El usuario ya existe");
                    } else {

                        listaUsuarios.add(nuevoUsuario);
                        System.out.printf("El nuevo usuario es %s", nuevoUsuario);
                        break;
                    }

                case 2:
                    System.out.println("Estos son los usuarios que existen: ");

                case 3:

                case 4:
                    System.out.println("¿Seguro que quieres salir? (Y/N): ");
                    String confirmacion = teclado.nextLine();

                    if (confirmacion.equalsIgnoreCase("Y")) {
                        System.out.println("Saliendo del programa.");
                        opcion = 4;

                    } else {
                        System.out.println("Volviendo al menú principal");
                    }
                    break;

                default:
                    System.out.println("Esa opción no es valida");
                    break;

            }

        } while (opcion != 4);
        teclado.close();







        if (fichero.length() >= maxBytes) {

            System.out.printf("El fichero no se puede leer ni escribir porque supera los %d Bytes", maxBytes);

        } else {

            try (FileWriter fileWriter = new FileWriter(nombreFichero, true)) {

                fileWriter.write(teclado.nextLine());

            } catch (Exception e) {

                throw new RuntimeException(e);

            }

            List<Usuario> usuarios = new ArrayList<>();

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nombreFichero))) {

                String linea;

                while ((linea = bufferedReader.readLine()) != null) {

                    String[] partes = linea.split(" ");
                    String codigo = partes[0];

                    List<String> aficiones = new ArrayList<>();
                    for (int i = 1; i < partes.length; i++) {
                        aficiones.add(partes[i]);
                    }

                    Usuario usuario = new Usuario(codigo, aficiones);
                    usuarios.add(usuario);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);

            }

        }
    }
}









