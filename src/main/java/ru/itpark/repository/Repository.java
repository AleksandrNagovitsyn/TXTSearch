package ru.itpark.repository;

import java.util.List;

public interface Repository <E> {
    void init();
    List <E> gerAll();
}
