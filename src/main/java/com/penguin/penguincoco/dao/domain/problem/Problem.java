package com.penguin.penguincoco.dao.domain.problem;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.penguin.penguincoco.dao.domain.base.BaseEntity;
import com.penguin.penguincoco.dao.domain.copy.Copy;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.judge.Judge;
import com.penguin.penguincoco.dao.domain.team.Team;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@TypeDefs({
@TypeDef(name = "string-array", typeClass = StringArrayType.class),
@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Problem extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "course_id")
    private Course course;
    private String name;
    private String type;
    private String category;
    @Type(type = "string-array")
    @Column(name = "tag", columnDefinition = "text[]")
    private String[] tag;
    private double rate;
    private String description;
    private String inputDesc;
    private String outputDesc;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<TestCase> testCases;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    private int correctNum;
    private int incorrectNum;
    private double correctRate;
    private String bestStudentAccount;
    @Type(type = "string-array")
    @Column(name = "pattern", columnDefinition = "text[]")
    private String[] pattern;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Copy> copies;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Judge> judges;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;

    public Problem(Course course, String name, String type, String category, String[] tag, double rate, String description, String inputDesc, String outputDesc, List<TestCase> testCases, Date deadline, int correctNum, int incorrectNum, double correctRate, String bestStudentAccount, String[] pattern, List<Copy> copies, List<Judge> judges, List<Team> teams) {
        this.course = course;
        this.name = name;
        this.type = type;
        this.category = category;
        this.tag = tag;
        this.rate = rate;
        this.description = description;
        this.inputDesc = inputDesc;
        this.outputDesc = outputDesc;
        this.testCases = testCases;
        this.deadline = deadline;
        this.correctNum = correctNum;
        this.incorrectNum = incorrectNum;
        this.correctRate = correctRate;
        this.bestStudentAccount = bestStudentAccount;
        this.copies = copies;
        this.judges = judges;
        this.pattern = pattern;
        this.teams = teams;
    }

}
