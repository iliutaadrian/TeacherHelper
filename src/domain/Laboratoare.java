package domain;

import java.util.Comparator;

public class Laboratoare implements HasId<Integer> {
    private int id;
    private String descriere;
    private int deadline;

    public Laboratoare(int id, String descriere, int deadline) {
        this.id = id;
        this.descriere = descriere;
        this.deadline = deadline;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public static Comparator<Laboratoare> cmpLaboratoare = (Laboratoare n1, Laboratoare n2)->
            (int)(n1.getId()-n2.getId());

    @Override
    public String toString() {
        return "Laboratoare{" +
                "id=" + id +
                ", descriere='" + descriere + '\'' +
                ", deadline=" + deadline +
                '}';
    }

    @Override
    public Integer getId() {
        return id;
    }
}
