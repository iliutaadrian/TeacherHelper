package Repository;

import domain.Laboratoare;
import domain.Student;
import Repository.AbstractRepository;
import validator.Ivalidator;
import validator.ValidatorException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class StudentRepository extends AbstractRepository<Student, Integer> {
    String path;

    public StudentRepository(String path, Ivalidator<Student> iValidator) {
        super(iValidator);
        this.path = path;
        loadData();
    }

    public void adauga(Student s) throws ValidatorException {
        add(s);
        saveData();
    }

    public void sterge(int id) throws ValidatorException {
        delete(id);
        saveData();
    }

    public void modify(int id, Student obj) throws ValidatorException {
        Student obiect= findOne(id) ;
        if(obiect==null)
            throw new ValidatorException("Id-ul nu este existent");
        if(obiect.getId()!=null){
            delete(obiect.getId());
            add(obj);
        }

    }

    void loadData(){
        try{
            Path path1 = Paths.get(path);
            Stream<String> lines;
            lines = Files.lines(path1);
            lines.forEach((s) ->{
                String fields[];
                fields=s.split("#");
                try {
                    add(new Student(Integer.parseInt(fields[0]),fields[1],
                            Integer.parseInt(fields[2]), fields[3],fields[4]));
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void saveData() {
        try{
            BufferedWriter br = new BufferedWriter(new FileWriter(path));
            for (Student t: findAll())
            {

                String information = t.getId()+ "#"+
                        t.getNume() + "#" +
                        t.getGrupa() + "#" +
                        t.getEmail() + "#"+
                        t.getProfesor() +  "\n";
                br.write(information);
            }
            br.close();
        }
        catch(IOException e){
            e.getStackTrace();
        }

    }
}