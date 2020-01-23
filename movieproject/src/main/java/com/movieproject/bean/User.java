package com.movieproject.bean;

import org.springframework.context.annotation.Bean;


public class User {

    private Integer id;
    private String role;
    private String token;

    @Bean
    public Integer getId() {
        return id;
    }

    @Bean
    public void setId(Integer id) {
        this.id = id;
    }

    @Bean
    public String getRole() {
        return role;
    }

    @Bean
    public void setRole(String role) {
        this.role = role;
    }

    @Bean
    public String getToken() {
        return token;
    }

    @Bean
    public void setToken(String token) {
        this.token = token;
    }
}
