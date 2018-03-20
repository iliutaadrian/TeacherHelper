package validator;

import domain.Laboratoare;

import java.util.Objects;

public class LaboratorValidator implements Ivalidator<Laboratoare> {
    @Override
    public void validate(Laboratoare element) throws ValidatorException {
        String exceptie="";

        if(element.getId()<0)
            exceptie+="Numarul laboratorului nu poate fi negativ, ";

        if(element.getDeadline() < 0)
            exceptie+="Deadline-ul nu poate fi negativ, ";

        if(!exceptie.isEmpty())
            throw new ValidatorException(exceptie);
    }
}
