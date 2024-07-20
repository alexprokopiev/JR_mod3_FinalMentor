package org.example.jr_mod3_finalmentor.listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import org.example.jr_mod3_finalmentor.models.LocalDB;

import static org.example.jr_mod3_finalmentor.consts.Constants.LOCAL_DB;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        LocalDB db = new LocalDB();
        ctx.setAttribute(LOCAL_DB, db);
        ServletContextListener.super.contextInitialized(sce);
    }
}
