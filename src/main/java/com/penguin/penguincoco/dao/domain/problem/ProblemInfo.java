package com.penguin.penguincoco.dao.domain.problem;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date deadline;
    private int correctNum;
    private int incorrectNum;
    private double correctRate;
    private String bestStudentAccount;
    private String[] pattern;
}
