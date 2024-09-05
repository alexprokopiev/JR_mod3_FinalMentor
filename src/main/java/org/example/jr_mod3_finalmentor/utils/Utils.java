package org.example.jr_mod3_finalmentor.utils;

import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.example.jr_mod3_finalmentor.models.UtilsExecutable;

import java.util.List;
import java.util.regex.*;
import java.io.IOException;
import java.util.function.Predicate;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;

@Log4j2
public class Utils {

    //парсинг ID из URL
    public static int getId(String requestUrl) {
        int id = 0;
        String idAsString = StringUtils.substringBefore(StringUtils.substringAfterLast(requestUrl, "/"), "?");
        if (!idAsString.isEmpty() && !idAsString.equals(STUDENTS)) {
            try {
                id = Integer.parseInt(idAsString);
            } catch (NumberFormatException e) {
                id = -1;
            }
        }
        return id;
    }

    //поиск элементов списка по условию
    public static <T> List<T> findListElementsByParameter(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .toList();
    }

    //отправка http ответа в формате json
    public static void getJson(HttpServletResponse resp, String s) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().write(s);
    }


    //поиск уникального ID для объекта
    public static int findUniqueId(List<? extends UtilsExecutable> list) {
        int i = 1;
        int j = 0;
        while (true) {
            for (; j < list.size(); j++) {
                if (i == list.get(j).getId()) {
                    i++;
                    j = 0;
                    break;
                }
            }
            if (j == list.size()) {
                return i;
            }
        }
    }

    //проверка существования элемента под переданным ID в списке
    public static boolean isExistId(List<? extends UtilsExecutable> list, int id) {
        for (UtilsExecutable element : list) {
            if (id == element.getId()) return true;
        }
        return false;
    }

    //проверка переданных данных на соответствие требованиям системы
    public static boolean isValidData(String data, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
}
