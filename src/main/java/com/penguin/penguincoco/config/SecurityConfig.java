package com.penguin.penguincoco.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.service.impl.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SecurityService securityService;

    @Autowired
    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new AuthenticationAccessDeniedHandler();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/api/checkLogin")
                .permitAll()
                .antMatchers("/api/student/**")
                .access("hasAnyRole('student', 'teacher', 'assistant', 'admin')")
                .antMatchers("/api/problem/getInfo")
                .access("hasAnyRole('assistant', 'teacher', 'admin', 'student')")
                .antMatchers("/api/problem/getProblems")
                .access("hasAnyRole('assistant', 'teacher', 'admin')")
                .antMatchers("/api/problem/addProblem")
                .access("hasAnyRole('assistant', 'teacher', 'admin')")
                .antMatchers("/api/problem/editProblem")
                .access("hasAnyRole('assistant', 'teacher', 'admin')")
                .antMatchers("/api/problem/deleteProblem")
                .access("hasAnyRole('assistant', 'teacher', 'admin')")
                .antMatchers("/api/judge/judgeCode").hasRole("student")
                .antMatchers("/api/judge/judgedInfo")
                .access("hasAnyRole('admin', 'assistant', 'teacher', 'student')")
                .antMatchers("/api/judge/checkJudged")
                .access("hasAnyRole('admin', 'assistant', 'teacher', 'student')")
                .antMatchers("/api/judge/judgeCopy")
                .access("hasAnyRole('admin', 'assistant', 'teacher')")
                .antMatchers("/api/teacher/**").hasRole("teacher")
                .antMatchers("/api/assistant/**").hasRole("assistant")
                .antMatchers("/api/course/getCourses")
                .access("hasAnyRole('admin', 'assistant', 'teacher')")
                .antMatchers("/api/course/getStudentsData")
                .access("hasAnyRole('admin', 'assistant', 'teacher')")
                .antMatchers("/api/course/delCourse")
                .access("hasAnyRole('admin', 'teacher')")
                .antMatchers("/api/course/editCourse")
                .access("hasAnyRole('admin', 'teacher')")
                .antMatchers("/api/rank/**").hasRole("student")
                .antMatchers("/api/feedback/addFeedback").hasRole("student")
                .antMatchers("/api/feedback/getCourseFeedbacks")
                .access("hasAnyRole('admin', 'assistant', 'teacher')")
                .antMatchers("/api/admin/**").hasRole("admin")
                .antMatchers("/api/problemBank/**")
                .access("hasAnyRole('admin', 'assistant', 'teacher')")
                .antMatchers("/api/team/createTeam")
                .access("hasAnyRole('admin', 'assistant', 'teacher')")
                .antMatchers("/api/team/correctStuds").hasRole("student")
                .antMatchers("/api/team/checkCorrectStatus").hasRole("student")
                .antMatchers("/api/team/correctInfo").hasRole("student")
                .antMatchers("/api/team/checkCorrectedStatus").hasRole("student")
                .antMatchers("/api/team/correctedInfo").hasRole("student")
                .antMatchers("/team/submitCorrect").hasRole("student")
                .antMatchers("/team/discussScore")
                .access("hasAnyRole('admin', 'assistant', 'teacher')")
                .antMatchers("/team/teacher/correctInfo")
                .access("hasAnyRole('teacher')")
                .antMatchers("/team/teacher/submitCorrect")
                .access("hasAnyRole('teacher')")
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .invalidateHttpSession(true)
                .logoutSuccessHandler((req, resp, auth) -> {
                    resp.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = resp.getWriter();
                    resp.setStatus(200);
                    Message message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
                    ObjectMapper om = new ObjectMapper();
                    out.write(om.writeValueAsString(message));
                    out.flush();
                    out.close();
                })
                .and()
                .csrf()
                .disable();
        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());
        filter.setAuthenticationFailureHandler((req, resp, e) -> {
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            resp.setStatus(404);
            Message message = new Message(ApiMessageCode.LOGIN_ERROR, "");
            ObjectMapper om = new ObjectMapper();
            out.write(om.writeValueAsString(message));
            out.flush();
            out.close();
        });
        filter.setFilterProcessesUrl("/api/login");
        return filter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
