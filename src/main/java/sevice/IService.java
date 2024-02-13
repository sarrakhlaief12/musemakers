package sevice;

import java.util.Set;

public interface IService <T> {
    public void ajouter(T r);
    public void modifier(T r);
     public void supprimer(int id);
   public T getOneById(int id);
   public Set<T> getAll();

}
