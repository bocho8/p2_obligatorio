/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Mural de 10 filas por 12 columnas. Cada posicion tiene una ficha. Guarda ademas
 * una copia del estado con el que fue creado, para poder restaurarlo despues.
 */
public class Mural {

    public static final int FILAS = 10;
    public static final int COLUMNAS = 12;

    /** Formato damero: las dos fichas alternan como un tablero de ajedrez. */
    public static final char DAMERO = 'D';
    /** Formato random: cantidad y posiciones al azar. */
    public static final char RANDOM = 'R';
    /** Formato alternado por filas: cada fila entera con la misma ficha. */
    public static final char ALTERNADO = 'F';
    /** Formato borde exterior: el borde con la primera ficha y el resto con la segunda. */
    public static final char BORDE = 'B';
    /** Formato manual: se empieza vacio y el usuario agrega las fichas. */
    public static final char MANUAL = 'M';

    private static final Random AZAR = new Random();

    private String nombre;
    private char formato;
    private Ficha[][] matriz;
    private Ficha[][] original;

    /**
     * Crea el mural con el formato indicado, usando las dos fichas elegidas.
     * El formato manual deja el mural vacio: las fichas se agregan despues.
     */
    public Mural(String nombre, char formato, Ficha fichaUno, Ficha fichaDos, Ficha fichaVacia) {
        this.nombre = nombre;
        this.formato = formato;
        this.matriz = new Ficha[FILAS][COLUMNAS];
        this.original = new Ficha[FILAS][COLUMNAS];
        llenarConFicha(fichaVacia);
        if (formato == DAMERO) {
            armarDamero(fichaUno, fichaDos);
        }
        if (formato == RANDOM) {
            armarRandom(fichaUno, fichaDos);
        }
        if (formato == ALTERNADO) {
            armarAlternado(fichaUno, fichaDos);
        }
        if (formato == BORDE) {
            armarBorde(fichaUno, fichaDos);
        }
        guardarOriginal();
    }

    public String getNombre() {
        return this.nombre;
    }

    public char getFormato() {
        return this.formato;
    }

    public Ficha getFicha(int fila, int columna) {
        return this.matriz[fila][columna];
    }

    public void setFicha(int fila, int columna, Ficha ficha) {
        this.matriz[fila][columna] = ficha;
    }

    /** Lista de codigos de formato validos, para mostrar en los mensajes. */
    public static String formatosValidos() {
        return "" + DAMERO + RANDOM + ALTERNADO + BORDE + MANUAL;
    }

    /** Nombre del formato, para mostrar en los mensajes. */
    public static String nombreFormato(char formato) {
        String nombre = "desconocido";
        if (formato == DAMERO) {
            nombre = "damero";
        }
        if (formato == RANDOM) {
            nombre = "random";
        }
        if (formato == ALTERNADO) {
            nombre = "alternado por filas";
        }
        if (formato == BORDE) {
            nombre = "borde exterior";
        }
        if (formato == MANUAL) {
            nombre = "manual";
        }
        return nombre;
    }

