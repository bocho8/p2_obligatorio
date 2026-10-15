/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

/**
 * Color de una ficha. Cada color tiene un codigo de una letra, que es el que se
 * ingresa por consola, y un nombre.
 */
public class Color {

    /** Los 8 colores disponibles, en el orden del enunciado. */
    private static final Color[] COLORES = {
        new Color('R', "rojo"),
        new Color('A', "azul"),
        new Color('B', "blanco"),
        new Color('N', "negro"),
        new Color('M', "amarillo"),
        new Color('V', "verde"),
        new Color('G', "magenta"),
        new Color('C', "celeste")
    };

    private char codigo;
    private String nombre;

    private Color(char codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public char getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    /**
     * Devuelve el color del codigo recibido.
     * Devuelve null si el codigo no corresponde a ningun color.
     */
    public static Color porCodigo(char codigo) {
        Color encontrado = null;
        int i = 0;
        while (i < COLORES.length && encontrado == null) {
            if (COLORES[i].getCodigo() == codigo) {
                encontrado = COLORES[i];
            }
            i++;
        }
        return encontrado;
    }

    /** Devuelve true si el codigo recibido corresponde a algun color. */
    public static boolean esValido(char codigo) {
        return porCodigo(codigo) != null;
    }

    /** Lista de codigos validos, para mostrar en los mensajes por consola. */
    public static String codigosValidos() {
        String lista = "";
        int i = 0;
        while (i < COLORES.length) {
            if (i > 0) {
                lista = lista + ", ";
            }
            lista = lista + COLORES[i].getCodigo();
            i++;
        }
        return lista;
    }

    /** Descripcion de los colores con su nombre, para mostrar en los mensajes. */
    public static String descripcion() {
        String lista = "";
        int i = 0;
        while (i < COLORES.length) {
            if (i > 0) {
                lista = lista + ", ";
            }
            lista = lista + COLORES[i].getCodigo() + ": " + COLORES[i].getNombre();
            i++;
        }
        return lista;
    }

    /** Dos colores son iguales cuando tienen el mismo codigo. */
    public boolean equals(Object objeto) {
        boolean iguales = false;
        if (objeto instanceof Color) {
            Color otro = (Color) objeto;
            iguales = this.codigo == otro.getCodigo();
        }
        return iguales;
    }

    public int hashCode() {
        return this.codigo;
    }

    public String toString() {
        return this.nombre;
    }
}
