package ru.itpark.repository;

import lombok.AllArgsConstructor;
import ru.itpark.exception.SqlRunTimeException;
import ru.itpark.model.Query;
import util.JdbcTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class RepositoryJdbcImpl implements Repository<Query> {
    private DataSource dataSource;

//    TODO: что за datasource
//    TODO: ВАЖНО одно из двух используем , либо датасоурс, либо jdbc template? А ЧТО И ПОЧЕМУ ЛУЧШЕ?


    @Override
    public void init() throws NamingException, SQLException {

        JdbcTemplate.init(dataSource, "CREATE TABLE IF NOT EXISTS queries " +
                "(id TEXT PRIMARY KEY, query TEXT NOT NULL, status TEXT NOT NULL)");
    }


    @Override
    public List<Query> getAll() {
        List<Query> texts;
        try {

            texts = JdbcTemplate.executeQuery(dataSource, "SELECT id, query, status FROM books",
                    resultSet -> (new Query(
                            resultSet.getString("id"),
                            resultSet.getString("query"),
                            resultSet.getString("status")
                    ))
            );
            return texts;
        } catch (SQLException e) {
            throw new SqlRunTimeException("Some problems here. Call to developer");
        }
    }


    public Query save (Query query)  {
        try {
            return query.getId() == null ? insert(query): update(query);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("Such ID not found");
        }
    }


    public Query insert (Query query) {

         JdbcTemplate.insert(dataSource,"INSERT INTO queries (id, query, status) VALUES(?, ?, ?)", (statement) -> {
            statement.setString(1, query.getId());
            statement.setString(2, query.getQuery());
            statement.setString(3, query.getStatus().toString());
        });
        return query;
    }
    public Query update (Query query) throws NamingException {
        JdbcTemplate.update(dataSource,"UPDATE queries SET query =?, status =? WHERE id = ?", (statement) -> {
            statement.setString(1, query.getQuery());
            statement.setString(2, query.getStatus().toString());
            statement.setString(3, query.getId());
        });
        return query;
    }


}





//
//@RequiredArgsConstructor
//public class QueryRepositorySqliteImpl implements QueryRepository {
//    private final DataSource dataSource;
//
//    @Override
//    public void init() {
//        try (
//                Connection connection = dataSource.getConnection();
//                Statement statement = connection.createStatement();
//
//        ) {
//            statement.execute("CREATE TABLE queries (id TEXT PRIMARY KEY, query TEXT NOT NULL, status TEXT NOT NULL)");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public List<QueryModel> getAll() {
//        try (
//                Connection connection = dataSource.getConnection();
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery("SELECT id, query, status FROM queries");
//        ) {
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("id"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return Collections.emptyList();
//    }
//}
