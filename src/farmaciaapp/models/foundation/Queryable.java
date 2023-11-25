/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package farmaciaapp.models.foundation;

import java.util.ArrayList;

/**
 *
 * @author Lenovo ideapad 330S
 * @param <T>
 */
public interface Queryable<T extends DataObject>{
    boolean insert(T dto);
    boolean update(int dtoId, T dto);
    boolean delete(T dto);
    ArrayList<T> selectAll();
    int getLastInsertedId();
    T selectThis(T dto);
    ArrayList<T> selectWhere(String field, String param);
}
