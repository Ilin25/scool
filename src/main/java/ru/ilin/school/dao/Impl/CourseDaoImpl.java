package ru.ilin.school.dao.Impl;

import ru.ilin.school.dao.interfaces.CourseDao;
import ru.ilin.school.dao.DBConnector;
import ru.ilin.school.dao.interfaces.StudentDao;
import ru.ilin.school.domain.Course;
import ru.ilin.school.domain.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl extends AbstractCrudDaoImpl<Course> implements CourseDao {

    private static final String SAVE_QUERY = "INSERT INTO courses(course_name,course_description) VALUES (?,?)";
    private static final String UPDATE_QUERY = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM courses WHERE course_id = ?";
    private static final String FIND_ALL_COURSE_BY_STUDENT_ID_QUERY = "SELECT c.course_id, c.course_name, c.course_description" +
            "       FROM courses c " +
            "       JOIN students_courses sc ON c.course_id = sc .course_id" +
            "       JOIN students s ON sc.student_id = s.student_id" +
            "       WHERE s.student_id = ?";

    private static StudentDao studentDao;

    public CourseDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, UPDATE_QUERY, DELETE_QUERY);
    }

    @Override
    protected void insertValues(PreparedStatement preparedStatement, Course entity) throws SQLException {
        preparedStatement.setInt(1,entity.getId());
        preparedStatement.setString(2,entity.getName());
        preparedStatement.setString(2,entity.getDescription());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Course course) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_QUERY)) {
            prepareStatement.setInt(1, course.getId());
            prepareStatement.setString(2, course.getName());
            prepareStatement.setString(3, course.getDescription());
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    protected Course mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("course_name");
        return Course.builder()
                .withId(resultSet.getInt("course_id"))
                .withName(name)
                .withDescription(resultSet.getString("course_description"))
                .withStudents(studentDao.findAllStudentByCourseName(name))
                .build();
    }

    @Override
    public void removeStudent(int id) {

    }

    @Override
    public void createStudent(Student student) {

    }

    @Override
    public List<Course> findAllCourseByStudentId(int id){
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(FIND_ALL_COURSE_BY_STUDENT_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Course> courses = new ArrayList<>();
                while (resultSet.next()) {
                    courses.add(mapResultSetToEntity(resultSet));
                }
                return courses;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }
}
