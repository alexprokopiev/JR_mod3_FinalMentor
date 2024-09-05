package org.example.jr_mod3_finalmentor.servlets;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.jr_mod3_finalmentor.utils.Utils;
import org.example.jr_mod3_finalmentor.models.Lesson;
import org.example.jr_mod3_finalmentor.services.TimetableService;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + TIMETABLE + SLASH + ASTERISK)
@Log4j2
@Setter
public class TimetableServlet extends HttpServlet {

    private TimetableService timetableService;
    private String errorMessage;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        timetableService = (TimetableService) servletContext.getAttribute(TIMETABLE_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            timetableService.addLesson(this, requestBody);
            resp.setStatus(201);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String[] parameters = {
                req.getParameter(GROUP_NUMBER),
                req.getParameter(LAST_NAME),
                req.getParameter(FULL_NAME),
                req.getParameter(DATE)
        };
        try {
            List<Lesson> filteredTimetable = timetableService.getTimetable(parameters);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredTimetable.toString());
            else {
                req.setAttribute(TIMETABLE, filteredTimetable);
                req.setAttribute(GROUP_NUMBER, parameters[0]);
                req.setAttribute(LAST_NAME, parameters[1]);
                req.setAttribute(FULL_NAME, parameters[2]);
                req.setAttribute(DATE, parameters[3]);
                req.getRequestDispatcher(TIMETABLE_JSP).forward(req, resp);
            }
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String requestUrl = req.getRequestURL().toString();
        String[] parameters = {
                req.getParameter(GROUP_ID),
                req.getParameter(TEACHER_ID),
                req.getParameter(START_TIME),
                req.getParameter(END_TIME)
        };
        try {
            timetableService.editLesson(this, requestUrl, parameters);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }
}
