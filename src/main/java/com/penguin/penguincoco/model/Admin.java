package com.penguin.penguincoco.model;

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
public class Admin extends BaseEntity {

    private String name;
    @OneToOne(mappedBy = "admin")
    private Person person;
}
