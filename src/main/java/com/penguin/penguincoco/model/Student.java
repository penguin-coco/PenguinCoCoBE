package com.penguin.penguincoco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.penguin.penguincoco.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {

    private String name;
    private String studentClass;
    @JsonIgnore
    @OneToOne(mappedBy = "student")
    private Person person;
}
