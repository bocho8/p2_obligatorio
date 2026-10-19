/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

import java.util.ArrayList;

public class Mural {

    public static final int FILAS = 10;
    public static final int COLUMNAS = 12;

    public static final char DAMERO = 'D';
    public static final char RANDOM = 'R';
    public static final char ALTERNADO = 'F';
    public static final char BORDE = 'B';
    public static final char MANUAL = 'M';

    private String nombre;
    private char formato;
    private Ficha[][] matriz;
    private Ficha[][] original;

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

    public static String formatosValidos() {
        return "" + DAMERO + RANDOM + ALTERNADO + BORDE + MANUAL;
    }

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

    // Se sortean la cantidad total, la cantidad de la primera ficha y las posiciones.
    private void armarRandom(Ficha fichaUno, Ficha fichaDos) {
        int cantidad = (int) (Math.random() * (FILAS * COLUMNAS + 1));
        int cantidadUno = (int) (Math.random() * (cantidad + 1));
        boolean[] usadas = new boolean[FILAS * COLUMNAS];
        int puestas = 0;
        while (puestas < cantidad) {
            int posicion = (int) (Math.random() * (FILAS * COLUMNAS));
            if (!usadas[posicion]) {
                usadas[posicion] = true;
                if (puestas < cantidadUno) {
                    ponerFicha(posicion, fichaUno);
                } else {
                    ponerFicha(posicion, fichaDos);
                }
                puestas++;
            }
        }
    }

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

    // Se copia celda por celda: asignar la matriz dejaria las dos variables apuntando al mismo arreglo.
    public void guardarOriginal() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                this.original[fila][columna] = this.matriz[fila][columna];
            }
        }
    }

    public void restaurar() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                this.matriz[fila][columna] = this.original[fila][columna];
            }
        }
    }

    public void reemplazarFicha(Ficha vieja, Ficha nueva) {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (this.matriz[fila][columna].equals(vieja)) {
                    this.matriz[fila][columna] = nueva;
                }
            }
        }
    }

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

    // Fila y columna se cuentan desde cero.
    public Ficha[][] getArea(int fila, int columna, int cantidadFilas, int cantidadColumnas) {
        Ficha[][] area = new Ficha[cantidadFilas][cantidadColumnas];
        for (int f = 0; f < cantidadFilas; f++) {
            for (int c = 0; c < cantidadColumnas; c++) {
                area[f][c] = this.matriz[fila + f][columna + c];
            }
        }
        return area;
    }

    // Devuelve la primera aparicion, o null si no aparece.
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

    @Override
    public String toString() {
        return this.nombre;
    }
}
