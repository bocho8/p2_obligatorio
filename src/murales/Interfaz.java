/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Interfaz por consola del sistema de murales: el menu principal, el submenu de
 * modificacion, la lectura y validacion de los datos y la visualizacion de las
 * fichas y de los murales en los dos tamanos.
 */
public class Interfaz {

    /** Autores del obligatorio, que se muestran en la opcion 1. */
    private static final String AUTOR_UNO = "Agustín Roizen - 350021";
    private static final String AUTOR_DOS = "Juan Francisco Tarigo - 391925";

    /** Secuencia ANSI que vuelve al color normal de la consola. */
    private static final String FIN_COLOR = "\u001B[0m";

    /** Si se pone en false, la salida no lleva codigos de color. */
    private static final boolean USAR_COLOR = true;

    private Sistema sistema;
    private Scanner entrada;

    public Interfaz(Sistema sistema) {
        this.sistema = sistema;
        this.entrada = new Scanner(System.in);
    }

    /** Muestra el menu principal hasta que se elige la opcion de terminar. */
    public void ejecutar() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = leerEnteroEnRango("Ingrese una opcion: ", 0, 9);
            ejecutarOpcion(opcion);
        }
        System.out.println("Fin del programa.");
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("================ MURALES ASCII ================");
        System.out.println("0) Terminar");
        System.out.println("1) Información de autores del obligatorio");
        System.out.println("2) Registrar diseñador");
        System.out.println("3) Registrar ficha");
        System.out.println("4) Creación de mural");
        System.out.println("5) Modificar mural");
        System.out.println("6) Visualizar mural");
        System.out.println("7) Listado de diseñadores");
        System.out.println("8) Comparar similitud de murales");
        System.out.println("9) Visualizar todas las fichas");
        System.out.println("==============================================");
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                mostrarAutores();
                break;
            case 2:
                registrarDisenador();
                break;
            case 3:
                registrarFicha();
                break;
            case 4:
                crearMural();
                break;
            case 5:
                modificarMural();
                break;
            case 6:
                visualizarMural();
                break;
            case 7:
                listarDisenadores();
                break;
            case 8:
                compararMurales();
                break;
            case 9:
                visualizarFichas();
                break;
            default:
                // La opcion 0 termina el programa y no hace nada mas.
                break;
        }
    }

    /** Opcion 1: muestra el nombre y el numero de estudiante de los autores. */
    private void mostrarAutores() {
        System.out.println("Autores del obligatorio:");
        System.out.println(AUTOR_UNO);
        System.out.println(AUTOR_DOS);
    }

    /** Opcion 2: registra un disenador con nombre unico, direccion y mail. */
    private void registrarDisenador() {
        System.out.println("--- Registrar diseñador ---");
        String nombre = leerNombreDisenador();
        String direccion = leerTextoNoVacio("Dirección: ");
        String mail = leerTexto("Mail: ");
        this.sistema.registrarDisenador(nombre, direccion, mail);
        System.out.println("Diseñador registrado: " + nombre);
    }

    /** Opcion 3: registra una ficha con nombre unico, color y los dos disenos. */
    private void registrarFicha() {
        System.out.println("--- Registrar ficha ---");
        String nombre = leerNombreFicha();
        char codigoColor = leerColor();
        String disenoChico = leerDiseno(Ficha.LARGO_CHICO);
        String disenoGrande = leerDiseno(Ficha.LARGO_GRANDE);
        this.sistema.registrarFicha(nombre, Color.porCodigo(codigoColor), disenoChico, disenoGrande);
        System.out.println("Ficha registrada: " + nombre);
    }

    /** Opcion 4: crea un mural de un disenador con el formato elegido. */
    private void crearMural() {
        System.out.println("--- Creación de mural ---");
        Disenador disenador = elegirDisenador();
        if (disenador != null) {
            String nombre = leerNombreMural();
            char formato = leerFormato();
            Ficha fichaUno = elegirFicha("Primera ficha");
            Ficha fichaDos = elegirFichaDistinta("Segunda ficha", fichaUno);
            Mural mural = this.sistema.crearMural(disenador, nombre, formato, fichaUno, fichaDos);
            if (formato == Mural.MANUAL) {
                agregarFichasManual(mural, fichaUno, fichaDos);
            }
            mural.guardarOriginal();
            System.out.println("Mural creado por " + disenador.getNombre() + " con formato "
                    + Mural.nombreFormato(formato) + ".");
            mostrarMural(mural, true);
            mostrarMural(mural, false);
        }
    }

    /** Opcion 5: submenu con las modificaciones posibles sobre un mural. */
    private void modificarMural() {
        System.out.println("--- Modificar mural ---");
        Mural mural = elegirMural();
        if (mural != null) {
            int opcion = -1;
            while (opcion != 0) {
                mostrarSubmenuModificar(mural);
                opcion = leerEnteroEnRango("Ingrese una opcion: ", 0, 3);
                if (opcion == 1) {
                    modificarPosicion(mural);
                }
                if (opcion == 2) {
                    modificarFicha(mural);
                }
                if (opcion == 3) {
                    restaurarMural(mural);
                }
            }
        }
    }

    private void mostrarSubmenuModificar(Mural mural) {
        System.out.println();
        System.out.println("--- Mural " + mural.getNombre() + " ---");
        System.out.println("0) Volver al menú anterior");
        System.out.println("1) Modificar posición");
        System.out.println("2) Modificar ficha");
        System.out.println("3) Restaurar");
    }

    private void modificarPosicion(Mural mural) {
        int fila = leerEnteroEnRango("Fila (1 a " + Mural.FILAS + "): ", 1, Mural.FILAS) - 1;
        int columna = leerEnteroEnRango("Columna (1 a " + Mural.COLUMNAS + "): ", 1, Mural.COLUMNAS) - 1;
        Ficha ficha = elegirFicha("Ficha para esa posición");
        mural.setFicha(fila, columna, ficha);
        System.out.println("Posición modificada.");
        mostrarMural(mural, true);
        mostrarMural(mural, false);
    }

    private void modificarFicha(Mural mural) {
        ArrayList<Ficha> fichasDelMural = mural.getFichasDiferentes();
        System.out.println("Fichas que incluye el mural:");
        mostrarFichasNumeradas(fichasDelMural);
        int numero = leerEnteroEnRango("Elija la ficha a reemplazar: ", 1, fichasDelMural.size());
        Ficha vieja = fichasDelMural.get(numero - 1);
        Ficha nueva = elegirFicha("Ficha que la reemplaza");
        mural.reemplazarFicha(vieja, nueva);
        System.out.println("Ficha " + vieja.getNombre() + " reemplazada por " + nueva.getNombre() + ".");
        mostrarMural(mural, true);
        mostrarMural(mural, false);
    }

    private void restaurarMural(Mural mural) {
        mural.restaurar();
        System.out.println("Mural restaurado al estado original.");
        mostrarMural(mural, true);
        mostrarMural(mural, false);
    }

    /** Opcion 6: muestra un mural y alterna el tamano con ENTER hasta que se ingresa S. */
    private void visualizarMural() {
        System.out.println("--- Visualizar mural ---");
        Disenador disenador = elegirDisenador();
        if (disenador != null) {
            if (disenador.getMurales().isEmpty()) {
                System.out.println("Ese diseñador no tiene murales.");
            } else {
                Mural mural = elegirMuralDe(disenador);
                boolean grande = leerTamanoGrande();
                boolean salir = false;
                while (!salir) {
                    mostrarMural(mural, grande);
                    System.out.println("Presione ENTER para ver el otro tamaño, o S para salir.");
                    String linea = this.entrada.nextLine().trim();
                    if (linea.equalsIgnoreCase("S")) {
                        salir = true;
                    } else {
                        if (linea.isEmpty()) {
                            grande = !grande;
                        } else {
                            System.out.println("Debe presionar ENTER o ingresar S.");
                        }
                    }
                }
            }
        }
    }

    /** Opcion 7: lista los disenadores ordenados alfabeticamente con todos sus datos. */
    private void listarDisenadores() {
        System.out.println("--- Listado de diseñadores ---");
        ArrayList<Disenador> ordenados = new ArrayList<Disenador>(this.sistema.getDisenadores());
        Collections.sort(ordenados);
        if (ordenados.isEmpty()) {
            System.out.println("No hay diseñadores registrados.");
        } else {
            int i = 0;
            while (i < ordenados.size()) {
                mostrarDisenador(ordenados.get(i));
                i++;
            }
        }
    }

    private void mostrarDisenador(Disenador disenador) {
        System.out.println();
        System.out.println("Nombre: " + disenador.getNombre());
        System.out.println("Dirección: " + disenador.getDireccion());
        System.out.println("Mail: " + disenador.getMail());
        System.out.println("Cantidad de murales: " + disenador.cantidadMurales());
        System.out.println("Fichas diferentes usadas: " + disenador.getFichasDiferentes().size());
        System.out.println("Colores diferentes usados: " + disenador.getColoresDiferentes().size());
        int i = 0;
        while (i < disenador.getMurales().size()) {
            System.out.println("  Mural: " + disenador.getMurales().get(i).getNombre());
            i++;
        }
    }

    /**
     * Opcion 8: busca un area rectangular de un mural dentro de los demas murales.
     * No se busca dentro del propio mural original, solo en los otros.
     */
    private void compararMurales() {
        System.out.println("--- Comparar similitud de murales ---");
        Mural original = elegirMural();
        if (original != null) {
            int fila = leerEnteroEnRango("Fila inicial (1 a " + Mural.FILAS + "): ", 1, Mural.FILAS) - 1;
            int columna = leerEnteroEnRango("Columna inicial (1 a " + Mural.COLUMNAS + "): ", 1, Mural.COLUMNAS) - 1;
            int cantidadFilas = leerEnteroEnRango("Cantidad de filas (1 a " + (Mural.FILAS - fila) + "): ",
                    1, Mural.FILAS - fila);
            int cantidadColumnas = leerEnteroEnRango("Cantidad de columnas (1 a " + (Mural.COLUMNAS - columna) + "): ",
                    1, Mural.COLUMNAS - columna);
            Ficha[][] area = original.getArea(fila, columna, cantidadFilas, cantidadColumnas);
            boolean encontrado = false;
            ArrayList<Mural> murales = this.sistema.getMurales();
            int i = 0;
            while (i < murales.size()) {
                Mural otro = murales.get(i);
                if (otro != original) {
                    int[] posicion = otro.buscarArea(area);
                    if (posicion != null) {
                        System.out.println("El mural " + otro.getNombre() + " tiene esa área en la fila "
                                + (posicion[0] + 1) + ", columna " + (posicion[1] + 1) + ".");
                        encontrado = true;
                    }
                }
                i++;
            }
            if (!encontrado) {
                System.out.println("Ningún otro mural tiene esa área.");
            }
        }
    }

    /** Opcion 9: muestra todas las fichas registradas con sus dos disenos. */
    private void visualizarFichas() {
        System.out.println("--- Visualizar todas las fichas ---");
        ArrayList<Ficha> fichas = this.sistema.getFichas();
        int i = 0;
        while (i < fichas.size()) {
            mostrarFicha(fichas.get(i));
            i++;
        }
    }

    /** Muestra una ficha con el formato pedido en el obligatorio. */
    private void mostrarFicha(Ficha ficha) {
        System.out.println();
        System.out.println("Ficha: " + ficha.getNombre());
        System.out.println("Color: " + ficha.getColor().getCodigo());
        System.out.println("Tamaño chico:");
        mostrarDiseno(ficha, false);
        System.out.println("Tamaño grande:");
        mostrarDiseno(ficha, true);
    }

    /** Muestra el diseno de una ficha, en color, con el tamano indicado. */
    private void mostrarDiseno(Ficha ficha, boolean grande) {
        int lado = ladoDe(grande);
        int fila = 0;
        while (fila < lado) {
            String linea = codigoAnsi(ficha.getColor());
            int columna = 0;
            while (columna < lado) {
                linea = linea + caracterDe(ficha, fila, columna, grande);
                columna++;
            }
            linea = linea + FIN_COLOR;
            System.out.println(linea);
            fila++;
        }
    }

    /**
     * Muestra un mural completo en el tamano indicado.
     * Cada ficha ocupa 3x3 o 5x5 posiciones de la salida, asi que cada fila del
     * mural se imprime en varias lineas de caracteres.
     */
    private void mostrarMural(Mural mural, boolean grande) {
        System.out.println();
        System.out.println("Mural: " + mural.getNombre() + " (formato " + Mural.nombreFormato(mural.getFormato())
                + ", tamaño " + nombreTamano(grande) + ")");
        int lado = ladoDe(grande);
        int filaMural = 0;
        while (filaMural < Mural.FILAS) {
            int filaFicha = 0;
            while (filaFicha < lado) {
                String linea = "";
                int columnaMural = 0;
                while (columnaMural < Mural.COLUMNAS) {
                    Ficha ficha = mural.getFicha(filaMural, columnaMural);
                    linea = linea + codigoAnsi(ficha.getColor());
                    int columnaFicha = 0;
                    while (columnaFicha < lado) {
                        linea = linea + caracterDe(ficha, filaFicha, columnaFicha, grande);
                        columnaFicha++;
                    }
                    linea = linea + FIN_COLOR;
                    columnaMural++;
                }
                System.out.println(linea);
                filaFicha++;
            }
            filaMural++;
        }
    }

    /** Devuelve el caracter de una posicion de la ficha, en el tamano indicado. */
    private char caracterDe(Ficha ficha, int fila, int columna, boolean grande) {
        char caracter;
        if (grande) {
            caracter = ficha.getCaracterGrande(fila, columna);
        } else {
            caracter = ficha.getCaracterChico(fila, columna);
        }
        return caracter;
    }

    private int ladoDe(boolean grande) {
        int lado;
        if (grande) {
            lado = Ficha.LADO_GRANDE;
        } else {
            lado = Ficha.LADO_CHICO;
        }
        return lado;
    }

    private String nombreTamano(boolean grande) {
        String nombre;
        if (grande) {
            nombre = "grande";
        } else {
            nombre = "chico";
        }
        return nombre;
    }

    /** Devuelve el codigo ANSI del color, o texto vacio si el color esta desactivado. */
    private String codigoAnsi(Color color) {
        String codigo = "";
        if (USAR_COLOR) {
            if (color.getCodigo() == 'R') {
                codigo = "\u001B[31m";
            }
            if (color.getCodigo() == 'A') {
                codigo = "\u001B[34m";
            }
            if (color.getCodigo() == 'B') {
                codigo = "\u001B[37m";
            }
            if (color.getCodigo() == 'N') {
                codigo = "\u001B[30m";
            }
            if (color.getCodigo() == 'M') {
                codigo = "\u001B[33m";
            }
            if (color.getCodigo() == 'V') {
                codigo = "\u001B[32m";
            }
            if (color.getCodigo() == 'G') {
                codigo = "\u001B[35m";
            }
            if (color.getCodigo() == 'C') {
                codigo = "\u001B[36m";
            }
        }
        return codigo;
    }

    /** Muestra los disenadores registrados, numerados, para poder elegir uno. */
    private Disenador elegirDisenador() {
        Disenador elegido = null;
        ArrayList<Disenador> disenadores = this.sistema.getDisenadores();
        if (disenadores.isEmpty()) {
            System.out.println("No hay diseñadores registrados.");
        } else {
            int i = 0;
            while (i < disenadores.size()) {
                System.out.println((i + 1) + ") " + disenadores.get(i).getNombre());
                i++;
            }
            int numero = leerEnteroEnRango("Elija un diseñador: ", 1, disenadores.size());
            elegido = disenadores.get(numero - 1);
        }
        return elegido;
    }

    /** Muestra todos los murales con su disenador, numerados, para poder elegir uno. */
    private Mural elegirMural() {
        Mural elegido = null;
        ArrayList<Mural> murales = this.sistema.getMurales();
        if (murales.isEmpty()) {
            System.out.println("No hay murales creados.");
        } else {
            int numero = 1;
            int i = 0;
            while (i < this.sistema.getDisenadores().size()) {
                Disenador disenador = this.sistema.getDisenadores().get(i);
                int j = 0;
                while (j < disenador.getMurales().size()) {
                    System.out.println(numero + ") " + disenador.getMurales().get(j).getNombre()
                            + " (" + disenador.getNombre() + ")");
                    numero++;
                    j++;
                }
                i++;
            }
            int elegidoNumero = leerEnteroEnRango("Elija un mural: ", 1, murales.size());
            elegido = murales.get(elegidoNumero - 1);
        }
        return elegido;
    }

    /** Muestra los murales de un disenador, numerados, para poder elegir uno. */
    private Mural elegirMuralDe(Disenador disenador) {
        ArrayList<Mural> murales = disenador.getMurales();
        int i = 0;
        while (i < murales.size()) {
            System.out.println((i + 1) + ") " + murales.get(i).getNombre());
            i++;
        }
        int numero = leerEnteroEnRango("Elija un mural: ", 1, murales.size());
        return murales.get(numero - 1);
    }

    /** Muestra todas las fichas registradas, numeradas, y devuelve la elegida. */
    private Ficha elegirFicha(String titulo) {
        ArrayList<Ficha> fichas = this.sistema.getFichas();
        System.out.println(titulo + ":");
        mostrarFichasNumeradas(fichas);
        int numero = leerEnteroEnRango("Elija una ficha: ", 1, fichas.size());
        return fichas.get(numero - 1);
    }

    /** Igual que elegirFicha, pero la ficha elegida debe ser distinta de la recibida. */
    private Ficha elegirFichaDistinta(String titulo, Ficha otra) {
        Ficha elegida = elegirFicha(titulo);
        while (elegida.equals(otra)) {
            System.out.println("Error: la segunda ficha debe ser diferente de la primera.");
            elegida = elegirFicha(titulo);
        }
        return elegida;
    }

    private void mostrarFichasNumeradas(ArrayList<Ficha> fichas) {
        int i = 0;
        while (i < fichas.size()) {
            System.out.println((i + 1) + ") " + fichas.get(i).getNombre()
                    + " (" + fichas.get(i).getColor().getNombre() + ")");
            i++;
        }
    }

    /**
     * Formato manual: agrega fichas en las posiciones que indica el usuario,
     * mientras responda que si a la pregunta de seguir agregando.
     */
    private void agregarFichasManual(Mural mural, Ficha fichaUno, Ficha fichaDos) {
        System.out.println("Mural manual: el mural empieza vacío.");
        boolean seguir = true;
        while (seguir) {
            int fila = leerEnteroEnRango("Fila (1 a " + Mural.FILAS + "): ", 1, Mural.FILAS) - 1;
            int columna = leerEnteroEnRango("Columna (1 a " + Mural.COLUMNAS + "): ", 1, Mural.COLUMNAS) - 1;
            int cual = leerEnteroEnRango("Ficha (1: " + fichaUno.getNombre()
                    + ", 2: " + fichaDos.getNombre() + "): ", 1, 2);
            if (cual == 1) {
                mural.setFicha(fila, columna, fichaUno);
            } else {
                mural.setFicha(fila, columna, fichaDos);
            }
            seguir = leerSiNo("¿Agregar otra ficha? (S/N): ");
        }
    }

    private String leerNombreDisenador() {
        String nombre = leerTextoNoVacio("Nombre del diseñador: ");
        while (this.sistema.existeDisenador(nombre)) {
            System.out.println("Error: ya existe un diseñador con ese nombre.");
            nombre = leerTextoNoVacio("Nombre del diseñador: ");
        }
        return nombre;
    }

    private String leerNombreFicha() {
        String nombre = leerTextoNoVacio("Nombre de la ficha: ").toUpperCase();
        while (this.sistema.existeFicha(nombre)) {
            System.out.println("Error: ya existe una ficha con ese nombre.");
            nombre = leerTextoNoVacio("Nombre de la ficha: ").toUpperCase();
        }
        return nombre;
    }

    private String leerNombreMural() {
        String nombre = leerTextoNoVacio("Nombre del mural: ");
        while (this.sistema.existeMural(nombre)) {
            System.out.println("Error: ya existe un mural con ese nombre.");
            nombre = leerTextoNoVacio("Nombre del mural: ");
        }
        return nombre;
    }

    private char leerColor() {
        String texto = leerTexto("Color (" + Color.descripcion() + "): ").toUpperCase();
        while (texto.length() != 1 || !Color.esValido(texto.charAt(0))) {
            System.out.println("Error: el color debe ser uno de " + Color.codigosValidos() + ".");
            texto = leerTexto("Color: ").toUpperCase();
        }
        return texto.charAt(0);
    }

    private char leerFormato() {
        System.out.println("Formatos: D damero, R random, F alternado por filas, B borde exterior, M manual.");
        String texto = leerTexto("Formato: ").toUpperCase();
        while (texto.length() != 1 || Mural.formatosValidos().indexOf(texto.charAt(0)) < 0) {
            System.out.println("Error: el formato debe ser uno de " + Mural.formatosValidos() + ".");
            texto = leerTexto("Formato: ").toUpperCase();
        }
        return texto.charAt(0);
    }

    private boolean leerTamanoGrande() {
        String texto = leerTexto("Tamaño (C: chico, G: grande): ").toUpperCase();
        while (!texto.equals("C") && !texto.equals("G")) {
            System.out.println("Error: debe ingresar C o G.");
            texto = leerTexto("Tamaño (C: chico, G: grande): ").toUpperCase();
        }
        return texto.equals("G");
    }

    /** Lee un diseno y repite el pedido hasta que tenga el largo exacto. */
    private String leerDiseno(int largo) {
        System.out.println("Ingrese el diseño de " + largo + " caracteres (el punto es un espacio en blanco):");
        String diseno = this.entrada.nextLine();
        while (diseno.length() != largo) {
            System.out.println("Error: el diseño debe tener exactamente " + largo + " caracteres, "
                    + "y el ingresado tiene " + diseno.length() + ".");
            diseno = this.entrada.nextLine();
        }
        return diseno;
    }

    /** Lee un numero entero, repitiendo el pedido mientras no sea un numero valido. */
    private int leerEntero(String mensaje) {
        int numero = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(mensaje);
            String linea = this.entrada.nextLine().trim();
            try {
                numero = Integer.parseInt(linea);
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero.");
            }
        }
        return numero;
    }

    /** Lee un numero entero dentro del rango indicado, con los dos extremos incluidos. */
    private int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        int numero = leerEntero(mensaje);
        while (numero < minimo || numero > maximo) {
            System.out.println("Error: el número debe estar entre " + minimo + " y " + maximo + ".");
            numero = leerEntero(mensaje);
        }
        return numero;
    }

    /** Lee un texto, que puede quedar vacio. */
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return this.entrada.nextLine().trim();
    }

    /** Lee un texto, repitiendo el pedido mientras quede vacio. */
    private String leerTextoNoVacio(String mensaje) {
        String texto = leerTexto(mensaje);
        while (texto.isEmpty()) {
            System.out.println("Error: el texto no puede quedar vacío.");
            texto = leerTexto(mensaje);
        }
        return texto;
    }

    /** Lee S o N y devuelve true cuando la respuesta es S. */
    private boolean leerSiNo(String mensaje) {
        String texto = leerTexto(mensaje).toUpperCase();
        while (!texto.equals("S") && !texto.equals("N")) {
            System.out.println("Error: debe ingresar S o N.");
            texto = leerTexto(mensaje).toUpperCase();
        }
        return texto.equals("S");
    }
}
