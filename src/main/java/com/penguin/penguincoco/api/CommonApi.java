package com.penguin.penguincoco.api;

import com.penguin.penguincoco.FakeService;
import com.penguin.penguincoco.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/common")
@RestController
public class CommonApi {

    @Autowired
    FakeService fakeService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> body) throws MyException {
//        fakeService.fake();
        Map<String, String> map = new HashMap<String, String>() {{
            put("name", "Kenny");
        }};
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
