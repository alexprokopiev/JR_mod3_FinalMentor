package org.example.jr_mod3_finalmentor.models;

import lombok.*;
import org.apache.commons.lang3.builder.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student implements UtilsExecutable {
    private int id;
    private String name;
    private String patronymic;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("name", name)
                .append("patronymic", patronymic)
                .append("lastName", lastName)
                .append("birthDate", birthDate)
                .append("phoneNumber", phoneNumber)
                .toString();
    }
}
