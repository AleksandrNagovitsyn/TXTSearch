package ru.itpark.servlet;

import ru.itpark.service.FileService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Paths;

public class TextServlet extends HttpServlet {
    FileService fileService;
    @Override
    public void init()  {

        fileService = new FileService();

    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getPathInfo()  != null) {
            var parts = req.getPathInfo().split("/");

            if (parts.length != 2) {
                throw new RuntimeException("Not Found");
            }
            System.out.println(parts[1]);

            fileService.readFile(Paths.get(System.getenv("RESULTS")).toString(), parts[1] , resp.getOutputStream());

        }

    }
}
