package org.example.jr_mod3_finalmentor.servlets;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.jr_mod3_finalmentor.utils.Utils;
import org.example.jr_mod3_finalmentor.models.Teacher;
import org.example.jr_mod3_finalmentor.services.TeacherService;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + TEACHERS + SLASH + ASTERISK)
@Log4j2
@Setter
public class TeacherServlet extends HttpServlet {

    private TeacherService teacherService;
    private String errorMessage;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        teacherService = (TeacherService) servletContext.getAttribute(TEACHER_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            teacherService.addTeacher(this, requestBody);
            resp.setStatus(201);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Teacher> teachers = teacherService.getTeachers();
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, teachers.toString());
            else {
                req.setAttribute(TEACHERS, teachers);
                req.getRequestDispatcher(TEACHERS_JSP).forward(req, resp);
            }
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String requestUrl = req.getRequestURL().toString();
        String subjectAsString = req.getParameter(SUBJECT);
        try {
            teacherService.editTeacher(this, requestUrl, subjectAsString);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }
}
