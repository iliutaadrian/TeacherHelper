package Repository;

import domain.Laboratoare;
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

public class LaboratoareRepository extends AbstractRepository<Laboratoare, Integer> {
    String path;

    public LaboratoareRepository(String path, Ivalidator<Laboratoare> iValidator) {
        super(iValidator);
        this.path = path;
        loadData();
    }

    public void modify(int id, int data) throws ValidatorException {
        Laboratoare obiect= findOne(id) ;
        if(obiect.getId()!=0){
            if(obiect.getDeadline()>=data)
                throw new ValidatorException("Nu avem ce modifica");
            else{
                obiect.setDeadline(data);
            }
        }
        else
            throw new ValidatorException("Nu am gasit obiectul");
    }

    public void adauga(Laboratoare s) throws ValidatorException {
        add(s);
        saveData();
    }

    public void sterge(int id) throws ValidatorException {
        delete(id);
        saveData();
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
                    add(new Laboratoare(Integer.parseInt(fields[0]),fields[1],
                            Integer.parseInt(fields[2])));
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
            for (Laboratoare t: findAll())
            {

                String information = t.getId()+ "#"+
                        t.getDescriere() + "#" +
                        t.getDeadline() + "#" +
                         "\n";
                br.write(information);
            }
            br.close();
        }
        catch(IOException e){
            e.getStackTrace();
        }

    }
}
