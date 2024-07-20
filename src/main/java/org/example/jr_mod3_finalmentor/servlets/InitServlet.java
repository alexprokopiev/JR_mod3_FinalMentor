package org.example.jr_mod3_finalmentor.servlets;

import java.io.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.jr_mod3_finalmentor.models.LocalDB;
import org.example.jr_mod3_finalmentor.models.Subject;
import org.example.jr_mod3_finalmentor.services.InitService;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + INIT)
public class InitServlet extends HttpServlet {

    private LocalDB db;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) {
        servletContext = config.getServletContext();
        db = (LocalDB) servletContext.getAttribute(LOCAL_DB);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InitService initService = new InitService();
        initService.initLocalDB(db, resp);
        servletContext.setAttribute(LOCAL_DB, db);
        servletContext.setAttribute(SUBJECT, Subject.values());
        servletContext.getRequestDispatcher(INDEX_JSP).forward(req,resp);
    }
}