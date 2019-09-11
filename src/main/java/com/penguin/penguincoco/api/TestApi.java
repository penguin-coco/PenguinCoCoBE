package com.penguin.penguincoco.api;

import com.penguin.penguincoco.model.student.Student;
import com.penguin.penguincoco.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestApi {

    private final String LOGGED_IN = "logged_in";
    private final String USER_TYPE = "user_type";

    @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/saveStudent")
    public Student saveStudent(@RequestBody Map<String, String> body) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String account = body.get("account");
        String password = body.get("password");
        password = passwordEncoder.encode(password);
        String name = body.get("name");
        String studentClass = body.get("studentClass");

        Student student = new Student(account, password, name, studentClass);
        return studentRepository.save(student);
    }

    @GetMapping("/checkLogin")
    public Map checkLogin(HttpSession session) {
        if (session.getAttribute(LOGGED_IN) != null) {
            String account = session.getAttribute(LOGGED_IN).toString();
            System.out.println(account);
            Map<String, ? extends Session> usersSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, account);
            return usersSessions;
        }
        else {
            return null;
        }
    }
}
