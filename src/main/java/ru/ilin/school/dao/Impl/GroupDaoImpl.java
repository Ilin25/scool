package ru.ilin.school.dao.Impl;

import ru.ilin.school.dao.DBConnector;
import ru.ilin.school.dao.interfaces.GroupDao;
import ru.ilin.school.domain.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GroupDaoImpl extends  AbstractCrudDaoImpl<Group> implements GroupDao {

    private static final String SAVE_QUERY = "INSERT INTO groups (group_id,group_name) values(?,?)";
    private static final String UPDATE_QUERY = "UPDATE groups SET group_id = ?, group_name = ? WHERE group_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM groups WHERE group_id = ?";
    private static final String FIND_GROUP_BY_STUDENT_ID_QUERY = "SELECT g.group_id , g.group_name " +
                                                                  "FROM groups g " +
                                                                  "JOIN students s ON g.group_id = s.group_id " +
                                                                  "WHERE s.student_id = ?";

    public GroupDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, UPDATE_QUERY, DELETE_QUERY);
    }

    @Override
    public List<Group> getAllByMinQuantityStudent() {
        return null;
    }

    @Override
    public Group findGroupByStudentId(int id){
        Group group;
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(FIND_GROUP_BY_STUDENT_ID_QUERY)) {
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();

            group = mapResultSetToEntity(resultSet);

        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion is failed", e);
        }
        return  group;
    }

    @Override
    protected void insertValues(PreparedStatement preparedStatement, Group group) throws SQLException {
        preparedStatement.setInt(1,group.getId());
        preparedStatement.setString(2,group.getName());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Group group) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_QUERY)) {
            prepareStatement.setInt(1, group.getId());
            prepareStatement.setString(2, group.getName());
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    protected Group mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getInt("group_id"));
        group.setName(resultSet.getString("group_name"));
        return group;
    }

}
