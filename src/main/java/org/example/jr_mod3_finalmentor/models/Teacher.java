package org.example.jr_mod3_finalmentor.models;

import lombok.*;
import org.apache.commons.lang3.builder.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher implements UtilsExecutable {
    private int id;
    private String fullName;
    private Long age;
    private List<Subject> subjects;
    private int experience;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("fullName", fullName)
                .append("age", age)
                .append("subjects", subjects)
                .append("experience", experience)
                .toString();
    }
}
