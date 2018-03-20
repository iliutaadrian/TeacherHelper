package service;

import Repository.LaboratoareRepository;
import domain.Laboratoare;
import validator.Ivalidator;
import validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LaboratorService{
    /**
     * variabila de tip Laborator repository
     */
    LaboratoareRepository repoL;
    Filter<Laboratoare> filter;

    /**
     *
     *
     * @param iValidator validatorul Laboratorului ce verifica corectitudinea acestuia
     * Constructorul primeste ca parametru validatorul ce il paseaza mai departe
     * Repository.LaboratoareRepository pentru a putea accesa functiile din aceasta
     *
     */
    public LaboratorService(String path, Ivalidator<Laboratoare> iValidator) {
        repoL = new LaboratoareRepository(path, iValidator);
        filter = new Filter<>();
    }

    /**
     *
     * @param id int
     * @param descriere String
     * @param deadline int
     * @throws ValidatorException daca id-ul este deja existent
     *Functie ce adauga obiectul de tip Laborator
     */
    public void adauga(int id, String descriere, int deadline) throws ValidatorException {
        repoL.adauga(new Laboratoare(id,descriere,deadline));
    }

    /**
     *
     * @param id int
     * @throws ValidatorException daca id-ul nu exista
     * Functie ce primeste ca parametru id-ul si sterge obiectul corespunzator
     */
    public void remove(int id) throws ValidatorException {
        repoL.sterge(id);
    }

    /**
     *
     * @return dimensiunea listei de studenti
     */
    public long size(){
        return repoL.size();
    }

    /**
     * Functie ce afiseaza pe ecran toata lista de studenti
     */
    public Iterable<Laboratoare> find_all(){
        return repoL.findAll();
    }

    /**
     *
     * @param id int
     * @return obiectul cu id-ul dat ca parametru
     *
     */
    public Laboratoare findOne(int id){
        return repoL.findOne(id);
    }

    /**
     *
     * @param id int
     * @param data int
     * @throws ValidatorException daca id-ul nu exista sau data nu este corespunzatoare
     * Modifica data de la obiectul cu id-ul dat
     */
    public void modifica(int id, int data) throws ValidatorException {
        repoL.modify(id, data);
    }

    public List filter(int min, int max){
        Predicate<Laboratoare> f = (x)-> x.getId()>=min && x.getId()<=max;

        List<Laboratoare> l = new ArrayList<>();
        find_all().forEach(l::add);

        List<Laboratoare> r = new ArrayList<>();

        r.addAll(filter.filterAndSort(l, f, Laboratoare.cmpLaboratoare));

        return r;
    }

    public List filterID(int id){
        Predicate<Laboratoare> p  = x->x.getId()==id;
        List<Laboratoare> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, p, Laboratoare.cmpLaboratoare);
    }

    public List filterDescriere(String descriere){
        Predicate<Laboratoare> p  = x->x.getDescriere().toLowerCase().contains(descriere.toLowerCase());
        List<Laboratoare> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, p, Laboratoare.cmpLaboratoare);
    }

    public List filterDeadline(int dealine){
        Predicate<Laboratoare> p  = x->x.getDeadline()==dealine;
        List<Laboratoare> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, p, Laboratoare.cmpLaboratoare);
    }

    public void undo() {
        try {
            repoL.undo();
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }

    public int numarLaboratoare() {
        return repoL.getSize();
    }
}
