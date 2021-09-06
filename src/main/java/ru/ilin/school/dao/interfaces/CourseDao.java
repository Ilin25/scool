package ru.ilin.school.dao.interfaces;

import ru.ilin.school.domain.Course;
import ru.ilin.school.domain.Student;
import java.util.List;

public interface CourseDao extends CrudDao<Course,Long> {

    void removeStudent(int id);
    void createStudent(Student student);
    List<Course> findAllCourseByStudentId(int id);
}
