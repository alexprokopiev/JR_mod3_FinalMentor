package org.example.jr_mod3_finalmentor.models;

import lombok.*;

import java.util.*;

@Getter
@Setter
public class LocalDB {
    private Properties props = new Properties();
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Lesson> timetable = new ArrayList<>();
}