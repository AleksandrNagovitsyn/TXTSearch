package ru.itpark.repository;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface Repository <E> {
    void init() throws NamingException, SQLException;
//    TODO: убрать потом ексепшн
    List <E> getAll();
    E save (E e);
}
