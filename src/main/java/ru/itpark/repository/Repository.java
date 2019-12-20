package ru.itpark.repository;

import java.util.Deque;

public interface Repository <E> {
    void init() ;
    Deque<E> getAll();
    E save (E e);
}
