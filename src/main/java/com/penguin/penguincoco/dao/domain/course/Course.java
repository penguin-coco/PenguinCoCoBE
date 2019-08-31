package com.penguin.penguincoco.dao.domain.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.penguin.penguincoco.dao.domain.assistant.Assistant;
import com.penguin.penguincoco.dao.domain.base.BaseEntity;
import com.penguin.penguincoco.dao.domain.feedback.Feedback;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.student.Student;
import com.penguin.penguincoco.dao.domain.teacher.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Course extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    private String name;
    private String semester;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Problem> problems;
    @ManyToMany(mappedBy = "courses")
    @JsonIgnoreProperties("courses")
    private List<Student> students;
    @ManyToMany(mappedBy = "courses")
    @JsonIgnoreProperties("courses")
    private List<Assistant> assistants;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;

    public Course(Teacher teacher, String name, String semester, List<Problem> problems, List<Student> students, List<Assistant> assistants, List<Feedback> feedbacks) {
        this.teacher = teacher;
        this.name = name;
        this.semester = semester;
        this.problems = problems;
        this.students = students;
        this.assistants = assistants;
        this.feedbacks = feedbacks;
    }

}
