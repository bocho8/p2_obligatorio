/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

import java.util.ArrayList;

/**
 * Disenador de murales. Tiene un nombre unico, direccion y mail, y la lista de los
 * murales que creo. Se ordena alfabeticamente por nombre.
 */
public class Disenador implements Comparable<Disenador> {

    private String nombre;
    private String direccion;
    private String mail;
    private ArrayList<Mural> murales;

    public Disenador(String nombre, String direccion, String mail) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.mail = mail;
        this.murales = new ArrayList<Mural>();
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public String getMail() {
        return this.mail;
    }

    public ArrayList<Mural> getMurales() {
        return this.murales;
    }

    public void agregarMural(Mural mural) {
        this.murales.add(mural);
    }

    public int cantidadMurales() {
        return this.murales.size();
    }

    /**
     * Fichas diferentes que usa entre todos sus murales.
     * Cuenta todas las fichas que aparecen en sus murales, incluida la ficha vacia.
     */
    public ArrayList<Ficha> getFichasDiferentes() {
        ArrayList<Ficha> fichas = new ArrayList<Ficha>();
        int i = 0;
        while (i < this.murales.size()) {
            agregarSinRepetir(fichas, this.murales.get(i).getFichasDiferentes());
            i++;
        }
        return fichas;
    }

    /** Colores diferentes que usa entre todos sus murales. */
    public ArrayList<Color> getColoresDiferentes() {
        ArrayList<Color> colores = new ArrayList<Color>();
        int i = 0;
        while (i < this.murales.size()) {
            agregarSinRepetir(colores, this.murales.get(i).getColoresDiferentes());
            i++;
        }
        return colores;
    }

    /**
     * Agrega a la lista destino los elementos de la lista nuevos que todavia no estan.
     * Sirve para las fichas y para los colores, comparando con equals.
     */
    private <T> void agregarSinRepetir(ArrayList<T> destino, ArrayList<T> nuevos) {
        int i = 0;
        while (i < nuevos.size()) {
            if (!destino.contains(nuevos.get(i))) {
                destino.add(nuevos.get(i));
            }
            i++;
        }
    }

    /** Orden alfabetico por nombre, sin distinguir mayusculas de minusculas. */
    public int compareTo(Disenador otro) {
        return this.nombre.compareToIgnoreCase(otro.getNombre());
    }

    /** Dos disenadores son iguales cuando tienen el mismo nombre. */
    public boolean equals(Object objeto) {
        boolean iguales = false;
        if (objeto instanceof Disenador) {
            Disenador otro = (Disenador) objeto;
            iguales = this.nombre.equals(otro.getNombre());
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
