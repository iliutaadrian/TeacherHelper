package service;

import domain.Nota;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Filter <E>{
    public <E> List<E>  filterAndSort(List<E> l, Predicate<E> p, Comparator<E> cmp){
        return l.stream().
                filter(p).
                sorted(cmp).
                collect(Collectors.toList());
    }

}
