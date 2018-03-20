package validator;

public interface Ivalidator<E> {
    void validate(E element) throws ValidatorException;

}
