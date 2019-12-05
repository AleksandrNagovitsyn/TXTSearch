package ru.itpark.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itpark.model.Text;
import ru.itpark.repository.Repository;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Data
public class FileService {
//    private Repository<Text> currentRepository;
    public FileService(Repository <Text> currentRepository) throws IOException {
        currentRepository.init();
    }


    public void writeFile (Path path, Part file) {
        String id = UUID.randomUUID().toString();
        if (file != null && file.getSize() != 0) {
            try {
                file.write(path.resolve(id).toString());
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
//              TODO: додумать обработку
            }
        }
    }
}
