package com.penguin.penguincoco.dao.domain.judge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistoryCode implements Serializable {

    private String handDate;
    private String code;
    private double runTime;
    private List<String> output;
    private List<String> symbol;
    private List<String> errorMessage;
    private double score;

}
