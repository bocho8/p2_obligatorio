/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

import java.util.ArrayList;

public class Sistema {

    private ArrayList<Ficha> fichas;
    private ArrayList<Disenador> disenadores;
    private Ficha fichaVacia;

    public Sistema() {
        this.fichas = new ArrayList<Ficha>();
        this.disenadores = new ArrayList<Disenador>();
        precargarFichas();
    }

    // Las 8 fichas del enunciado. Sus nombres no se pueden repetir.
    private void precargarFichas() {
        this.fichaVacia = new Ficha("VACIA", Color.porCodigo('B'),
                ".........",
                ".........................");
        agregarFicha(this.fichaVacia);
        agregarFicha(new Ficha("SOL", Color.porCodigo('M'),
                "\\|/-*-/|\\",
                "..|...\\|/.--*--./|\\...|.."));
        agregarFicha(new Ficha("CUADRADO", Color.porCodigo('N'),
                "+-+|.|+-+",
                "+---+|...||...||...|+---+"));
        agregarFicha(new Ficha("CORAZON", Color.porCodigo('R'),
                "*.****.*.",
                "**.************.***...*.."));
        agregarFicha(new Ficha("COPA", Color.porCodigo('C'),
                "\\./.|./_\\",
                "\\.../.\\./...|....|.../_\\."));
        agregarFicha(new Ficha("ARBOL", Color.porCodigo('V'),
                ".^.^^^.|.",
                "..^...^^^.^^^^^..|.../.\\."));
        agregarFicha(new Ficha("GATO", Color.porCodigo('A'),
                "^.^o.o=^=",
                "/\\_/\\(o.o).>^<../|\\../.\\."));
        agregarFicha(new Ficha("DIAMANTE", Color.porCodigo('G'),
                ".*.*.*.*.",
                "..*...*.*.*...*.*.*...*.."));
    }

    private void agregarFicha(Ficha ficha) {
        this.fichas.add(ficha);
    }

    public Ficha getFichaVacia() {
        return this.fichaVacia;
    }

    public ArrayList<Ficha> getFichas() {
        return this.fichas;
    }

    public ArrayList<Disenador> getDisenadores() {
        return this.disenadores;
    }

    public Ficha buscarFicha(String nombre) {
        Ficha encontrada = null;
        int i = 0;
        while (i < this.fichas.size() && encontrada == null) {
            if (this.fichas.get(i).getNombre().equals(nombre)) {
                encontrada = this.fichas.get(i);
            }
            i++;
        }
        return encontrada;
    }

    public boolean existeFicha(String nombre) {
        return buscarFicha(nombre) != null;
    }

    public void registrarFicha(String nombre, Color color, String disenoChico, String disenoGrande) {
        agregarFicha(new Ficha(nombre, color, disenoChico, disenoGrande));
    }

    public Disenador buscarDisenador(String nombre) {
        Disenador encontrado = null;
        int i = 0;
        while (i < this.disenadores.size() && encontrado == null) {
            if (this.disenadores.get(i).getNombre().equals(nombre)) {
                encontrado = this.disenadores.get(i);
            }
            i++;
        }
        return encontrado;
    }

    public boolean existeDisenador(String nombre) {
        return buscarDisenador(nombre) != null;
    }

    public void registrarDisenador(String nombre, String direccion, String mail) {
        this.disenadores.add(new Disenador(nombre, direccion, mail));
    }

    public ArrayList<Mural> getMurales() {
        ArrayList<Mural> murales = new ArrayList<Mural>();
        int i = 0;
        while (i < this.disenadores.size()) {
            murales.addAll(this.disenadores.get(i).getMurales());
            i++;
        }
        return murales;
    }

    public Mural buscarMural(String nombre) {
        Mural encontrado = null;
        ArrayList<Mural> murales = getMurales();
        int i = 0;
        while (i < murales.size() && encontrado == null) {
            if (murales.get(i).getNombre().equals(nombre)) {
                encontrado = murales.get(i);
            }
            i++;
        }
        return encontrado;
    }

    public boolean existeMural(String nombre) {
        return buscarMural(nombre) != null;
    }

    public Mural crearMural(Disenador disenador, String nombre, char formato, Ficha fichaUno, Ficha fichaDos) {
        Mural mural = new Mural(nombre, formato, fichaUno, fichaDos, this.fichaVacia);
        disenador.agregarMural(mural);
        return mural;
    }
}
