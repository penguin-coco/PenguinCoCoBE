package com.penguin.penguincoco.model.feedback;

import com.penguin.penguincoco.model.base.BaseEntity;
import com.penguin.penguincoco.model.course.Course;
import com.penguin.penguincoco.model.student.Student;
import lombok.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "student_id")
    private Student student;
    private Date date;
    private String content;

}
