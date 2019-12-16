package ru.itpark.service;

import ru.itpark.enumeration.QueryStatus;
import ru.itpark.model.Query;
import ru.itpark.repository.Repository;

import javax.naming.NamingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class BookService {

    private Repository<Query> currentRepository;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    static int index = 0;


    public BookService(Repository<Query> currentRepository) {
        this.currentRepository = currentRepository;
        try {
            currentRepository.init();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<Query> showQuery() {
        List<Query> allQueries = new ArrayList<>(currentRepository.getAll());
        return allQueries;
    }
//    public void register(Text text) {
//        currentRepository.save(text);
//    }

    public Path search(Path path, String searchingString) throws IOException {
        String id = UUID.randomUUID().toString();

        currentRepository.save(new Query(id, searchingString, QueryStatus.ENQUEUED));
        Path createdFile = Files.createFile(path.resolve("exitTXT" + id));
        List<String> founded = new ArrayList<>();



        List<Path> pathOfTexts = Files.list(Paths.get(System.getenv("UPLOAD_PATH")))
                .collect(Collectors.toList());
        for (Path pathOfText : pathOfTexts) {
            if (Files.exists(pathOfText)) {
                executor.execute(() -> {
                    currentRepository.save(new Query(id, searchingString, QueryStatus.INPROGRESS));
                    List<String> strings = new ArrayList<>();
                    try {
                        strings = Files.readAllLines(pathOfText)
                                .stream()
                                .filter(o -> o.toLowerCase().contains(searchingString.toLowerCase()))
                                .collect(Collectors.toList());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                strings.forEach(s ->
//                    s = pathOfText.getFileName().toString().concat(s));
//                founded.addAll(strings);
//                TODO: почему такой вармант не работает?
                    strings.forEach(s -> {
                        s = ("["+pathOfText.getFileName().toString()+"] ").toUpperCase().concat(s);
                        founded.add(s);
                        try {
                            Files.write(createdFile, founded);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        currentRepository.save(new Query (id, searchingString, QueryStatus.DONE));


                    });
                });
            }
        }
        return createdFile;
    }

    public List<String> showFounded(Path path, String searchingString) throws IOException {
        List<String> founded = Files.readAllLines(search(path, searchingString));
        System.out.println(founded);
        return founded;
//        TODO: здесь косяк, файл не успевает записаться. Надо сделать так, чтобы ждал

    }
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
