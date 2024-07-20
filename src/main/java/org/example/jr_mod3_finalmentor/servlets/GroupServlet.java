package org.example.jr_mod3_finalmentor.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.example.jr_mod3_finalmentor.models.LocalDB;
import org.example.jr_mod3_finalmentor.services.GroupService;

import java.io.IOException;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + GROUPS + SLASH + ASTERISK)
public class GroupServlet extends HttpServlet {

    private LocalDB db;
    private GroupService groupService;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        db = (LocalDB) servletContext.getAttribute(LOCAL_DB);
        groupService = new GroupService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        groupService.addGroup(req, resp, db);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        groupService.getGroups(req, resp, db);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        groupService.editGroup(req, resp, db);
    }
}
