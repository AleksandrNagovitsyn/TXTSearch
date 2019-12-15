package ru.itpark.service;

import lombok.Data;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Path;

@Data
public class FileService {
//    private Repository<Text> currentRepository;


    public String writeFile (Path path, Part file) {
        String uploadedTextPath;
//        String id = UUID.randomUUID().toString();
        if (file != null && file.getSize() != 0) {
            try {
                file.write(path.resolve(file.getSubmittedFileName()).toString());
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
//              TODO: додумать обработку
            }
        }
        return uploadedTextPath = path.resolve(file.getSubmittedFileName()).toString();
    }
}
