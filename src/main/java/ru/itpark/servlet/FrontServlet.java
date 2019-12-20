package ru.itpark.servlet;

import ru.itpark.constants.Constants;
import ru.itpark.model.Query;
import ru.itpark.repository.RepositoryJdbcImpl;
import ru.itpark.service.BookService;
import ru.itpark.service.FileService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@MultipartConfig
public class FrontServlet extends HttpServlet {
    private BookService bookService;
    private FileService fileService;
    private Path uploadPath;
    private Path exitDirectory;
    private DataSource dataSource;
    private InitialContext context;
    private Queue<Query> items;

    private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    @Override
    public void init() {
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/db");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        bookService = new BookService(new RepositoryJdbcImpl(dataSource));

        uploadPath = Paths.get(System.getenv("UPLOAD_PATH"));
        if (Files.notExists(uploadPath)) {
            try {
                Files.createDirectory(uploadPath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        exitDirectory = Paths.get(System.getenv("RESULTS"));
        if (Files.notExists(exitDirectory)) {
            try {
                Files.createDirectory(exitDirectory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileService = new FileService();



    }

    //TODO: не поня лкак дебажить
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI().substring(req.getContextPath().length());

        if (url.equals("/")) {
            List <Path> filesNames = new ArrayList<>();
            filesNames.clear();

            List <Path> files = Files.list(uploadPath).collect(Collectors.toList());
            files.forEach(o -> filesNames.add(o.getFileName()));

            req.setAttribute("Up", filesNames);
          req.getRequestDispatcher("/WEB-INF/FrontJsp.jsp").forward(req, resp);



        }
        if (req.getMethod().equals("POST")) {
            String action = req.getParameter("action");

            if (action.equals("save")) {
                Part file = req.getPart("file");
                System.out.println(file);
                Collection<Part> files = req.getParts();
                fileService.writeFile(uploadPath, files);
                resp.sendRedirect(req.getContextPath());
            }
        }
         if (req.getMethod().equals("GET"))   {
            if (url.equals("/search")) {
                String q = req.getParameter("q");
                try {
                    Path pathOfResult = bookService.search(exitDirectory, q);

                    req.setAttribute(Constants.PATH, exitDirectory.resolve(pathOfResult.getFileName()));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                items = bookService.showQuery();

                req.setAttribute(Constants.ITEMS, items);

                req.getRequestDispatcher("/WEB-INF/Searched.jsp").forward(req, resp);
            }
        }

        req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
    }
}
