package controller;

import domain.Nota;
import service.NotaService;
import validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;

public class NoteController {
    NotaService ctrlN;

    public NoteController(NotaService ctrlN) {
        this.ctrlN = ctrlN;
    }


    public void adauga(int idNota,int idStudent, int idLaborator, int predare, int valoare) throws ValidatorException {
        ctrlN.adauga(idNota, idStudent, idLaborator, predare, valoare);
    }


    public void remove(int id) throws ValidatorException {
        ctrlN.sterge(id);
    }

    public void sterge_student(int idStudent){
        ctrlN.sterge_student(idStudent);
    }

    public void sterge_laborator(int idLaborator){
        ctrlN.sterge_laborator(idLaborator);
    }

    public List<Nota> getAll(){
        List<Nota> l = new ArrayList<>();
        ctrlN.find_all().forEach(l::add);

        return l;
    }

    public void modifica(int idNota, int nota, int predare) throws ValidatorException {
        ctrlN.modifica(idNota,nota,predare);
    }

    public int numarNote(){
        return ctrlN.numarNote();
    }

    public List filterIdNota(int id){
        return ctrlN.filterIdNota(id);
    }

    public List filterIdStudent(int id){
        return ctrlN.filterIdStudent(id);
    }

    public List filterIdLaborator(int id){
        return ctrlN.filterIdLaborator(id);
    }

    public List filterPredare(int predare){
        return ctrlN.filterPredare(predare);
    }

    public List filterValoare(int valoare){
        return ctrlN.filterValoare(valoare);
    }

    public void undo(){
        ctrlN.undo();
    }
}

