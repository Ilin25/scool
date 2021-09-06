package ru.ilin.school.dao.interfaces;

public interface CrudDao<E,ID> {

    void save(E entity);
    void update(E entity);
    void deleteById(ID id);
}
