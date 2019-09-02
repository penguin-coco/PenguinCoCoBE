package com.penguin.penguincoco.model.assistant;

import com.penguin.penguincoco.model.course.Course;
import com.penguin.penguincoco.model.base.AbstractUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assistant extends AbstractUser {

    @ManyToMany
    @JoinTable(name = "assistant_course", joinColumns = @JoinColumn(name = "assistant_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    public Assistant(String account, String password, String name, List<Course> courses) {
        super(account, password, name);
        this.courses = courses;
    }
}
