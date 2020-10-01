package com.example.RestTest.config;

import com.example.RestTest.domain.User;
import com.example.RestTest.repository.UserDataRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/error", "/js/**","/login**","/error").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().disable();
    }
    @Bean
    public PrincipalExtractor principalExtractor(UserDataRepository userDataRepository){

        return map -> {
            String id = (String)map.get("sub");
            User u =userDataRepository.findById(id).orElseGet(()->{
                User newUser=new User();
                newUser.setId(id);
                newUser.setUsername((String) map.get("name"));
                newUser.setEmail((String) map.get("email"));
                newUser.setLocation((String) map.get("locale"));
                newUser.setUserGender((String) map.get("gender"));
                newUser.setUserData((String)map.get("picture"));
                return newUser;
            });
            u.setLastSeenActivity(LocalDateTime.now());
            userDataRepository.save(u);
            return new User();
        };
    }
}
