package domain;

import controller.StudentController;
import service.StudentService;
import validator.StudentValidator;

import java.util.Comparator;

public class Nota implements HasId<Integer>{

    int idNota;
    int idStudent;
    int idLaborator;
    int predare;
    int valoare;

    public Nota(int idNota, int idStudent, int idLaborator, int predare, int valoare) {
        this.idNota = idNota;
        this.idStudent = idStudent;
        this.idLaborator = idLaborator;
        this.predare = predare;
        this.valoare = valoare;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdLaborator() {
        return idLaborator;
    }

    public void setIdLaborator(int idLaborator) {
        this.idLaborator = idLaborator;
    }

    public int getPredare() {
        return predare;
    }

    public void setPredare(int predare) {
        this.predare = predare;
    }

    public int getValoare() {
        return valoare;
    }

    public void setValoare(int valoare) {
        this.valoare = valoare;
    }

    public static Comparator<Nota>  cmpValoare = (Nota n1, Nota n2)->
            (int)(n1.getValoare()-n2.getValoare());

    public static Comparator<Nota> cmpValoareDesc = cmpValoare.reversed();

    public static Comparator<Nota>  cmpStudent = (Nota n1, Nota n2)->
            (int)(n1.getIdStudent()-n2.getIdStudent());

    public static Comparator<Nota>  cmpNota = (Nota n1, Nota n2)->
            (int)(n1.getIdNota()-n2.getIdNota());


    @Override
    public Integer getId() {
        return idNota;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "idNota= "+idNota+
                ", idStudent=" + idStudent +
                ", idLaborator=" + idLaborator +
                ", predare=" + predare +
                ", valoare=" + valoare +
                '}';
    }
}
