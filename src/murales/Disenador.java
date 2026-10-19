/*
 * Obligatorio 1 - Programacion 2
 * Autores: Agustín Roizen (350021)
 *          Juan Francisco Tarigo (391925)
 */
package murales;

import java.util.ArrayList;

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

    public ArrayList<Ficha> getFichasDiferentes() {
        ArrayList<Ficha> fichas = new ArrayList<Ficha>();
        int i = 0;
        while (i < this.murales.size()) {
            agregarFichasSinRepetir(fichas, this.murales.get(i).getFichasDiferentes());
            i++;
        }
        return fichas;
    }

    public ArrayList<Color> getColoresDiferentes() {
        ArrayList<Color> colores = new ArrayList<Color>();
        int i = 0;
        while (i < this.murales.size()) {
            agregarColoresSinRepetir(colores, this.murales.get(i).getColoresDiferentes());
            i++;
        }
        return colores;
    }

    private void agregarFichasSinRepetir(ArrayList<Ficha> destino, ArrayList<Ficha> nuevas) {
        int i = 0;
        while (i < nuevas.size()) {
            if (!destino.contains(nuevas.get(i))) {
                destino.add(nuevas.get(i));
            }
            i++;
        }
    }

    private void agregarColoresSinRepetir(ArrayList<Color> destino, ArrayList<Color> nuevos) {
        int i = 0;
        while (i < nuevos.size()) {
            if (!destino.contains(nuevos.get(i))) {
                destino.add(nuevos.get(i));
            }
            i++;
        }
    }

    @Override
    public int compareTo(Disenador otro) {
        return this.nombre.compareToIgnoreCase(otro.getNombre());
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
