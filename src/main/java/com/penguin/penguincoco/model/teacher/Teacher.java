package com.penguin.penguincoco.model.teacher;

import com.penguin.penguincoco.model.base.AbstractUser;
import com.penguin.penguincoco.model.course.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Teacher extends AbstractUser {

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses;

    public Teacher(String account, String password, String name, List<Course> courses) {
        super(account, password, name);
        this.courses = courses;
    }
}
