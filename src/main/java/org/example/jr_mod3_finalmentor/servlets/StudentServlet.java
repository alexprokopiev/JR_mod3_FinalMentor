package org.example.jr_mod3_finalmentor.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.jr_mod3_finalmentor.models.LocalDB;
import org.example.jr_mod3_finalmentor.services.StudentService;

import java.io.IOException;
import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + STUDENTS + SLASH + ASTERISK)
public class StudentServlet extends HttpServlet {

    private LocalDB db;
    private StudentService studentService;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        db = (LocalDB) servletContext.getAttribute(LOCAL_DB);
        studentService = new StudentService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        studentService.addStudent(req, resp, db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        studentService.getStudents(req, resp, db);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        studentService.editStudent(req, resp, db);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        studentService.deleteStudent(req, resp, db);
    }
}
