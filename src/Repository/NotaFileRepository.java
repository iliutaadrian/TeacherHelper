package Repository;

import domain.Laboratoare;
import domain.Nota;
import service.LaboratorService;
import service.StudentService;
import validator.Ivalidator;
import validator.ValidatorException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;
import java.util.stream.Stream;

public class NotaFileRepository extends AbstractRepository<Nota, Integer> {
    private String path;
    private StudentService ctrlS;
    private LaboratorService ctrlL;

    public NotaFileRepository(String path, Ivalidator<Nota> valN, LaboratorService ctrlL, StudentService ctrlS) {
        super(valN);
        this.path = path;
        this.ctrlS = ctrlS;
        this.ctrlL = ctrlL;
        loadData();
    }

    private void loadData(){
        try{
            Path path1 = Paths.get(path);
            Stream<String> lines;
            lines = Files.lines(path1);
            lines.forEach((s) ->{
                String fields[];
                fields=s.split("#");
                try {
                    add(new Nota(Integer.parseInt(fields[0]),Integer.parseInt(fields[1]),
                            Integer.parseInt(fields[2]), Integer.parseInt(fields[3]),Integer.parseInt(fields[4])));
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
                salvare_student(Integer.parseInt(fields[1]));
            });
        }
        catch(IOException e){
            e.printStackTrace();
        }
}

    private void saveData() {
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter(path));
            for (Nota t: findAll())
            {

                String information = t.getIdNota()+ "#"+
                        t.getIdStudent() + "#" +
                        t.getIdLaborator() + "#" +
                        t.getPredare() + "#"+
                        t.getValoare() +  "\n";
                br.write(information);
            }
            br.close();
        }
        catch(IOException e){
            e.getStackTrace();
        }

    }

    public void adauga(int idNota, int idStudent, int idLaborator, int predare, int valoare) throws ValidatorException {
        if(ctrlS.findOne(idStudent)==null){
            throw new ValidatorException("Id-ul student nu exista");
        }
        if(ctrlL.findOne(idLaborator)==null){
            throw new ValidatorException("Id-ul laborator nu exista");
        }
        for(Nota obj: findAll())
            if(obj.getIdStudent()==idStudent && obj.getIdLaborator()==idLaborator)
                throw  new ValidatorException("Laboratorul studentului deja exista");

        Laboratoare obj = ctrlL.findOne(idLaborator);
        if(obj.getDeadline()<predare) {
            valoare = valoare - (predare - obj.getDeadline());
            if (valoare<=0)
                add(new Nota(idNota,idStudent, idLaborator,predare, 1));
        }
        add(new Nota(idNota,idStudent, idLaborator, predare, valoare));

        saveData();

        salvare_student(idStudent);
    }

    private void salvare_student(int id){
        String locatie = "files/" +id+".txt";
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter(locatie));
            for (Nota t: findAll())
            {
                if(t.getIdStudent()==id) {
                    String information = t.getIdNota() + "#" +
                            t.getIdStudent() + "#" +
                            t.getIdLaborator() + "#" +
                            t.getPredare() + "#" +
                            t.getValoare() + "\n";
                    br.write(information);
                }
            }
            br.close();
        }
        catch(IOException e){
            e.getStackTrace();
        }
    }

    /**
     *
     * deadline <= predare si getValoare > nota schimba nota
     * deadline > predare si getValoare < nota schimba nota
     */
    public void modify(int idNota, int nota, int predare) throws ValidatorException {
        if(ctrlS.findOne(idNota)==null){
            throw new ValidatorException("Id-ul student nu exista");
        }
        if(nota<0)
            throw new ValidatorException("Nota nu poate fi negativa");


        Nota obj = findOne(idNota);
        Laboratoare lab = ctrlL.findOne(obj.getIdLaborator());

        if(predare<=lab.getDeadline())
            obj.setValoare(nota);

        if(predare>lab.getDeadline())
            if(obj.getValoare() < nota-(predare-lab.getDeadline())) {
                obj.setValoare(nota - (predare - lab.getDeadline()));
            }

        saveData();
        salvare_student(obj.getIdStudent());
    }

    public void sterge(int id) throws ValidatorException {
        delete(id);
        saveData();
    }

    public void sterge_student(int idStudent) {
        Stack<Integer> stiva = new Stack<>();
        for(Nota n: findAll())
            if(n.getIdStudent()==idStudent)
                stiva.push(n.getId());

        sterge(stiva);
        salvare_student(idStudent);
        saveData();
    }

    public void sterge_laborator(int idLaborator) {
        Stack<Integer> stiva = new Stack<>();
        for(Nota n: findAll())
            if(n.getIdLaborator()==idLaborator)
                stiva.push(n.getId());
        sterge(stiva);
        saveData();
    }

    public void sterge(Stack<Integer> stiva){
        while(!stiva.empty()){
            try {
                delete(stiva.peek());
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
            stiva.pop();
        }
    }
}
