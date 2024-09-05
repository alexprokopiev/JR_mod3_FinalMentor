package org.example.jr_mod3_finalmentor.servlets;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.jr_mod3_finalmentor.utils.Utils;
import org.example.jr_mod3_finalmentor.models.Student;
import org.example.jr_mod3_finalmentor.services.StudentService;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + STUDENTS + SLASH + ASTERISK)
@Log4j2
@Setter
public class StudentServlet extends HttpServlet {

    private StudentService studentService;
    private String errorMessage;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        studentService = (StudentService) servletContext.getAttribute(STUDENT_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //получение тела запроса
        BufferedReader reader = req.getReader();
        String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            studentService.addStudent(this, requestBody);
            resp.setStatus(201);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String requestUrl = req.getRequestURL().toString();
        String lastName = req.getParameter(LAST_NAME);
        try {
            List<Student> filteredStudents = studentService.getStudents(this, requestUrl, lastName);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredStudents.toString());
            else {
                req.setAttribute(STUDENTS, filteredStudents);
                req.setAttribute(LAST_NAME, lastName);
                req.setAttribute(STUDENT_ID, Utils.getId(requestUrl));
                req.getRequestDispatcher(STUDENTS_JSP).forward(req, resp);
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
                req.getParameter(NAME),
                req.getParameter(PATRONYMIC),
                req.getParameter(LAST_NAME),
                req.getParameter(BIRTH_DATE),
                req.getParameter(PHONE_NUMBER)
        };
        try {
            studentService.editStudent(this, requestUrl, parameters);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String requestUrl = req.getRequestURL().toString();
        try {
            studentService.deleteStudent(this, requestUrl);
            resp.setStatus(204);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }
}
