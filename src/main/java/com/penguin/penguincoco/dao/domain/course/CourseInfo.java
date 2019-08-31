package com.penguin.penguincoco.dao.domain.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CourseInfo {

    private Long id;
    private String name;
    private String semester;
}
