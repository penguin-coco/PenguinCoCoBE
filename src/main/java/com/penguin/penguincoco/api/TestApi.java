package com.penguin.penguincoco.api;

import com.penguin.penguincoco.model.Person;
import com.penguin.penguincoco.model.Student;
import com.penguin.penguincoco.repository.PersonRepository;
import com.penguin.penguincoco.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class TestApi {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/allPerson")
    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    @GetMapping("/allStudent")
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @GetMapping("/savePerson")
    public Person savePerson() {
        Student student = studentRepository.findById(1L).get();
        Person person = new Person("04156211", "0000", student, null, null, null);
        return personRepository.save(person);
    }
}
