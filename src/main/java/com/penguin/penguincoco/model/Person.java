package com.penguin.penguincoco.model;

import com.penguin.penguincoco.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity {

    private String account;
    private String password;
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @OneToOne
    @JoinColumn(name = "assistant_id")
    private Assistant assistant;
    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
