package ru.itpark.repository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.itpark.exception.NamingRuntimeException;
import ru.itpark.exception.SqlRunTimeException;
import ru.itpark.model.Text;
import util.JdbcTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class RepositoryJdbcImpl implements Repository<Text> {
    private DataSource dataSource;

//    TODO: что за datasource
//    TODO: ВАЖНО одно из двух используем , либо датасоурс, либо jdbc template? А ЧТО И ПОЧЕМУ ЛУЧШЕ?


    @Override
    public void init() throws NamingException, SQLException {

        JdbcTemplate.init(dataSource, "CREATE TABLE IF NOT EXISTS books " +
                "(id TEXT PRIMARY KEY, name TEXT NOT NULL, textURI TEXT NOT NULL)");
    }


    @Override
    public List<Text> getAll() {
        List<Text> texts;
        try {

            texts = JdbcTemplate.executeQuery(dataSource, "SELECT id, name, textURI FROM books",
                    resultSet -> (new Text(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("textURI")
                    ))
            );
            return texts;
        } catch (SQLException e) {
            throw new SqlRunTimeException("Some problems here. Call to developer");
        }
    }


    public Text save (Text text)  {
        try {
            return text.getId().equals(null) ? insert(text): update(text);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("Such ID not found");
        }
    }


    public Text insert (Text text) {
        String id = UUID.randomUUID().toString();
        text.setId(id);
         JdbcTemplate.insert(dataSource,"INSERT INTO books (id, name, textURI) VALUES(?, ?, ?)", (statement) -> {
            statement.setString(1, text.getId());
            statement.setString(2, text.getName());
            statement.setString(3, text.getTextURI());
        });
        return text;
    }
    public Text update (Text text) throws NamingException {
        JdbcTemplate.update(dataSource,"UPDATE books SET name =?, textURI =? WHERE id = ?", (statement) -> {
            statement.setString(1, text.getName());
            statement.setString(2, text.getTextURI());
            statement.setString(3, text.getId());
        });
        return text;
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
