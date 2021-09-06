package ru.ilin.school.dao.Impl;

import ru.ilin.school.dao.interfaces.CrudDao;
import ru.ilin.school.dao.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Long> {

    protected final DBConnector connector;
    private final String saveQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    public AbstractCrudDaoImpl(DBConnector connector, String saveQuery, String updateQuery, String deleteByIdQuery) {
        this.connector = connector;
        this.saveQuery = saveQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    @Override
    public void save(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {
            insertValues(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public void update(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            updateValues(preparedStatement, entity);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Update is failed", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Delete is failed",e);
        }
    }

    protected abstract void insertValues(PreparedStatement preparedStatement, E entity) throws SQLException;
    protected abstract void updateValues(PreparedStatement preparedStatement, E entity) throws SQLException;
    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;
}
