package org.example.jr_mod3_finalmentor.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.jr_mod3_finalmentor.models.LocalDB;
import org.example.jr_mod3_finalmentor.services.TeacherService;

import java.io.IOException;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + TEACHERS + SLASH + ASTERISK)
public class TeacherServlet extends HttpServlet {

    private LocalDB db;
    private TeacherService teacherService;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        db = (LocalDB) servletContext.getAttribute(LOCAL_DB);
        teacherService = new TeacherService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        teacherService.addTeacher(req, resp, db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        teacherService.getTeachers(req, resp, db);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        teacherService.editTeacher(req, resp, db);
    }
}
