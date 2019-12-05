package ru.itpark.service;

import ru.itpark.model.Text;
import ru.itpark.repository.Repository;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

public class BookService {
    private Collection<Text> searchedText = new HashSet<>();
    Repository<Text> currentRepository;
    private DataSource ds;


    public BookService () throws SQLException, NamingException {
        InitialContext context = new InitialContext();
        ds = (DataSource) context.lookup("java:/comp/env/jdbc/db");
        try (
                Connection connection = ds.getConnection()
        ){
            try(Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS books (id TEXT PRIMARY KEY, name TEXT NOT NULL, textURI TEXT NOT NULL)");
            }
        }

    }


    public void register (Text t) throws SQLException {
        try (   Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO books (id, name, textURI) VALUES(?, ?, ?)")
        ) {

            statement.setString(1, t.getId());
            statement.setString(2, t.getName());
            statement.setString(2, t.getTextURI());
            statement.executeUpdate();
            }

    }

    public List<Text> getAll() {
        try (
                Connection connection = ds.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT id, name, textURI FROM books");
        ) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

//    public String save ();

//    TODO:  записыват файл - дает назавние - присваивает id

}




//package ru.itpark.service;
//
//        import ru.itpark.exception.NotFoundException;
//        import ru.itpark.model.House;
//
//        import javax.servlet.http.Part;
//        import java.io.IOException;
//        import java.nio.file.Files;
//        import java.nio.file.Path;
//        import java.util.*;
//        import java.util.stream.Collectors;
//
//// FIXME: для примера делаем всё в одном сервисе, хотя хорошо бы для работы с файлами сделать отдельный сервис, как в видео
//public class HouseService {
//    private final Collection<House> items = new LinkedList<>();
//
//    public Collection<House> getAll() {
//        return items;
//    }
//
//    public Collection<House> searchByDistrict(String district) {
//        return items.stream()
//                .filter(o -> o.getDistrict().equals(district))
//                .collect(Collectors.toList());
//    }
//
//    public Collection<House> searchByUnderground(String underground) {
//        return items.stream()
//                .filter(o -> o.getUndergrounds().contains(underground))
//                .collect(Collectors.toList());
//    }
//
//    public void save(String id, String district, String undergrounds, String price, Part file, Path path) {
//        // "  Козья  "
//        final List<String> parsedUndergrounds = Arrays.stream(undergrounds.split(" "))
//                .filter(o -> !o.isEmpty())
//                .collect(Collectors.toList());
//
//        final int parsedPrice = Integer.parseInt(price);
//        if (id.equals("")) {
//            id = generateId();
//            writeFile(id, file, path);
//            insert(new House(id, district, parsedUndergrounds, parsedPrice));
//            return;
//        }
//
//        writeFile(id, file, path);
//        update(new House(id, district, parsedUndergrounds, parsedPrice));
//    }
//
//    private void writeFile(String id, Part file, Path uploadPath) {
//        if (file != null && file.getSize() != 0) {
//            try {
//                file.write(uploadPath.resolve(id).toString());
//                file.delete();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void insert(House house) {
//        items.add(house);
//    }
//
//    public void update(House house) {
//        boolean removed = items.removeIf(o -> o.getId().equals(house.getId()));
//        if (!removed) {
//            throw new NotFoundException();
//        }
//        items.add(house);
//    }
//
//    private String generateId() {
//        return UUID.randomUUID().toString();
//    }
//
//    public void removeById(String id, Path path) {
//        items.removeIf(o -> o.getId().equals(id));
//        try {
//            Files.deleteIfExists(path.resolve(id));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public House getById(String id) {
//        return items.stream()
//                .filter(o -> o.getId().equals(id))
//                .findAny()
//                .orElseThrow(NotFoundException::new);
//    }
//}
