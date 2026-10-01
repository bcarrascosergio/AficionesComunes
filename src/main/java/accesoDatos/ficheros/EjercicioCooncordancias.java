package accesoDatos.ficheros;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

//Hay que hacer que el usuario introduzca sus aficiones en una sola linea y que no tenga que estar escribiendo mucho

public class EjercicioCooncordancias {
    public static void main(String[] args)  {

        int maxBytes = 10000;
        String nombreFichero;
        String nombre;
        int opcion;

        Scanner teclado = new Scanner(System.in);

        System.out.println("Introduce tu nombre: ");
        nombre = teclado.nextLine();//Pido el texto directamente por consola

        System.out.printf("Buenas %s.\n", nombre);
        File fichero;
        boolean ficheroValido = false;

        do {
            System.out.println("Introduce el nombre de tu fichero: ");
            nombreFichero = teclado.nextLine().trim();
            fichero = new File(nombreFichero);

            if (!fichero.exists()) {
                System.out.println("Error, el fichero indicado no existe.");

            } else if (fichero.length() > maxBytes) {
                System.out.printf("Error, el fichero supera el máximo de bytes permitido (%s bytes).", maxBytes);

            } else {
                ficheroValido = true;
                System.out.println("El fichero se ha cargado correctamente.");
            }
        } while (!ficheroValido);

        List<Usuario> listaUsuarios = new ArrayList<>();
        //Lectura del fichero
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
                listaUsuarios.add(usuario);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        do {
            System.out.println("========== MENÚ PRINCIPAL ==========");
            System.out.println("1. Añadir usuario");
            System.out.println("2. Mostrar lista de usuarios introducidos");
            System.out.println("3. Generar fichero de concordancias");
            System.out.println("4. Salir");
            System.out.println("====================================");
            System.out.println("Seleccione una opción: ");

            opcion = Integer.parseInt(teclado.nextLine()); //Pido el número int por consola y lee lo que escribe el usuario.

            switch (opcion) {
                case 1:
                    //Aquí creo el codigo de manera dinámica, para que cuando se vaya a añadir un nuevo Usuario, se le asigne directamente empezando por el U100.
                    String nuevoCodigo;
                    if(listaUsuarios.isEmpty()) {
                        nuevoCodigo = "U100";
                    } else {
                        Usuario ultimoUsuario = listaUsuarios.get(listaUsuarios.size() - 1);
                        int siguienteNumero = Integer.parseInt(ultimoUsuario.getCodigo().substring(1)) + 1;
                        nuevoCodigo = "U" + siguienteNumero;
                    }

                    System.out.println("El código asignado al nuevo usuario es: " + nuevoCodigo);
                    //Le pido al usuario que introduzca las aficiones en una linea y esto lo guardaré en la variable.trim para quitarle los espacios de alante y atrás para que no los pille como caracteres vacios.
                    System.out.println("Introduce las aficiones separadas por espacios: ");
                    String aficionesUsuario = teclado.nextLine().trim();

                    if (aficionesUsuario.isEmpty()) {
                        System.out.println("Error: Un usuario debe tener por lo menos una afición");
                        break;
                    }

                    //Aquí almacenamos cada una de las aficiones separadas por el .split quitando los espacios en blanco y poniéndolas en mayúsculas.
                    String[] partesAficiones = aficionesUsuario.toUpperCase().split(" ");
                    List<String> aficionesNuevo = new ArrayList<>();
                    for (String aficion : partesAficiones) {
                        if (!aficion.isEmpty()) {
                            aficionesNuevo.add(aficion);
                        }
                    }

                    //Creo el nuevo objeto y lo añado a la lista.
                    Usuario nuevoUsuario = new Usuario(nuevoCodigo, aficionesNuevo);
                    listaUsuarios.add(nuevoUsuario);

                    //Lo escribimos en el fichero original pero al final.
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero, true))) {
                        bw.newLine();
                        bw.write(nuevoCodigo + " " + aficionesUsuario.toUpperCase());
                        System.out.println("El usuario se ha guardado correctamente.");

                    } catch (IOException e) {
                        System.out.println("Error al escribir en el fichero: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Estos son los listaUsuarios que existen: ");
                    for (Usuario usuario : listaUsuarios) {
                        System.out.println(usuario.getCodigo() + ": " + usuario.getAficiones());
                    }
                    break;

                case 3:
                    System.out.println("Introduce el número mínimo de concordancias: ");
                    int minConcordancias = Integer.parseInt(teclado.nextLine());

                    if (minConcordancias < 1) {
                        System.out.println("Error, el mínimo de concordancias tiene que ser de 1");
                        break;
                    }

                    //si el numero de aficiones comunes es mayor o igual que 1, está bien. Si no excepcion.
                    //listaUsuarios tiene 3 usuarios, 0-1-2.
                    //Utilizar 2 bucles anidados para comparar el primer usuario con el que va después de él.

                    List<Concordancia> listaConcordancias = new ArrayList<>();

                    for (int i = 0; i < listaUsuarios.size();i++) {
                        for (int j = i + 1; j < listaUsuarios.size(); j++) {
                            Usuario usuario1 = listaUsuarios.get(i);
                            Usuario usuario2 = listaUsuarios.get(j);
                            //Con esto lo que hacemos es comparar las aficiones de ambos usuarios.


                            List<String> aficionesComunes = new ArrayList<>();
                            for (String aficion : usuario1.getAficiones()) {
                                if (usuario2.getAficiones().contains(aficion)) {
                                    aficionesComunes.add(aficion);
                                }
                            }

                            if (aficionesComunes.size() >= minConcordancias) {
                                listaConcordancias.add(new Concordancia(usuario1.getCodigo(), usuario2.getCodigo(), aficionesComunes));
                            }
                        }
                    }
                    if (listaConcordancias.isEmpty()) {
                        System.out.println("No se han encontrado parejas con el mínimo de concordancia");
                    } else {
                        //Ordeno de mayor a menor el número de concordancias
                        listaConcordancias.sort((concordancia1, concordancia2)  -> Integer.compare(concordancia2.getNumeroConcordancias(), concordancia1.getNumeroConcordancias()));

                        //Escribo todas las parejas en el fichero concordancias.txt
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter("concordancias.txt"))) {
                            for (Concordancia concordancia : listaConcordancias) {
                                bw.write(concordancia.aTextoFichero());
                                bw.newLine();
                            }
                            System.out.println("Se ha creado correctamente el fichero concordancias.txt");

                        } catch (IOException e) {
                            System.out.println("Error al escribir el fichero concordancias.txt: " + e.getMessage());
                        }
                    }
                    break;


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

    }
}




