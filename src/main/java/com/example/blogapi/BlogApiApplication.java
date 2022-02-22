package com.example.blogapi;

import com.example.blogapi.model.Role;
import com.example.blogapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@AllArgsConstructor
@SpringBootApplication
@EnableWebMvc
public class BlogApiApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        setRoleInDB("ROLE_ADMIN");
        setRoleInDB("ROLE_USER");
    }

    private void setRoleInDB(String role_user) {
        Role userRole = new Role();
        userRole.setName(role_user);
        roleRepository.save(userRole);
    }
}
