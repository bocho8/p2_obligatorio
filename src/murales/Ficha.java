/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

public class Ficha {

    public static final int LADO_CHICO = 3;
    public static final int LADO_GRANDE = 5;
    public static final int LARGO_CHICO = LADO_CHICO * LADO_CHICO;
    public static final int LARGO_GRANDE = LADO_GRANDE * LADO_GRANDE;
    public static final char VACIO = '.';

    private String nombre;
    private Color color;
    private String disenoChico;
    private String disenoGrande;

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

    public char getCaracterChico(int fila, int columna) {
        return caracterDe(this.disenoChico, LADO_CHICO, fila, columna);
    }

    public char getCaracterGrande(int fila, int columna) {
        return caracterDe(this.disenoGrande, LADO_GRANDE, fila, columna);
    }

    // El punto del diseno se imprime como espacio.
    private char caracterDe(String diseno, int lado, int fila, int columna) {
        char caracter = diseno.charAt(fila * lado + columna);
        if (caracter == VACIO) {
            caracter = ' ';
        }
        return caracter;
    }

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

    @Override
    public String toString() {
        return this.nombre;
    }
}
