/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

public class Color {

    // Los 8 colores del enunciado.
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

    // Devuelve null si el codigo no es valido.
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

    public static boolean esValido(char codigo) {
        return porCodigo(codigo) != null;
    }

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

    @Override
    public String toString() {
        return this.nombre;
    }
}
