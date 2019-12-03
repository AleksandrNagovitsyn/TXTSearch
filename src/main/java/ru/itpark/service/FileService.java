package ru.itpark.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Data
public class FileService {
    private Path uploadPath;
    public FileService() throws IOException {
        uploadPath = Paths.get(System.getenv("UPLOAD_PATH"));
        if (Files.notExists(uploadPath)) {
            Files.createDirectory(uploadPath);
        }
    }



    public void writeFile (Part file) {
        String id = UUID.randomUUID().toString();
        if (file != null && file.getSize() != 0) {
            try {
                file.write(uploadPath.resolve(id).toString());
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
//              TODO: додумать обработку
            }
        }
    }
}
