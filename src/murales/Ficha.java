/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

/**
 * Ficha de un mural. Tiene un nombre unico en mayusculas, un color y dos disenos:
 * el chico de 3x3 y el grande de 5x5. Los disenos se guardan como texto de 9 y 25
 * caracteres, que lo describen por fila, y el punto representa un espacio en blanco.
 */
public class Ficha {

    public static final int LADO_CHICO = 3;
    public static final int LADO_GRANDE = 5;
    public static final int LARGO_CHICO = LADO_CHICO * LADO_CHICO;
    public static final int LARGO_GRANDE = LADO_GRANDE * LADO_GRANDE;

    /** Caracter que representa un espacio en blanco dentro de un diseno. */
    public static final char VACIO = '.';

    private String nombre;
    private Color color;
    private String disenoChico;
    private String disenoGrande;

    /**
     * Crea una ficha con todos sus datos.
     * Los disenos deben tener exactamente 9 y 25 caracteres.
     */
    public Ficha(String nombre, Color color, String disenoChico, String disenoGrande) {
        if (disenoChico.length() != LARGO_CHICO) {
            throw new IllegalArgumentException("El diseno chico debe tener " + LARGO_CHICO + " caracteres.");
        }
        if (disenoGrande.length() != LARGO_GRANDE) {
            throw new IllegalArgumentException("El diseno grande debe tener " + LARGO_GRANDE + " caracteres.");
        }
        this.nombre = nombre;
        this.color = color;
        this.disenoChico = disenoChico;
        this.disenoGrande = disenoGrande;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Color getColor() {
        return this.color;
    }

    /** Caracter de la fila y columna indicadas del diseno chico (0 a 2). */
    public char getCaracterChico(int fila, int columna) {
        return caracterDe(this.disenoChico, LADO_CHICO, fila, columna);
    }

    /** Caracter de la fila y columna indicadas del diseno grande (0 a 4). */
    public char getCaracterGrande(int fila, int columna) {
        return caracterDe(this.disenoGrande, LADO_GRANDE, fila, columna);
    }

    /**
     * Devuelve el caracter que corresponde a una posicion de un diseno.
     * El punto del diseno se devuelve como espacio, para que no se imprima.
     */
    private char caracterDe(String diseno, int lado, int fila, int columna) {
        char caracter = diseno.charAt(fila * lado + columna);
        if (caracter == VACIO) {
            caracter = ' ';
        }
        return caracter;
    }

    /** Devuelve true si los dos disenos estan formados solamente por puntos. */
    public boolean esVacia() {
        return soloPuntos(this.disenoChico) && soloPuntos(this.disenoGrande);
    }

    private boolean soloPuntos(String diseno) {
        boolean soloPuntos = true;
        int i = 0;
        while (i < diseno.length() && soloPuntos) {
            if (diseno.charAt(i) != VACIO) {
                soloPuntos = false;
            }
            i++;
        }
        return soloPuntos;
    }

    /** Dos fichas son iguales cuando tienen el mismo nombre. */
    public boolean equals(Object objeto) {
        boolean iguales = false;
        if (objeto instanceof Ficha) {
            Ficha otra = (Ficha) objeto;
            iguales = this.nombre.equals(otra.getNombre());
        }
        return iguales;
    }

    public int hashCode() {
        return this.nombre.hashCode();
    }

    public String toString() {
        return this.nombre;
    }
}
