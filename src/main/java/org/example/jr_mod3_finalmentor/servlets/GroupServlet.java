package org.example.jr_mod3_finalmentor.servlets;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.jr_mod3_finalmentor.utils.Utils;
import org.example.jr_mod3_finalmentor.models.Group;
import org.example.jr_mod3_finalmentor.services.GroupService;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + GROUPS + SLASH + ASTERISK)
@Log4j2
@Setter
public class GroupServlet extends HttpServlet {

    private GroupService groupService;
    private String errorMessage;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        groupService = (GroupService) servletContext.getAttribute(GROUP_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //получение тела запроса
        BufferedReader reader = req.getReader();
        String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            groupService.addGroup(this, requestBody);
            resp.setStatus(201);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String lastName = req.getParameter(LAST_NAME);
        String groupNumber = req.getParameter(GROUP_NUMBER);
        try {
            List<Group> filteredGroups = groupService.getGroups(lastName, groupNumber);
            if (req.getHeader("User-Agent").startsWith("Postman")) Utils.getJson(resp, filteredGroups.toString());
            else {
                req.setAttribute(GROUPS, filteredGroups);
                req.setAttribute(LAST_NAME, lastName);
                req.setAttribute(GROUP_NUMBER, groupNumber);
                req.getRequestDispatcher(GROUPS_JSP).forward(req, resp);
            }
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String requestUrl = req.getRequestURL().toString();
        String[] studentsId = req.getParameterValues(STUDENT_ID);
        try {
            groupService.editGroup(this, requestUrl, studentsId);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(400);
        }
    }
}
