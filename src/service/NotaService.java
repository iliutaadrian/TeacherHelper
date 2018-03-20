package service;

import Repository.NotaFileRepository;
import domain.Nota;
import validator.Ivalidator;
import validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class NotaService {
    NotaFileRepository repoN;
    Filter<Nota> filter;

    public NotaService(String path, StudentService ctrlS, LaboratorService ctrlL, Ivalidator<Nota> valN) {
        repoN = new NotaFileRepository(path, valN, ctrlL, ctrlS);
        filter = new Filter<>();
    }

    public void adauga(int idNota,int idStudent, int idLaborator, int predare, int valoare) throws ValidatorException {
        repoN.adauga(idNota,idStudent,idLaborator,predare,valoare);
    }

    public void modifica(int idNota, int nota, int predare) throws ValidatorException {
        repoN.modify(idNota, nota, predare);
    }

    public Iterable<Nota> find_all(){
        return repoN.findAll();
    }

    public void sterge(int id) throws ValidatorException {
        repoN.sterge(id);
    }

    public void sterge_student(int idStudent){
        repoN.sterge_student(idStudent);
    }

    public void sterge_laborator(int idLaborator){
        repoN.sterge_laborator(idLaborator);
    }

    public List filter(int min, int max){
        Predicate<Nota>f = (x)-> x.getValoare() >= min && x.getValoare() < max;

        List<Nota> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, f, Nota.cmpValoare);
    }

    public List filter_desc(int min, int max){
        Predicate<Nota>f = (x)-> x.getValoare() >= min && x.getValoare() <= max;

        List<Nota> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, f, Nota.cmpValoareDesc);
    }

    public List filterIdNota(int id){
        Predicate<Nota>f = (x)-> x.getIdNota()==id;

        List<Nota> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, f, Nota.cmpNota);
    }

    public List filterIdStudent(int id){
        Predicate<Nota>f = (x)-> x.getIdStudent()==id;

        List<Nota> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, f, Nota.cmpNota);
    }

    public List filterIdLaborator(int id){
        Predicate<Nota>f = (x)-> x.getIdLaborator()==id;

        List<Nota> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, f, Nota.cmpNota);
    }

    public List filterPredare(int predare){
        Predicate<Nota>f = (x)-> x.getPredare()==predare;

        List<Nota> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, f, Nota.cmpNota);
    }

    public List filterValoare(int valoare){
        Predicate<Nota>f = (x)-> x.getValoare()==valoare;

        List<Nota> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, f, Nota.cmpNota);
    }

    public void undo(){
        try {
            repoN.undo();
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    public int numarNote() {
        return repoN.getSize();
    }
}
