/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Clase de prueba del obligatorio. Configura la salida estandar en UTF-8 para que
 * se impriman bien todos los caracteres y arranca la interfaz por consola.
 */
public class Prueba {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Sistema sistema = new Sistema();
        Interfaz interfaz = new Interfaz(sistema);
        interfaz.ejecutar();
    }
}
