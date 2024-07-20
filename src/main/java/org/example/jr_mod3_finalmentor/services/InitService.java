package org.example.jr_mod3_finalmentor.services;

import org.apache.logging.log4j.*;
import jakarta.servlet.http.HttpServletResponse;
import org.example.jr_mod3_finalmentor.models.*;

import java.io.*;
import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;

import static org.example.jr_mod3_finalmentor.consts.Constants.*;
import static org.example.jr_mod3_finalmentor.models.Subject.*;

public class InitService {

    private static final Logger logger = LogManager.getLogger(InitService.class);

    public void initLocalDB(LocalDB db, HttpServletResponse resp) {
        readProperties(db, resp);
        initStudentsList(db);
        initTeachersList(db);
        initGroupslist(db);
        initTimetable(db);
        logger.info(INIT_MSG);
    }

    private void readProperties(LocalDB db, HttpServletResponse resp) {
        Properties props = db.getProps();
        try (InputStream inputStream = InitService.class.getResourceAsStream(PROPS_PATH)) {
            props.load(inputStream);
        } catch (IOException e) {
            System.err.println(PROPERTIES_ERROR);
            resp.setStatus(500);
        }
    }

    private void initStudentsList(LocalDB db) {
        List<Student> students = db.getStudents();
        if (students.isEmpty()) {
            students.add(new Student(1, "Захар", "Степанович", "Шаповалов", LocalDate.parse("04.04.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79911556928"));
            students.add(new Student(2, "Платон", "Михайлович", "Попов", LocalDate.parse("01.06.2010", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79884197690"));
            students.add(new Student(3, "Анастасия", "Егоровна", "Ильина", LocalDate.parse("13.10.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79693660190"));
            students.add(new Student(4, "Полина", "Михайловна", "Боброва", LocalDate.parse("21.11.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79627318718"));
            students.add(new Student(5, "Александра", "Матвеевна", "Ситникова", LocalDate.parse("29.09.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79889609879"));
            students.add(new Student(6, "Вероника", "Леонидовна", "Мельникова", LocalDate.parse("24.10.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79057538939"));
            students.add(new Student(7, "Елизавета", "Владимировна", "Федорова", LocalDate.parse("19.04.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79393475908"));
            students.add(new Student(8, "Полина", "Константиновна", "Филиппова", LocalDate.parse("18.03.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79991999385"));
            students.add(new Student(9, "Ева", "Викторовна", "Ковалева", LocalDate.parse("26.02.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79992125259"));
            students.add(new Student(10, "Никита", "Иванович", "Никитин", LocalDate.parse("12.04.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79319428981"));
            students.add(new Student(11, "Лука", "Лукич", "Сазонов", LocalDate.parse("01.11.2002", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79388645195"));
            students.add(new Student(12, "Семён", "Викторович", "Высоцкий", LocalDate.parse("09.03.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79635925880"));
            students.add(new Student(13, "Леонид", "Никитич", "Ткачев", LocalDate.parse("27.10.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79325360790"));
            students.add(new Student(14, "Денис", "Андреевич", "Сидоров", LocalDate.parse("31.08.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79629519295"));
            students.add(new Student(15, "Аделина", "Кирилловна", "Федорова", LocalDate.parse("01.08.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79190853477"));
            students.add(new Student(16, "Софья", "Владимировна", "Попова", LocalDate.parse("06.11.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79920527792"));
            students.add(new Student(17, "Ярослав", "Иванович", "Калугин", LocalDate.parse("28.05.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79686164096"));
            students.add(new Student(18, "Арина", "Ильинична", "Воробьева", LocalDate.parse("02.06.2010", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79325813265"));
            students.add(new Student(19, "Кира", "Данииловна", "Зубкова", LocalDate.parse("03.11.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79171959704"));
            students.add(new Student(20, "Анастасия", "Адамовна", "Соколова", LocalDate.parse("06.08.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79138589306"));
            students.add(new Student(21, "Александр", "Артёмович", "Ефимов", LocalDate.parse("29.11.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79399329849"));
            students.add(new Student(22, "Олег", "Максимович", "Ильин", LocalDate.parse("08.10.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79332165052"));
            students.add(new Student(23, "Елизавета", "Артёмовна", "Ковалева", LocalDate.parse("10.01.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79217128436"));
            students.add(new Student(24, "Матвей", "Александрович", "Иванов", LocalDate.parse("10.08.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79653477503"));
            students.add(new Student(25, "Николай", "Никитич", "Степанов", LocalDate.parse("26.02.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79816145524"));
            students.add(new Student(26, "Анастасия", "Даниэльевна", "Федорова", LocalDate.parse("19.05.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79380484875"));
            students.add(new Student(27, "Анастасия", "Александровна", "Сахарова", LocalDate.parse("11.12.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79898938311"));
            students.add(new Student(28, "Ксения", "Михайловна", "Терехова", LocalDate.parse("11.06.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79943359683"));
            students.add(new Student(29, "Мия", "Михайловна", "Абрамова", LocalDate.parse("14.03.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79036105189"));
            students.add(new Student(30, "Максим", "Захарович", "Соколов", LocalDate.parse("22.08.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79589165490"));
            students.add(new Student(31, "Иван", "Александрович", "Никитин", LocalDate.parse("26.10.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79231829876"));
            students.add(new Student(32, "Михаил", "Маркович", "Орлов", LocalDate.parse("04.12.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79947067728"));
            students.add(new Student(33, "Елизавета", "Тимофеевна", "Воробьева", LocalDate.parse("24.01.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79551302513"));
            students.add(new Student(34, "Артём", "Тимурович", "Рудаков", LocalDate.parse("11.01.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79718922210"));
            students.add(new Student(35, "Анна", "Макаровна", "Киселева", LocalDate.parse("17.03.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79174728713"));
            students.add(new Student(36, "Дмитрий", "Степанович", "Воронов", LocalDate.parse("14.03.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79789213621"));
            students.add(new Student(37, "Лев", "Юрьевич", "Иванов", LocalDate.parse("01.11.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79284425274"));
            students.add(new Student(38, "Анастасия", "Мироновна", "Лазарева", LocalDate.parse("20.04.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79917695946"));
            students.add(new Student(39, "Алиса", "Дмитриевна", "Воронина", LocalDate.parse("30.05.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79643716475"));
            students.add(new Student(40, "Варвара", "Марковна", "Фокина", LocalDate.parse("15.07.2002", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79930978837"));
            students.add(new Student(41, "Александр", "Алексеевич", "Королев", LocalDate.parse("04.11.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79525459251"));
            students.add(new Student(42, "Мария", "Матвеевна", "Самойлова", LocalDate.parse("10.11.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79626504600"));
            students.add(new Student(43, "Виктория", "Егоровна", "Егорова", LocalDate.parse("18.02.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79569703041"));
            students.add(new Student(44, "Алёна", "Ивановна", "Морозова", LocalDate.parse("20.12.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79939919113"));
            students.add(new Student(45, "Данила", "Денисович", "Котов", LocalDate.parse("09.05.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79515638654"));
            students.add(new Student(46, "Виктория", "Константиновна", "Костина", LocalDate.parse("16.10.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79883755463"));
            students.add(new Student(47, "Дмитрий", "Савельевич", "Белов", LocalDate.parse("18.07.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79170313025"));
            students.add(new Student(48, "Алексей", "Романович", "Иванов", LocalDate.parse("10.01.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79947360053"));
            students.add(new Student(49, "Алексей", "Демидович", "Смирнов", LocalDate.parse("25.03.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79067673583"));
            students.add(new Student(50, "Диана", "Юрьевна", "Фомина", LocalDate.parse("28.07.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79157209009"));
            students.add(new Student(51, "Марина", "Вадимовна", "Овсянникова", LocalDate.parse("02.06.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79413324491"));
            students.add(new Student(52, "Дарья", "Ивановна", "Александрова", LocalDate.parse("17.09.2002", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79500906492"));
            students.add(new Student(53, "Семён", "Тимофеевич", "Ширяев", LocalDate.parse("17.07.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79683229895"));
            students.add(new Student(54, "Николай", "Егорович", "Кириллов", LocalDate.parse("19.01.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79223038618"));
            students.add(new Student(55, "Алиса", "Ильинична", "Александрова", LocalDate.parse("23.02.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79805954271"));
            students.add(new Student(56, "Роман", "Даниилович", "Захаров", LocalDate.parse("28.03.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79662742677"));
            students.add(new Student(57, "Полина", "Ярославовна", "Коновалова", LocalDate.parse("27.02.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79060614084"));
            students.add(new Student(58, "Кирилл", "Константинович", "Андреев", LocalDate.parse("19.03.2010", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79979329211"));
            students.add(new Student(59, "София", "Мироновна", "Климова", LocalDate.parse("29.01.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79661491722"));
            students.add(new Student(60, "Александра", "Петровна", "Березина", LocalDate.parse("22.10.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79103016369"));
            students.add(new Student(61, "Алиса", "Данииловна", "Грачева", LocalDate.parse("15.03.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79836922494"));
            students.add(new Student(62, "Софья", "Всеволодовна", "Чижова", LocalDate.parse("22.04.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79296862044"));
            students.add(new Student(63, "Варвара", "Романовна", "Ситникова", LocalDate.parse("19.09.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79554967582"));
            students.add(new Student(64, "Артур", "Максимович", "Воронин", LocalDate.parse("04.06.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79306010385"));
            students.add(new Student(65, "Вероника", "Андреевна", "Королева", LocalDate.parse("05.05.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79546081762"));
            students.add(new Student(66, "Ульяна", "Борисовна", "Васильева", LocalDate.parse("05.02.2010", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79234300375"));
            students.add(new Student(67, "Максим", "Дмитриевич", "Титов", LocalDate.parse("19.08.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79700328446"));
            students.add(new Student(68, "Семён", "Андреевич", "Борисов", LocalDate.parse("24.02.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79341674669"));
            students.add(new Student(69, "Георгий", "Викторович", "Герасимов", LocalDate.parse("23.07.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79396417315"));
            students.add(new Student(70, "Дана", "Артёмовна", "Никольская", LocalDate.parse("23.12.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79004386904"));
            students.add(new Student(71, "Александра", "Егоровна", "Григорьева", LocalDate.parse("25.07.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79603505917"));
            students.add(new Student(72, "Сергей", "Александрович", "Михайлов", LocalDate.parse("28.06.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79882867246"));
            students.add(new Student(73, "Екатерина", "Семёновна", "Савельева", LocalDate.parse("14.06.2010", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79829747094"));
            students.add(new Student(74, "Макар", "Андреевич", "Николаев", LocalDate.parse("15.01.2010", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79883487191"));
            students.add(new Student(75, "Тимур", "Даниилович", "Успенский", LocalDate.parse("11.04.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79580467608"));
            students.add(new Student(76, "Богдан", "Михайлович", "Архипов", LocalDate.parse("18.11.2002", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79511175000"));
            students.add(new Student(77, "Владислава", "Давидовна", "Щукина", LocalDate.parse("03.06.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79185003591"));
            students.add(new Student(78, "Стефан", "Никитич", "Никитин", LocalDate.parse("24.05.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79508726299"));
            students.add(new Student(79, "Тимофей", "Иванович", "Ильинский", LocalDate.parse("15.07.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79669682987"));
            students.add(new Student(80, "Элина", "Марковна", "Пономарева", LocalDate.parse("17.09.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79562111669"));
            students.add(new Student(81, "Даниил", "Иванович", "Грачев", LocalDate.parse("03.06.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79847698015"));
            students.add(new Student(82, "Лев", "Арсентьевич", "Медведев", LocalDate.parse("04.06.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79287442301"));
            students.add(new Student(83, "Марк", "Александрович", "Богданов", LocalDate.parse("20.07.2009", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79017335696"));
            students.add(new Student(84, "Есения", "Дмитриевна", "Максимова", LocalDate.parse("16.07.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79505320474"));
            students.add(new Student(85, "Арина", "Алексеевна", "Калинина", LocalDate.parse("20.01.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79055634303"));
            students.add(new Student(86, "Даниил", "Алексеевич", "Ильин", LocalDate.parse("02.04.2010", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79376753304"));
            students.add(new Student(87, "Александра", "Васильевна", "Борисова", LocalDate.parse("03.10.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79663950764"));
            students.add(new Student(88, "Леонид", "Тимурович", "Макаров", LocalDate.parse("20.03.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79921513124"));
            students.add(new Student(89, "Арсений", "Григорьевич", "Кулагин", LocalDate.parse("15.01.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79341464586"));
            students.add(new Student(90, "Дмитрий", "Макарович", "Баженов", LocalDate.parse("16.03.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79294852032"));
            students.add(new Student(91, "Андрей", "Артемьевич", "Быков", LocalDate.parse("06.09.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79629690027"));
            students.add(new Student(92, "Фёдор", "Никитич", "Мальцев", LocalDate.parse("11.07.2005", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79616863228"));
            students.add(new Student(93, "Матвей", "Дмитриевич", "Константинов", LocalDate.parse("13.03.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79048361726"));
            students.add(new Student(94, "Василиса", "Кирилловна", "Князева", LocalDate.parse("03.12.2008", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79557871054"));
            students.add(new Student(95, "Егор", "Дмитриевич", "Островский", LocalDate.parse("01.10.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79608040517"));
            students.add(new Student(96, "Лев", "Ильич", "Кравцов", LocalDate.parse("27.03.2006", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79026815257"));
            students.add(new Student(97, "Михаил", "Андреевич", "Лавров", LocalDate.parse("08.05.2003", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79050016019"));
            students.add(new Student(98, "Алия", "Григорьевна", "Волкова", LocalDate.parse("13.09.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79253286514"));
            students.add(new Student(99, "Яна", "Михайловна", "Алексеева", LocalDate.parse("05.05.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79584214030"));
            students.add(new Student(100, "Евгения", "Андреевна", "Новикова", LocalDate.parse("30.04.2007", DateTimeFormatter.ofPattern("dd.MM.yyyy")), "+79970385722"));
        }
    }

    private void initTeachersList(LocalDB db) {
        List<Teacher> teachers = db.getTeachers();
        if (teachers.isEmpty()) {
            teachers.add(new Teacher(1, "Леонова Виктория Артемьевна", 50L, new ArrayList<>(List.of(ALGEBRA, GEOMETRY)), 25));
            teachers.add(new Teacher(2, "Дорохов Константин Денисович", 48L, new ArrayList<>(List.of(DRAWING, MUSIC)), 23));
            teachers.add(new Teacher(3, "Егорова Анастасия Платоновна", 39L, new ArrayList<>(List.of(PHYSICAL_EDUCATION)), 10));
            teachers.add(new Teacher(4, "Крылова Мария Глебовна", 56L, new ArrayList<>(List.of(TECHNOLOGY)), 30));
            teachers.add(new Teacher(5, "Соловьев Никита Матвеевич", 42L, new ArrayList<>(List.of(BIOLOGY, CHEMISTRY)), 18));
            teachers.add(new Teacher(6, "Романова Валерия Всеволодовна", 64L, new ArrayList<>(List.of(GEOGRAPHY)), 34));
            teachers.add(new Teacher(7, "Максимова Алиса Давидовна", 48L, new ArrayList<>(List.of(HISTORY)), 29));
            teachers.add(new Teacher(8, "Орлов Константин Константинович", 38L, new ArrayList<>(List.of(LITERATURE)), 15));
            teachers.add(new Teacher(9, "Григорьев Роман Маркович", 30L, new ArrayList<>(List.of(PHYSICS)), 8));
            teachers.add(new Teacher(10, "Савин Фёдор Максимович", 64L, new ArrayList<>(List.of(COMPUTER_SCIENCE)), 38));
        }
    }

    private void initGroupslist(LocalDB db) {
        List<Group> groups = db.getGroups();
        if (groups.isEmpty()) {
            for (int i = 0; i < 4; i++) {
                List<Student> students = new ArrayList<>();
                for (int j = 0; j < 25; j++) {
                    students.add(db.getStudents().get(i * 25 + j));
                }
                groups.add(new Group(i + 1, "" + (31 + (int) Math.pow(31,i)), students));
            }
        }
    }

    private void initTimetable(LocalDB db) {
        List<Lesson> timetable = db.getTimetable();
        if (timetable.isEmpty()) {
            timetable.add(new Lesson(1, LocalDateTime.parse("2024-07-01 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().getFirst(), db.getTeachers().get(0)));
            timetable.add(new Lesson(2, LocalDateTime.parse("2024-07-01 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().getFirst(), db.getTeachers().get(1)));
            timetable.add(new Lesson(3, LocalDateTime.parse("2024-07-01 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(2)));
            timetable.add(new Lesson(4, LocalDateTime.parse("2024-07-01 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(3)));
            timetable.add(new Lesson(5, LocalDateTime.parse("2024-07-01 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(4)));
            timetable.add(new Lesson(6, LocalDateTime.parse("2024-07-01 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(5)));
            timetable.add(new Lesson(7, LocalDateTime.parse("2024-07-01 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(6)));
            timetable.add(new Lesson(8, LocalDateTime.parse("2024-07-01 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(7)));
            timetable.add(new Lesson(9, LocalDateTime.parse("2024-07-01 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(8)));
            timetable.add(new Lesson(10, LocalDateTime.parse("2024-07-01 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(9)));
            timetable.add(new Lesson(11, LocalDateTime.parse("2024-07-01 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(0)));
            timetable.add(new Lesson(12, LocalDateTime.parse("2024-07-01 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(1)));
            timetable.add(new Lesson(13, LocalDateTime.parse("2024-07-01 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(2)));
            timetable.add(new Lesson(14, LocalDateTime.parse("2024-07-01 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(3)));
            timetable.add(new Lesson(15, LocalDateTime.parse("2024-07-01 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(4)));
            timetable.add(new Lesson(16, LocalDateTime.parse("2024-07-01 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-01 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(5)));
            timetable.add(new Lesson(17, LocalDateTime.parse("2024-07-02 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(6)));
            timetable.add(new Lesson(18, LocalDateTime.parse("2024-07-02 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(7)));
            timetable.add(new Lesson(19, LocalDateTime.parse("2024-07-02 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(8)));
            timetable.add(new Lesson(20, LocalDateTime.parse("2024-07-02 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(9)));
            timetable.add(new Lesson(21, LocalDateTime.parse("2024-07-02 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(0)));
            timetable.add(new Lesson(22, LocalDateTime.parse("2024-07-02 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(1)));
            timetable.add(new Lesson(23, LocalDateTime.parse("2024-07-02 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(2)));
            timetable.add(new Lesson(24, LocalDateTime.parse("2024-07-02 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(3)));
            timetable.add(new Lesson(25, LocalDateTime.parse("2024-07-02 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(4)));
            timetable.add(new Lesson(26, LocalDateTime.parse("2024-07-02 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(5)));
            timetable.add(new Lesson(27, LocalDateTime.parse("2024-07-02 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(6)));
            timetable.add(new Lesson(28, LocalDateTime.parse("2024-07-02 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(7)));
            timetable.add(new Lesson(29, LocalDateTime.parse("2024-07-02 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(8)));
            timetable.add(new Lesson(30, LocalDateTime.parse("2024-07-02 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(9)));
            timetable.add(new Lesson(31, LocalDateTime.parse("2024-07-02 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(0)));
            timetable.add(new Lesson(32, LocalDateTime.parse("2024-07-02 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-02 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(1)));
            timetable.add(new Lesson(33, LocalDateTime.parse("2024-07-03 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(2)));
            timetable.add(new Lesson(34, LocalDateTime.parse("2024-07-03 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(3)));
            timetable.add(new Lesson(35, LocalDateTime.parse("2024-07-03 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(4)));
            timetable.add(new Lesson(36, LocalDateTime.parse("2024-07-03 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(0), db.getTeachers().get(5)));
            timetable.add(new Lesson(37, LocalDateTime.parse("2024-07-03 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(6)));
            timetable.add(new Lesson(38, LocalDateTime.parse("2024-07-03 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(7)));
            timetable.add(new Lesson(39, LocalDateTime.parse("2024-07-03 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(8)));
            timetable.add(new Lesson(40, LocalDateTime.parse("2024-07-03 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(1), db.getTeachers().get(9)));
            timetable.add(new Lesson(41, LocalDateTime.parse("2024-07-03 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(0)));
            timetable.add(new Lesson(42, LocalDateTime.parse("2024-07-03 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(1)));
            timetable.add(new Lesson(43, LocalDateTime.parse("2024-07-03 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(2)));
            timetable.add(new Lesson(44, LocalDateTime.parse("2024-07-03 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(2), db.getTeachers().get(3)));
            timetable.add(new Lesson(45, LocalDateTime.parse("2024-07-03 09:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 10:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(4)));
            timetable.add(new Lesson(46, LocalDateTime.parse("2024-07-03 10:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 12:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(5)));
            timetable.add(new Lesson(47, LocalDateTime.parse("2024-07-03 13:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(6)));
            timetable.add(new Lesson(48, LocalDateTime.parse("2024-07-03 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.parse("2024-07-03 16:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), db.getGroups().get(3), db.getTeachers().get(7)));
        }
    }
}