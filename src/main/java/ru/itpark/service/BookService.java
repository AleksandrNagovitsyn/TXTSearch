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
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class BookService {

    private Repository<Query> currentRepository;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public BookService(Repository<Query> currentRepository) {
        this.currentRepository = currentRepository;
        try {
            this.currentRepository.init();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Queue<Query> showQuery() {
        Deque<Query> allQueries = new LinkedList<>(currentRepository.getAll());
        return allQueries;
    }

    public Path search(Path path, String searchingString) throws ExecutionException, InterruptedException {
        Query query = new Query(searchingString, QueryStatus.ENQUEUED);
        currentRepository.save(query);

        Future<Path> tsk = executor.submit(() -> {
            query.setStatus(QueryStatus.INPROGRESS);
            currentRepository.save(query);

            Path createdFile = Files.createFile(path.resolve(query.getId()));

            List<String> founded = new ArrayList<>();

            List<Path> pathOfTexts = Files.list(Paths.get(System.getenv("UPLOAD_PATH")))
                    .collect(Collectors.toList());
            toScan(pathOfTexts, searchingString, founded);
//            for (Path pathOfText : pathOfTexts) {
//                if (Files.exists(pathOfText)) {
//
//                    try {
//                        strings = Files.readAllLines(pathOfText)
//                                .stream()
//                                .filter(o -> o.toLowerCase().contains(searchingString.toLowerCase()))
//                                .collect(Collectors.toList());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    strings.forEach(s -> {
//                        s = ("[" + pathOfText.getFileName().toString() + "] ").toUpperCase().concat(s);
//                        founded.add(s);
//                    });
//                }
//            }
            Files.write(createdFile, founded);

            return createdFile;
        });

        while (!tsk.isDone()) {
        }
        System.out.println(tsk.isDone());
        query.setStatus(QueryStatus.DONE);
        currentRepository.save(query);

        return tsk.get();


    }

    public void toScan(List<Path> pathOfTexts, String searchingString, List<String> founded) throws IOException {
        for (Path pathOfText : pathOfTexts) {
            if (Files.exists(pathOfText)) {

                List<String> strings = Files.readAllLines(pathOfText)
                        .stream()
                        .filter(o -> o.toLowerCase().contains(searchingString.toLowerCase()))
                        .collect(Collectors.toList());
                strings.forEach(s -> {
                    s = ("[" + pathOfText.getFileName().toString() + "] ").toUpperCase().concat(s);
                    founded.add(s);
                });
            }
        }
    }

}


