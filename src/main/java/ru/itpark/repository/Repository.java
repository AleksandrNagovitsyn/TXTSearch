package ru.itpark.repository;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Deque;
import java.util.List;

public interface Repository <E> {
    void init();
    Deque<E> getAll();
    E save (E e);
}
