package com.penguin.penguincoco.dao.domain.assistant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.penguin.penguincoco.dao.domain.base.AbstractUser;
import com.penguin.penguincoco.dao.domain.course.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Assistant extends AbstractUser implements UserDetails {

    @ManyToMany
    @JoinTable(name = "assistant_course", joinColumns = @JoinColumn(name = "assistant_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @JsonIgnoreProperties("assistants")
    private List<Course> courses;

    public Assistant(String account, String password, String name, List<Course> courses) {
        super(account, password, name);
        this.courses = courses;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_assistant"));
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
