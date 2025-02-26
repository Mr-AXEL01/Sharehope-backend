package net.axel.sharehope;

import net.axel.sharehope.exception.domains.ResourceNotFoundException;
import net.axel.sharehope.security.domain.entity.AppRole;
import net.axel.sharehope.security.domain.entity.AppUser;
import net.axel.sharehope.security.repository.AppRoleRepository;
import net.axel.sharehope.security.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SharehopeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharehopeApplication.class, args);
    }

    @Bean
    CommandLineRunner init(AppUserRepository userRepository, AppRoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                AppRole roleUser = new AppRole();
                roleUser.setRole("ROLE_USER");
                AppRole roleAdmin = new AppRole();
                roleAdmin.setRole("ROLE_ADMIN");
                roleRepository.save(roleUser);
                roleRepository.save(roleAdmin);
            }

            if (userRepository.count() == 0) {
                AppUser adminUser = new AppUser();
                adminUser.setUsername("admin")
                        .setEmail("admin@wora.com")
                        .setPassword("admin123")
                        .setPhone("0639330455");

                Set<AppRole> roles = new HashSet<>();
                roles.add(roleRepository.findByRole("ROLE_ADMIN")
                        .orElseThrow(()-> new ResourceNotFoundException("Role admin not exists."))
                );

                roles.add(roleRepository.findByRole("ROLE_USER")
                        .orElseThrow(()-> new ResourceNotFoundException("Role User not exists."))
                );

                adminUser.setRoles(roles);

                userRepository.save(adminUser);
            }
        };
    }

}
