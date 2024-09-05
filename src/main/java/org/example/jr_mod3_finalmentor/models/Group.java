package org.example.jr_mod3_finalmentor.models;

import lombok.*;
import org.apache.commons.lang3.builder.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group implements UtilsExecutable {
    private int id;
    private String groupNumber;
    private List<Student> students;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("groupNumber", groupNumber)
                .append("students", students)
                .toString();
    }
}
