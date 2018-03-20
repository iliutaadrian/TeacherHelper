package service;

import Repository.StudentRepository;
import domain.Student;
import validator.Ivalidator;
import validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StudentService {
    /**
     * variabila de tip Repository.StudentRepository
     */
    StudentRepository repoS;
    Filter<Student> filter;

    /**
     *
     * @param iValidator validatorul Studentului ce verifica corectitudinea acestuia
     * Constructorul primeste ca parametru validatorul ce il paseaza mai departe StudentReposiory,
     * pentru a putea accesa functiile din aceasta
     */
    public StudentService(String path, Ivalidator<Student> iValidator) {
        repoS = new StudentRepository(path,iValidator);
        filter = new Filter<>();
    }


    /**
     *
     * @param id int
     * @param nume String
     * @param grupa int
     * @param email String
     * @param profesor String
     * @throws ValidatorException daca id-ul deja este existent
     * Functie ce adauga obiectul de tip Student
     */
    public void adauga(int id, String nume, int grupa, String email, String profesor) throws ValidatorException {
        repoS.adauga(new Student(id, nume, grupa, email, profesor));
    }

    /**
     *
     * @param id int
     * @throws ValidatorException daca id-ul nu exista
     * Functie ce primeste ca parametru id-ul si sterge obiectul corespunzator
     *
     */
    public void remove(int id) throws ValidatorException {
        repoS.sterge(id);
    }


    public void modifica(int id, Student obj) throws ValidatorException {
        repoS.modify(id, obj);
    }

    /**
     *
     * @return dimensiune listei de studenti
     *
     */
    public long size(){
        return repoS.size() ;
    }

    /**
     * afiseaza pe ecran toata lista de studenti
     *
     */
    public Iterable<Student> find_all(){
        return repoS.findAll();
    }

    /**
     *
     * @param id int
     * @return obiectul cu id-ul dat ca parametru
     *
     */
    public Student findOne(int id){
        return repoS.findOne(id);
    }

    public List filterNume(String Nume){
        Predicate<Student> f = (x)-> x.getNume().toLowerCase().contains(Nume.toLowerCase());

        List<Student> l = new ArrayList<>();
        find_all().forEach(l::add);


        return filter.filterAndSort(l, f, Student.cmpStudent);
    }

    public List filterID(int id){
        Predicate<Student> f = (x)-> x.getId()==id;

        List<Student> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, f, Student.cmpStudent);
    }

    public List filterGrupa(int grupa){
        Predicate<Student> f = (x)-> x.getGrupa()==grupa;

        List<Student> l = new ArrayList<>();
        find_all().forEach(l::add);

        return filter.filterAndSort(l, f, Student.cmpStudent);
    }

    public List filterProfesor(String Profesor){
        Predicate<Student> f = (x)-> x.getProfesor().toLowerCase().contains(Profesor.toLowerCase());

        List<Student> l = new ArrayList<>();
        find_all().forEach(l::add);


        return filter.filterAndSort(l, f, Student.cmpStudent);
    }

    public List filterEmail(String Email){
        Predicate<Student> f = (x)-> x.getEmail().toLowerCase().contains(Email.toLowerCase());

        List<Student> l = new ArrayList<>();
        find_all().forEach(l::add);


        return filter.filterAndSort(l, f, Student.cmpStudent);
    }

    public int numarStudenti(){
        return repoS.getSize();
    }

    public void undo(){
        try {
            repoS.undo();
        } catch (ValidatorException e) {
            e.printStackTrace();
        }
    }
}
