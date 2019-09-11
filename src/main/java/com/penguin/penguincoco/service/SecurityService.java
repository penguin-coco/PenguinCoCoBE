package com.penguin.penguincoco.service;

import com.penguin.penguincoco.model.admin.Admin;
import com.penguin.penguincoco.model.assistant.Assistant;
import com.penguin.penguincoco.model.student.Student;
import com.penguin.penguincoco.model.teacher.Teacher;
import com.penguin.penguincoco.repository.AdminRepository;
import com.penguin.penguincoco.repository.AssistantRepository;
import com.penguin.penguincoco.repository.StudentRepository;
import com.penguin.penguincoco.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityService implements UserDetailsService {

    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private AssistantRepository assistantRepository;
    private AdminRepository adminRepository;

    @Autowired
    public SecurityService(StudentRepository studentRepository,
                           TeacherRepository teacherRepository,
                           AssistantRepository assistantRepository,
                           AdminRepository adminRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.assistantRepository = assistantRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Optional<Student> studentOptional = studentRepository.findByAccount(account);
        Optional<Teacher> teacherOptional = teacherRepository.findByAccount(account);
        Optional<Assistant> assistantOptional = assistantRepository.findByAccount(account);
        Optional<Admin> adminOptional = adminRepository.findByAccount(account);

        if (studentOptional.isPresent()) {
            return studentOptional.get();
        }
        else if (teacherOptional.isPresent()) {
            return teacherOptional.get();
        }
        else if (assistantOptional.isPresent()) {
            return assistantOptional.get();
        }
        else if (adminOptional.isPresent()) {
            return adminOptional.get();
        }
        else {
            throw new UsernameNotFoundException("account not found");
        }
    }
}
