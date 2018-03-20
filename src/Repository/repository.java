package Repository;

import validator.ValidatorException;

import java.util.Optional;

public interface repository<E, ID> {
    Optional<E> add(E obiect) throws ValidatorException;

    Optional<E> delete(ID id) throws ValidatorException;

    E findOne(ID id);

    Iterable<E> findAll();

    long size();
}
