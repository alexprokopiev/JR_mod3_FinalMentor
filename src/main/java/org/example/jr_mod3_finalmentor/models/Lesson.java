package org.example.jr_mod3_finalmentor.models;

import lombok.*;
import org.apache.commons.lang3.builder.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson implements UtilsExecutable {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Group group;
    private Teacher teacher;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .append("group", group)
                .append("teacher", teacher)
                .toString();
    }
}
