package ru.itpark.service;

import lombok.Data;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

@Data
public class FileService {


    public void writeFile(Path path, Collection<Part> files) {

        for (Part file : files) {
            if (file != null && file.getSize() != 0) {
                System.out.println(path.resolve(file.getSubmittedFileName()).toString());
                try {
                    file.write(path.resolve(file.getSubmittedFileName()).toString());
                    file.delete();
                } catch (IOException e) {
                    e.printStackTrace();
//              TODO: додумать обработку
                }

            }

//        files.forEach((Part file)-> {
//            System.out.println(file);
//        if (file != null && file.getSize() != 0) {
//            try {
//                file.write(path.resolve(file.getSubmittedFileName()).toString());
//                file.delete();
//            } catch (IOException e) {
//                e.printStackTrace();
////              TODO: додумать обработку
//            }
//        }
//        });
//        return  path.resolve(file.getSubmittedFileName()).toString();

        }
    }

        public void readFile (String envPath, String id, ServletOutputStream servletOutputStream) throws IOException {
            Path path = Paths.get(envPath).resolve(id);
            Files.copy(path, servletOutputStream);

        }
    }
