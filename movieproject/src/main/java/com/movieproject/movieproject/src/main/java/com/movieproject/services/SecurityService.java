package com.movieproject.services;

import com.movieproject.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SecurityService extends WebSecurityConfigurerAdapter {

    public static List<User> users;

    public SecurityService() {
        users = new ArrayList<>();
    }


    public boolean hasAccess(String token) {
        System.out.println("Nema token bratoto");
        for (User u : users) {
            if (u.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }

    public Integer getUserId(String token) {
        for (User u: users) {
            if (u.getToken().equals(token)) {
                return u.getId();
            }
        }
        return -1;
    }


    public boolean removeToken(String token) {
        for (User u : users) {
            if (u.getToken().equals(token)) {
                users.remove(u);
                return true;
            }
        }
        return false;
    }

    public String generateToken() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}
		};
	}
}
