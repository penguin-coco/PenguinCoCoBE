package com.penguin.penguincoco;

import org.springframework.stereotype.Service;

@Service
public class FakeService {

    public String fake() throws MyException {
        throw new MyException();
    }
}
