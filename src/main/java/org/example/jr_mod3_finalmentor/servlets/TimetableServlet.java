package org.example.jr_mod3_finalmentor.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.jr_mod3_finalmentor.models.LocalDB;
import org.example.jr_mod3_finalmentor.services.TimetableService;

import java.io.IOException;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + TIMETABLE + SLASH + ASTERISK)
public class TimetableServlet extends HttpServlet {

    private LocalDB db;
    private TimetableService timetableService;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        db = (LocalDB) servletContext.getAttribute(LOCAL_DB);
        timetableService = new TimetableService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        timetableService.addLesson(req, resp, db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        timetableService.getTimetable(req, resp, db);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        timetableService.editLesson(req, resp, db);
    }
}
