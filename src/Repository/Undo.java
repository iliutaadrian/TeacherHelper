package Repository;

public class Undo<E> {
    E obj;
    Comand comand;

    public Undo(E obj, Comand comand) {
        this.obj = obj;
        this.comand = comand;
    }

    public E getObj() {
        return obj;
    }

    public Comand getComand() {
        return comand;
    }
}
