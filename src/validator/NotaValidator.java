package validator;

import domain.Nota;

public class NotaValidator implements Ivalidator<Nota> {
    @Override
    public void validate(Nota element) throws ValidatorException {
        String exceptie="";

        if (element.getIdStudent() < 0)
            exceptie += "Id-ul Student nu poate fi negativ, ";

        if (element.getIdLaborator() < 0)
            exceptie += "Id-ul Laborator nu poate fi negativ, ";

        if (element.getPredare() < 0)
            exceptie += "Saptamana de predare nu poate fi negativa, ";

        if (element.getValoare() < 0)
            exceptie += "Nota nu poate fi negativa. ";

        if (!exceptie.isEmpty())
            throw new ValidatorException(exceptie);
    }
}