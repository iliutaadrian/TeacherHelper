package Repository;

import domain.HasId;
import validator.Ivalidator;
import validator.ValidatorException;

import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;

public abstract class AbstractRepository<E extends HasId<ID>, ID> implements repository<E, ID>
{
    private HashMap<ID, E> map;

    private Ivalidator<E> validate;

    private Stack<Undo<E>> undos = new Stack<>();

    public AbstractRepository(Ivalidator<E> iValidator){

        map = new HashMap<>();
        this.validate = iValidator;
    }


    @Override
    public long size(){
        return map.size();
    }

    @Override
    public Optional<E> add(E entity) throws ValidatorException{
        validate.validate(entity);
        if(map.containsKey(entity.getId()))
            throw new ValidatorException("Id-ul este deja existent");
        map.put(entity.getId(), entity);
        undos.push(new Undo<E>(entity,Comand.adauga));

        return Optional.of(entity);
    }

    @Override
    public Optional<E> delete(ID id) throws ValidatorException{
        if(!map.containsKey(id))
            throw new ValidatorException("Id-ul nu este existent");
        undos.push(new Undo<E>(findOne(id),Comand.stergere));
        return Optional.ofNullable(map.remove(id));
    }

    @Override
    public E findOne(ID id){
        return map.get(id);
    }

    @Override
    public Iterable<E> findAll(){
        return map.values();
    }

    public int getSize(){
        return map.size();
    }

    public void undo() throws ValidatorException {
        if(undos.empty())
            throw new ValidatorException("Nu se mai poate face undo!");
        Undo obj = undos.peek();
        undos.pop();
        if(obj.getComand()==Comand.adauga){
            E obj1 = (E) obj.getObj();
            try {
                delete(obj1.getId());
                undos.pop();
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
        if(obj.getComand()==Comand.stergere){
            E obj1 = (E) obj.getObj();
            try {
                add(obj1);
                undos.pop();
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }
}