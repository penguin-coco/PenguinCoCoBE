package com.penguin.penguincoco.common.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Message {

    private String status;
    private String msg;
    private Object result;

    public Message(ApiMessageCode apiMessageCode, Object result) {
        this.status = apiMessageCode.getCode();
        this.msg = apiMessageCode.getDesc();
        this.result = result;
    }
}
