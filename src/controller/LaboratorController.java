package controller;

import domain.Laboratoare;
import service.LaboratorService;
import validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;

public class LaboratorController {
    LaboratorService ctrlL;

    public LaboratorController(LaboratorService ctrlL) {
        this.ctrlL = ctrlL;
    }

    public int numarLaboratoare(){
        return ctrlL.numarLaboratoare();
    }

    public List<Laboratoare> getAll(){
        List<Laboratoare> l = new ArrayList<>();
        ctrlL.find_all().forEach(l::add);

        return l;
    }

    public void adauga(int id, String descriere, int deadline) throws ValidatorException {
        ctrlL.adauga(id, descriere, deadline);
    }


    public void remove(int id) throws ValidatorException {
        ctrlL.remove(id);
    }

    public void modifica(int id, int data) throws ValidatorException {
        ctrlL.modifica(id, data);
    }

    public List filterID(int id){
        return ctrlL.filterID(id);
    }

    public List filterDescriere(String descriere){
        return ctrlL.filterDescriere(descriere);
    }

    public List filterDeadline(int dealine){
        return ctrlL.filterDeadline(dealine);
    }

    public void undo() {
        ctrlL.undo();
    }
}
