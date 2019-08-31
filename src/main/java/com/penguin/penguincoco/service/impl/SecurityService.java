package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.dao.domain.admin.Admin;
import com.penguin.penguincoco.dao.domain.assistant.Assistant;
import com.penguin.penguincoco.dao.domain.student.Student;
import com.penguin.penguincoco.dao.domain.teacher.Teacher;
import com.penguin.penguincoco.dao.repository.AdminRepository;
import com.penguin.penguincoco.dao.repository.AssistantRepository;
import com.penguin.penguincoco.dao.repository.StudentRepository;
import com.penguin.penguincoco.dao.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        boolean existStudent = studentRepository.existsByAccount(account);
        boolean existTeacher = teacherRepository.existsByAccount(account);
        boolean existAssistant = assistantRepository.existsByAccount(account);
        boolean existAdmin = adminRepository.existsByAccount(account);
        if (existStudent) {
            Student student = studentRepository.findByAccount(account).get();
            return student;
        }
        else if (existTeacher) {
            Teacher teacher = teacherRepository.findByAccount(account).get();
            return teacher;
        }
        else if (existAssistant) {
            Assistant assistant = assistantRepository.findByAccount(account).get();
            return assistant;
        }
        else if (existAdmin) {
            Admin admin = adminRepository.findByAccount(account).get();
            return admin;
        }
        else {
            throw new UsernameNotFoundException("帳戶不存在!");
        }
    }
}
