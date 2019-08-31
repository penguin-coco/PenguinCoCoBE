package com.penguin.penguincoco.dao.domain.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentResult implements Serializable {

    private String account;
    private CorrectValue correctValue;
    private ReadValue readValue;
    private SkillValue skillValue;
    private CompleteValue completeValue;
    private WholeValue wholeValue;
    private String comment;
}
