package ru.itpark.service;

import ru.itpark.model.Text;
import ru.itpark.repository.Repository;

import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookService {

    private Repository<Text> currentRepository;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public BookService(Repository<Text> currentRepository) {
        this.currentRepository = currentRepository;
        try {
            currentRepository.init();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public Collection<Text> showText() {
//        Collection<Text> searchedText = new HashSet<>(currentRepository.getAll());
//        return searchedText;
//    }
//    public void register(Text text) {
//        currentRepository.save(text);
//    }

    public Path search(Path path, String searchingString) throws IOException {
        String id = UUID.randomUUID().toString();

        Path createdFile = Files.createFile(path.resolve("exitTXT" + id));
        List<String> founded = new ArrayList<>();
//        FIXME: Надо будет создать отдельную папку
        List<Path> pathOfTexts = Files.list(Paths.get(System.getenv("UPLOAD_PATH")))
                .collect(Collectors.toList());
        for (Path pathOfText : pathOfTexts) {
            if (Files.exists(pathOfText)) {
                List<String> strings = Files.readAllLines(pathOfText)
                        .stream()
                        .filter(o->o.contains(searchingString))

                        .collect(Collectors.toList());

                founded.addAll(strings);
                }
            Files.write(createdFile, founded);

        }
//        TODO: если не проканает поиск по одной лишь папке, сделать БД для файлов
//        executor.execute(() -> {
//        TODO: допилить через потоки, почитать на JavaRush про них
        return createdFile;
    }

    public List<String> showFounded (Path path, String searchingString) throws IOException {
         List <String> founded = Files.readAllLines(search(path, searchingString));
         return  founded;

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
