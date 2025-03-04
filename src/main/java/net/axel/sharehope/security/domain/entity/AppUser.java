package net.axel.sharehope.security.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.axel.sharehope.domain.entities.Article;
import net.axel.sharehope.domain.entities.Attachment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "app_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<AppRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    @Transient
    private String avatar;

    public static AppUser register(String username, String email, String password, String phone, Set<AppRole> roles) {
        AppUser newUser  = new AppUser();
        newUser.username = username;
        newUser.email    = email;
        newUser.password = password;
        newUser.phone    = phone;
        newUser.roles    = roles;
        return newUser;
    }
}
