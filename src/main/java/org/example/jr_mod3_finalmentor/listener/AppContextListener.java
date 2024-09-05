package org.example.jr_mod3_finalmentor.listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import org.example.jr_mod3_finalmentor.services.*;
import org.example.jr_mod3_finalmentor.models.LocalDB;
import com.fasterxml.jackson.databind.json.JsonMapper;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        LocalDB db = new LocalDB();
        JsonMapper mapper = new JsonMapper();
        InitService initService = new InitService(db, mapper);
        StudentService studentService = new StudentService(db, mapper);
        TeacherService teacherService = new TeacherService(db, mapper);
        GroupService groupService = new GroupService(db, mapper);
        TimetableService timetableService = new TimetableService(db, mapper);
        ctx.setAttribute(LOCAL_DB, db);
        ctx.setAttribute(JSON_MAPPER, mapper);
        ctx.setAttribute(INIT_SERVICE, initService);
        ctx.setAttribute(STUDENT_SERVICE, studentService);
        ctx.setAttribute(TEACHER_SERVICE, teacherService);
        ctx.setAttribute(GROUP_SERVICE, groupService);
        ctx.setAttribute(TIMETABLE_SERVICE, timetableService);
        ServletContextListener.super.contextInitialized(sce);
    }
}
