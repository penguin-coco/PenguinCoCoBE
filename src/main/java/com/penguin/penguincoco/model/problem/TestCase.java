package com.penguin.penguincoco.model.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestCase implements Serializable {

    private String inputSample;
    private String outputSample;

}
