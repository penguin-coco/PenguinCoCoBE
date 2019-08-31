package com.penguin.penguincoco.dao.domain.copy;

import com.penguin.penguincoco.dao.domain.base.BaseEntity;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Copy extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "problem_id")
    private Problem problem;
    private String studentOneAccount;
    private String studentTwoAccount;
    private double similarity;

    public Copy(Problem problem, String studentOneAccount, String studentTwoAccount, double similarity) {
        this.problem = problem;
        this.studentOneAccount = studentOneAccount;
        this.studentTwoAccount = studentTwoAccount;
        this.similarity = similarity;
    }
}
