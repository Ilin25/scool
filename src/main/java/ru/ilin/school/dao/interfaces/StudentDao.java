package ru.ilin.school.dao.interfaces;

import ru.ilin.school.domain.Student;

import java.util.List;

public interface StudentDao extends CrudDao<Student,Long> {
    List<Student> findAllStudentByCourseName(String name);
}
