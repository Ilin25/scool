package ru.ilin.school.dao.Impl;

import ru.ilin.school.dao.DBConnector;
import ru.ilin.school.dao.interfaces.StudentDao;
import ru.ilin.school.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl extends AbstractCrudDaoImpl<Student> implements StudentDao {

    private static final String SAVE_QUERY = "INSERT INTO students (first_name,last_name) values(?,?)";
    private static final String UPDATE_QUERY = "UPDATE students SET first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM students WHERE student_id = ?";
    private static final String GET_ALL_STUDENT_BY_COURSE_NAME_QUERY =
            "SELECT s.student_id, s.first_name,s.last_name FROM students s" +
            "      JOIN students_courses sc ON s.student_id = sc.student_id" +
            "      JOIN courses c ON sc.course_id = c.course_id \n" +
            "      WHERE c.course_name = ? ";

    private static CourseDaoImpl courseDao;
    private static GroupDaoImpl groupDao;

    public StudentDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, UPDATE_QUERY, DELETE_QUERY);
    }


    public List<Student> findAllStudentByCourseName(String name){
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(GET_ALL_STUDENT_BY_COURSE_NAME_QUERY)) {
            preparedStatement.setString(1, name);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Student> students = new ArrayList<>();
                while (resultSet.next()) {
                    students.add(mapResultSetToEntity(resultSet));
                }
                return students;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    protected void insertValues(PreparedStatement preparedStatement, Student student) throws SQLException {
        preparedStatement.setString(1,student.getFirstName());
        preparedStatement.setString(2,student.getLastName());
        preparedStatement.setInt(3,student.getGroup().getId());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Student student) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_QUERY)) {
            prepareStatement.setInt(1, student.getId());
            prepareStatement.setString(2, student.getFirstName());
            prepareStatement.setString(3, student.getLastName());
            prepareStatement.setInt(4, student.getGroup().getId());
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    protected Student mapResultSetToEntity(ResultSet resultSet) throws SQLException {

        int studentId = resultSet.getInt("student_id");
        return Student.builder()
                .withId(studentId)
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withGroup(groupDao.findGroupByStudentId(studentId))
                .withCourse(courseDao.findAllCourseByStudentId(studentId))
                .build();
    }
}
