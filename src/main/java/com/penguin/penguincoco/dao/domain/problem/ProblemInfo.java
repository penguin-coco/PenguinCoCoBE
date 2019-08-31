package com.penguin.penguincoco.dao.domain.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProblemInfo {

    private String name;
    private String type;
    private String category;
    private String[] tag;
    private double rate;
    private String description;
    private String inputDesc;
    private String outputDesc;
    private List<TestCase> testCases;
    private Date deadline;
    private int correctNum;
    private int incorrectNum;
    private double correctRate;
    private String bestStudentAccount;
    private String[] pattern;
}
