package com.penguin.penguincoco.model.student;

import com.penguin.penguincoco.model.copy.Copy;
import com.penguin.penguincoco.model.feedback.Feedback;
import com.penguin.penguincoco.model.judge.Judge;
import com.penguin.penguincoco.model.problem.Problem;
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
public class Student extends AbstractUser {

    private String studentClass;
    @ManyToMany
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;
    @OneToMany(mappedBy = "bestStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Problem> bestProblems;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Judge> judges;
    @OneToMany(mappedBy = "referencedStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Copy> referencedCopies;
    @OneToMany(mappedBy = "referenceStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Copy> referenceCopies;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;

    public Student(String account, String password,
                   String name, String studentClass,
                   List<Course> courses, List<Judge> judges,
                   List<Problem> bestProblems, List<Copy> referencedCopies,
                   List<Copy> referenceCopies, List<Feedback> feedbacks) {
        super(account, password, name);
        this.studentClass = studentClass;
        this.courses = courses;
        this.bestProblems = bestProblems;
        this.judges = judges;
        this.referencedCopies = referencedCopies;
        this.referenceCopies = referenceCopies;
        this.feedbacks = feedbacks;
    }

}
