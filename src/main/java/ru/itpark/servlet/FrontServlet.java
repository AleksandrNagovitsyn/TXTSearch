package ru.itpark.servlet;

import ru.itpark.model.Text;
import ru.itpark.repository.RepositoryJdbcImpl;
import ru.itpark.service.BookService;
import ru.itpark.service.FileService;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


@MultipartConfig
public class FrontServlet extends HttpServlet {
    private BookService bookService;
    private FileService fileService;
    private Path uploadPath;


    @Override
    public void init() throws ServletException {
        try {
            bookService = new BookService();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
//        InitialContext context = null;
//        try {
//            context = new InitialContext();
//            fileService = (FileService) context.lookup("java:/comp/env/bean/file-service");
//        } catch (NamingException e) {
////            TODO: вот этот ексепшен вылезает
//            throw new ServletException(e);
//        }
        uploadPath = Paths.get(System.getenv("UPLOAD_PATH"));
//        FIXME: если переменной присваивать значение в сервис классе, не работает. Поэкспреременитровать
        if (Files.notExists(uploadPath)) {
            try {
                Files.createDirectory(uploadPath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }



            fileService = new FileService();

    }

    //TODO: не поня лкак дебажить
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Text> books = new HashSet<>();
        req.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI().substring(req.getContextPath().length());

        if (url.equals("/")) {

            try {
                bookService.register(new Text ("1", "AnnaKarenina", "URI"));
                bookService.register(new Text ("2", "AnnaKarenina2", "URI2"));

            } catch (SQLException e) {
                e.printStackTrace();
            }


            System.out.println(uploadPath);
            Collection<Text> items = bookService.getAll();
            req.setAttribute("Items", items.toString());
            req.getRequestDispatcher("/WEB-INF/FrontJsp.jsp").forward(req, resp);

        }
            if (req.getMethod().equals("POST")) {
                Part file = req.getPart("file");
                fileService.writeFile(uploadPath, file);
//                    FIXME: файл не записывается
                resp.sendRedirect("/");
            }

         req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);


    }
}










//package ru.itpark.servlet;
//
//        import ru.itpark.constant.Constants;
//        import ru.itpark.model.House;
//        import ru.itpark.service.HouseService;
//
//        import javax.servlet.ServletException;
//        import javax.servlet.http.HttpServlet;
//        import javax.servlet.http.HttpServletRequest;
//        import javax.servlet.http.HttpServletResponse;
//        import javax.servlet.http.Part;
//        import java.io.File;
//        import java.io.IOException;
//        import java.io.InputStream;
//        import java.net.URISyntaxException;
//        import java.net.URL;
//        import java.nio.file.Files;
//        import java.nio.file.Path;
//        import java.nio.file.Paths;
//        import java.util.*;
//
// Servlet - interface
// HttpServlet - class
//public class FrontServlet extends HttpServlet {
//    private HouseService service;
//    private Path uploadPath;
//
//    // component -> LifeCycle managed by container
//    // init -> инициализация
//    // destroy -> уничтожение
//    @Override
//    public void init() throws ServletException {
//        try {
//            service = new HouseService();
////            service.save(new House("", "Ново-Савиновский", Arrays.asList("Яшьлек", "Козья"), 4, null), uploadPath);
////            service.save(new House("", "Кировский", Collections.emptyList(), 2, null), uploadPath);
//            uploadPath = Paths.get(System.getenv("UPLOAD_PATH"));
//            if (Files.notExists(uploadPath)) {
//                Files.createDirectory(uploadPath);
//            }
//            System.out.println(uploadPath);
//        } catch (IOException e) {
//            throw new ServletException(e);
//        }
//    }
//
//    // TODO: это надо переделать из "одного большого метода", на несколько целевых
//    // Servlet -> service (родительский вообще не вызываем)
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // protected - ok
//        // null - ok
//        // Object - ok
//        // в какой кодировке принимаем данные
//        req.setCharacterEncoding("UTF-8");
//        System.out.println(Thread.currentThread().getName());
//
//        // Servlet -> request/response
//        // Service -> business logic'а
//        // JSP -> view
//
//        // "/"
//        // "".length()
//        String url = req.getRequestURI().substring(req.getContextPath().length());
//        System.out.println(url);
//
//        if (url.equals("/")) {
//            resp.getWriter().write("ok");
//            return;
//        }
//
//        if (url.equals("/houses")) {
//            if (req.getMethod().equals("GET")) {
//                req.setAttribute(Constants.ITEMS, service.getAll());
//                req.getRequestDispatcher("/WEB-INF/houses.jsp").forward(req, resp);
//                return;
//            }
//
//            if (req.getMethod().equals("POST")) {
//                //
//                final String action = req.getParameter("action");
//                if (action.equals("remove")) {
//                    service.removeById(req.getParameter("id"), uploadPath);
//                    resp.sendRedirect(req.getRequestURI());
//                    return;
//                }
//
//                if (action.equals("edit")) {
//                    final String id = req.getParameter("id");
//                    final House item = service.getById(id);
//                    req.setAttribute(Constants.ITEMS, service.getAll());
//                    req.setAttribute(Constants.ITEM, item);
//                    req.getRequestDispatcher("/WEB-INF/houses.jsp").forward(req, resp);
//                    return;
//                }
//
//                if (action.equals("save")) {
//                    // ничего не изменится
//                    final String id = req.getParameter("id");
//                    final String district = req.getParameter("district");
//                    final String undergrounds = req.getParameter("undergrounds");
//                    final String price = req.getParameter("price");
//                    final Part file = req.getPart("file");
//
//                    service.save(id, district, undergrounds, price, file, uploadPath);
//                    // чтобы повторно не отрисовывать, отправляем его на страницу, где итак отрисовывается весь список
//                    resp.sendRedirect(req.getRequestURI());
//                    return;
//                }
//            }
//        }
//
//        if (url.equals("/houses/search")) {
//            final String q = req.getParameter("q");
//            final Collection<House> foundByUnderground = service.searchByUnderground(q);
//            final Collection<House> foundByDistrict = service.searchByDistrict(q);
//
//            Set<House> items = new HashSet<>();
//            items.addAll(foundByUnderground);
//            items.addAll(foundByDistrict);
//
//            req.setAttribute(Constants.ITEMS, items);
//            req.setAttribute(Constants.SEARCH_QUERY, q);
//
//            req.getRequestDispatcher("/WEB-INF/houses.jsp").forward(req, resp);
//            return;
//        }
//
//        // FIXME: это достаточно вольная работа с изображениями
//        if (url.startsWith("/images/")) {
//            String id = url.substring("/images/".length());
//            System.out.println(id);
//            final Path image = uploadPath.resolve(id);
//            if (Files.exists(image)) {
//                Files.copy(image, resp.getOutputStream());
//                return;
//            }
//
//            try {
//                Files.copy(Paths.get(getServletContext().getResource("/WEB-INF/404.png").toURI()), resp.getOutputStream());
//            } catch (URISyntaxException e) {
//                throw new IOException(e);
//            }
//        }
//
//        req.getRequestDispatcher("/WEB-INF/404.jsp").forward(req, resp);
//    }
//}
