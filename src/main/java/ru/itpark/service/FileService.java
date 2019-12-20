package ru.itpark.service;

import lombok.Data;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class FileService {


    public String writeFile (Path path, Part file) throws IOException {
        if (file != null && file.getSize() != 0) {
                file.write(path.resolve(file.getSubmittedFileName()).toString());
                file.delete();
        }
        return  path.resolve(file.getSubmittedFileName()).toString();

    }



    public void readFile (String envPath, String id, ServletOutputStream servletOutputStream) throws IOException {
        Path path = Paths.get(envPath).resolve(id);
        Files.copy(path, servletOutputStream);

    }
}
