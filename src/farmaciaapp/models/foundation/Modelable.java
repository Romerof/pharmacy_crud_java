package farmaciaapp.models.foundation;

import java.util.List;



/**
 *
 * @author Lenovo ideapad 330S
 * @param <T>
 */
public interface Modelable <T extends DataObject> {
    T get(int index);
    int add(T dt);
    boolean put(int index, T dt);
    T remove(int index);
    void pull();
    boolean contains(T dt);
    int indexAt(T dt);
    void filterList(String filterParam);
    List<T> all();
}
