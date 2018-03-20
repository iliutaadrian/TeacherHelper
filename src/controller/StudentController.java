package controller;

import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.StudentService;
import validator.StudentValidator;
import validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StudentController{
    StudentService ctrlS ;

    public StudentController(StudentService ctrlS) {
        this.ctrlS = ctrlS;
    }

    public void adauga(int id, String nume, int grupa, String email, String profesor) throws ValidatorException {
        ctrlS.adauga(id, nume, grupa, email, profesor);
    }


    public void remove(int id) throws ValidatorException {
        ctrlS.remove(id);
    }

    public List<Student> getAll(){
        List<Student> l = new ArrayList<>();
        ctrlS.find_all().forEach(l::add);

        return l;
    }

    public void modifica(int id, Student obj) throws ValidatorException {
        ctrlS.modifica(id, obj);
    }

    public Student findOne(int id){
        //return ctrlS.findOne(id);
        for (Student x : getAll()) {
            if (x.getId() == id) {
                return x;
            }
        }
        return null;
    }

    public int numarStudenti(){
        return ctrlS.numarStudenti();
    }

    public List filterNume(String Nume){
        return ctrlS.filterNume(Nume);
    }

    public List filterID(int id){
        return ctrlS.filterID(id);
    }

    public List filterGrupa(int grupa){
        return ctrlS.filterGrupa(grupa);
    }

    public List filterProfesor(String Profesor){
        return ctrlS.filterProfesor(Profesor);
    }

    public List filterEmail(String Email){
        return ctrlS.filterEmail(Email);
    }

    public  List<Integer> getGrupa(){
        List<Integer> list = new ArrayList<>();

        getAll().forEach(obj->{
            if(!list.contains(obj.getGrupa())){
                list.add(obj.getGrupa());
            }
        });

        return list;
    }

    public void undo() {
        ctrlS.undo();
    }
}
