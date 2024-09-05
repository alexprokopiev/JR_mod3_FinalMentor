package org.example.jr_mod3_finalmentor.servlets;

import java.io.*;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.jr_mod3_finalmentor.models.*;
import org.example.jr_mod3_finalmentor.services.InitService;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebServlet(SLASH + INIT)
@Log4j2
@Setter
public class InitServlet extends HttpServlet {

    private LocalDB db;
    private ServletContext servletContext;
    private InitService initService;
    private String errorMessage;

    @Override
    public void init(ServletConfig config) {
        servletContext = config.getServletContext();
        db = (LocalDB) servletContext.getAttribute(LOCAL_DB);
        initService = (InitService) servletContext.getAttribute(INIT_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            initService.initLocalDB(this);
        } catch (Exception e) {
            log.error(errorMessage);
            resp.setStatus(500);
        }
        servletContext.setAttribute(LOCAL_DB, db);
        servletContext.setAttribute(SUBJECT, Subject.values());
        servletContext.getRequestDispatcher(INDEX_JSP).forward(req,resp);
    }
}