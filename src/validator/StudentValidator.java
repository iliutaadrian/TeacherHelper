package validator;

import domain.Student;

import java.util.Objects;

public class StudentValidator implements Ivalidator<Student> {
    @Override
    public void validate(Student element) throws ValidatorException {
        String exceptie="";

        if(element.getId()<0)
            exceptie+="Id-ul nu poate fi negativ, ";

        if(Objects.equals(element.getNume(), ""))
            exceptie+="Numele trebuie sa aiba o valoare, ";

        if(Objects.equals(element.getProfesor(), ""))
            exceptie+="Numele profesorului trebuie sa aiba o valoare.";

        if(!exceptie.isEmpty())
            throw new ValidatorException(exceptie);
    }
}
