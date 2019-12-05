package ru.itpark.repository;

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

@RequiredArgsConstructor
public class RepositoryJdbcImpl implements Repository<Text> {
//    private DataSource dataSource;
//    TODO: что за datasource
//    TODO: ВАЖНО одно из двух используем , либо датасоурс, либо jdbc template? А ЧТО И ПОЧЕМУ ЛУЧШЕ?

//    public void init() {
//        try {
//            var context = new InitialContext();
//            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/db");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//
//        try (
//                Connection connection = dataSource.getConnection();
//                Statement statement = connection.createStatement();
//
//        ) {
//            statement.execute("CREATE TABLE IF NOT EXISTS books (id TEXT PRIMARY KEY, textURI TEXT NOT NULL, status TEXT NOT NULL)");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public void init() {
        try {
            JdbcTemplate.init("CREATE TABLE IF NOT EXISTS books (id TEXT PRIMARY KEY, name TEXT NOT NULL textURI TEXT NOT NULL)");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Text> getAll() {
        List<Text> texts;
        try {
            texts = JdbcTemplate.executeQuery("SELECT id, textURI",
                    resultSet -> (new Text(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("textURI")
                    ))
            );
            return texts;
        } catch (SQLException e) {
            throw new SqlRunTimeException("Some problems here. Call to developer");
        } catch (NamingException e) {
            throw new NamingRuntimeException("Some problems here. Call to developer");
        }
    }


    public Text save (Text text)  {
        try {
            return text.getId().equals("0") ? insert(text): update(text);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("some problems here");
        }
    }


    public Text insert (Text text) throws NamingException {
        String id = UUID.randomUUID().toString();
        text.setId(id);
         JdbcTemplate.insert("INSERT INTO books (id, name, textURI) VALUES(?, ?, ?)", (statement) -> {
            statement.setString(1, text.getId());
            statement.setString(2, text.getName());
            statement.setString(3, text.getTextURI());
        });
        return text;
    }
    public Text update (Text text) throws NamingException {
        JdbcTemplate.update("UPDATE books SET name =?, textURI =? WHERE id = ?", (statement) -> {
            statement.setString(1, text.getName());
            statement.setString(2, text.getTextURI());
            statement.setString(3, text.getId());
        });
        return text;
    }


}






//    @Override
//    public List<Text> getAll() {
//        List<Text> texts;
//        try {
//            texts = JdbcTemplate.executeQuery(
//
//                    "SELECT id, price, rooms, district, metro FROM houses",
//                    resultSet -> (new House(
//                            resultSet.getInt("id"),
//                            resultSet.getInt("price"),
//                            resultSet.getInt("rooms"),
//                            resultSet.getString("district"),
//                            resultSet.getString("metro")
//                    )
//                    ));
//
//            return houses;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("some problems here");
//        }
//    }


//    public House save (House house)  {
//        try {
//            return house.getId() == 0 ? insert(house): update(house);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("some problems here");
//        }
//    }
//
//
//    public House insert (House house) throws SQLException {
//        int id = JdbcTemplate.insert(url,"INSERT INTO houses (price, rooms, district, metro) VALUES(?, ?, ?, ?)", (statement) -> {
//            statement.setInt(1, house.getPrice());
//            statement.setInt(2, house.getRooms());
//            statement.setString(3, house.getDistrict());
//            statement.setString(4, house.getMetro());
//        });
//        house.setId(id);
//        return house;
//    }
//    public House update (House house) throws SQLException {
//        JdbcTemplate.update(url,"UPDATE houses SET price =?, rooms =?, district =?, metro =? WHERE id = ?", (statement) -> {
//            statement.setInt(1, house.getPrice());
//            statement.setInt(2, house.getRooms());
//            statement.setString(3, house.getDistrict());
//            statement.setString(4, house.getMetro());
//            statement.setInt(5, house.getId());
//        });
//        return house;
//    }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
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
