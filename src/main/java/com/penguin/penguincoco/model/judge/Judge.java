package com.penguin.penguincoco.model.judge;

import com.penguin.penguincoco.model.base.BaseEntity;
import com.penguin.penguincoco.model.problem.Problem;
import com.penguin.penguincoco.model.student.Student;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;


@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)})
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Judge extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "problem_id")
    private Problem problem;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "student_id")
    private Student student;
    private double rate;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<HistoryCode> historyCodes;

}
