package com.penguin.penguincoco.model.student;

import com.penguin.penguincoco.model.copy.Copy;
import com.penguin.penguincoco.model.feedback.Feedback;
import com.penguin.penguincoco.model.judge.Judge;
import com.penguin.penguincoco.model.problem.Problem;
import com.penguin.penguincoco.model.base.AbstractUser;
import com.penguin.penguincoco.model.course.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Student extends AbstractUser implements UserDetails {

    private String studentClass;
    @ManyToMany
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;
    @OneToMany(mappedBy = "bestStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Problem> bestProblems;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Judge> judges;
    @OneToMany(mappedBy = "referencedStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Copy> referencedCopies;
    @OneToMany(mappedBy = "referenceStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Copy> referenceCopies;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;

    public Student(String account, String password,
                   String name, String studentClass) {
        super(account, password, name);
        this.studentClass = studentClass;
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
