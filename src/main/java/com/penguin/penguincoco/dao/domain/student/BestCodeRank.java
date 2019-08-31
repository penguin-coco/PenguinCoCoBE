package com.penguin.penguincoco.dao.domain.student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BestCodeRank {

    private String account;
    private String name;
    private String bestCodeNum;
    private String rank;
}
