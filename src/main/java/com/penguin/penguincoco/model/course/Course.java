package com.penguin.penguincoco.model.course;

import com.penguin.penguincoco.model.feedback.Feedback;
import com.penguin.penguincoco.model.student.Student;
import com.penguin.penguincoco.model.teacher.Teacher;
import com.penguin.penguincoco.model.assistant.Assistant;
import com.penguin.penguincoco.model.base.BaseEntity;
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
public class Course extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    private String name;
    private String semester;
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
    @ManyToMany(mappedBy = "courses")
    private List<Assistant> assistants;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;
}
