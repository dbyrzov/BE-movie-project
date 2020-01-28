package com.movieproject.bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Role {
    public enum role {
        USER,
        ADMIN,
        NONE
    }


}
