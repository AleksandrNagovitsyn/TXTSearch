package ru.itpark.util;



import ru.itpark.exception.NoDataAccessException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public interface JdbcTemplate {

    public static void init(DataSource ds, String creatingStatement) {

               try (
                       Connection connection = ds.getConnection();
                       Statement statement = connection.createStatement();

               ) {
                   statement.execute(creatingStatement);
               } catch (SQLException e) {
                   e.printStackTrace();
               }
    }




    public static <T> Deque<T> executeQuery(DataSource ds, String sql, RowMapper<T> mapper) throws SQLException {
        try (
                Connection connection = ds.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            Deque<T> results = new LinkedList<>() {
            };
            while (resultSet.next()) {
                results.addFirst(mapper.map(resultSet));
            }
            return results;
        }
    }

    public static void insert(DataSource ds, String sql, StatementSetter statementSetter) {


        try (   Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statementSetter.set(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new NoDataAccessException(e);
        }
    }

    public static void update(DataSource ds, String sql, StatementSetter statementSetter) {


        try (   Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statementSetter.set(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new NoDataAccessException(e);
        }
    }
}