    private void llenarConFicha(Ficha ficha) {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                this.matriz[fila][columna] = ficha;
            }
        }
    }

    private void armarDamero(Ficha fichaUno, Ficha fichaDos) {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if ((fila + columna) % 2 == 0) {
                    this.matriz[fila][columna] = fichaUno;
                } else {
                    this.matriz[fila][columna] = fichaDos;
                }
            }
        }
    }

    /**
     * Reparte las dos fichas al azar: primero se sortea cuantas posiciones quedan
     * ocupadas, despues cuantas de esas son de la primera ficha, y por ultimo se
     * eligen esas posiciones al azar sobre un listado mezclado.
     */
    private void armarRandom(Ficha fichaUno, Ficha fichaDos) {
        ArrayList<Integer> posiciones = new ArrayList<Integer>();
        for (int i = 0; i < FILAS * COLUMNAS; i++) {
            posiciones.add(i);
        }
        Collections.shuffle(posiciones);
        int cantidad = AZAR.nextInt(FILAS * COLUMNAS + 1);
        int cantidadUno = AZAR.nextInt(cantidad + 1);
        int cantidadDos = cantidad - cantidadUno;
        int i = 0;
        while (i < cantidadUno) {
            ponerFicha(posiciones.get(i), fichaUno);
            i++;
        }
        while (i < cantidadUno + cantidadDos) {
            ponerFicha(posiciones.get(i), fichaDos);
            i++;
        }
    }

    /** Pone una ficha en la posicion recibida, contando las celdas por fila. */
    private void ponerFicha(int posicion, Ficha ficha) {
        int fila = posicion / COLUMNAS;
        int columna = posicion % COLUMNAS;
        this.matriz[fila][columna] = ficha;
    }

    private void armarAlternado(Ficha fichaUno, Ficha fichaDos) {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (fila % 2 == 0) {
                    this.matriz[fila][columna] = fichaUno;
                } else {
                    this.matriz[fila][columna] = fichaDos;
                }
            }
        }
    }

    private void armarBorde(Ficha fichaUno, Ficha fichaDos) {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (esBorde(fila, columna)) {
                    this.matriz[fila][columna] = fichaUno;
                } else {
                    this.matriz[fila][columna] = fichaDos;
                }
            }
        }
    }

    private boolean esBorde(int fila, int columna) {
        return fila == 0 || fila == FILAS - 1 || columna == 0 || columna == COLUMNAS - 1;
    }

    /**
     * Copia el estado actual del mural como estado original.
     * Se copia celda por celda: asignar la matriz entera dejaria las dos variables
     * apuntando a la misma matriz y el original se perderia con cada cambio.
     */
    public void guardarOriginal() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                this.original[fila][columna] = this.matriz[fila][columna];
            }
        }
    }

    /** Vuelve el mural al estado que tenia cuando se creo. */
    public void restaurar() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                this.matriz[fila][columna] = this.original[fila][columna];
            }
        }
    }

    /** Sustituye todas las apariciones de una ficha del mural por otra ficha. */
    public void reemplazarFicha(Ficha vieja, Ficha nueva) {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (this.matriz[fila][columna].equals(vieja)) {
                    this.matriz[fila][columna] = nueva;
                }
            }
        }
    }

    /** Fichas diferentes que aparecen en el mural. */
    public ArrayList<Ficha> getFichasDiferentes() {
        ArrayList<Ficha> fichas = new ArrayList<Ficha>();
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (!fichas.contains(this.matriz[fila][columna])) {
                    fichas.add(this.matriz[fila][columna]);
                }
            }
        }
        return fichas;
    }

    /** Colores diferentes que aparecen en el mural. */
    public ArrayList<Color> getColoresDiferentes() {
        ArrayList<Color> colores = new ArrayList<Color>();
        ArrayList<Ficha> fichas = getFichasDiferentes();
        int i = 0;
        while (i < fichas.size()) {
            if (!colores.contains(fichas.get(i).getColor())) {
                colores.add(fichas.get(i).getColor());
            }
            i++;
        }
        return colores;
    }

    /**
     * Devuelve el area rectangular que empieza en la fila y columna recibidas.
     * Las filas y columnas se cuentan desde cero.
     */
    public Ficha[][] getArea(int fila, int columna, int cantidadFilas, int cantidadColumnas) {
        Ficha[][] area = new Ficha[cantidadFilas][cantidadColumnas];
        for (int f = 0; f < cantidadFilas; f++) {
            for (int c = 0; c < cantidadColumnas; c++) {
                area[f][c] = this.matriz[fila + f][columna + c];
            }
        }
        return area;
    }

    /**
     * Busca un area dentro del mural.
     * Devuelve la fila y la columna de la primera aparicion, o null si no aparece.
     */
    public int[] buscarArea(Ficha[][] area) {
        int cantidadFilas = area.length;
        int cantidadColumnas = area[0].length;
        int[] encontrada = null;
        int fila = 0;
        while (fila + cantidadFilas <= FILAS && encontrada == null) {
            int columna = 0;
            while (columna + cantidadColumnas <= COLUMNAS && encontrada == null) {
                if (coincideArea(area, fila, columna)) {
                    encontrada = new int[]{fila, columna};
                }
                columna++;
            }
            fila++;
        }
        return encontrada;
    }

    /** Devuelve true si el area coincide con el mural en la fila y columna recibidas. */
    private boolean coincideArea(Ficha[][] area, int fila, int columna) {
        boolean coincide = true;
        for (int f = 0; f < area.length && coincide; f++) {
            for (int c = 0; c < area[0].length && coincide; c++) {
                if (!this.matriz[fila + f][columna + c].equals(area[f][c])) {
                    coincide = false;
                }
            }
        }
        return coincide;
    }

    public String toString() {
        return this.nombre;
    }
}
