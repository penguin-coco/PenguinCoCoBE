package com.penguin.penguincoco.dao.domain.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.penguin.penguincoco.dao.domain.base.AbstractUser;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.feedback.Feedback;
import com.penguin.penguincoco.dao.domain.judge.Judge;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Student extends AbstractUser implements UserDetails {

    private String studentClass;
    @ManyToMany
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @JsonIgnoreProperties("students")
    private List<Course> courses;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Judge> judges;

    public Student(String account, String password, String name, String studentClass, List<Course> courses, List<Feedback> feedbacks, List<Judge> judges) {
        super(account, password, name);
        this.studentClass = studentClass;
        this.courses = courses;
        this.feedbacks = feedbacks;
        this.judges = judges;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_student"));
        return authorities;
    }

    @Override
    public String getUsername() {
        return super.getAccount();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
