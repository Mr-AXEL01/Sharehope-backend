package net.axel.sharehope.security.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.axel.sharehope.domain.entities.Action;
import net.axel.sharehope.domain.entities.Article;
import net.axel.sharehope.security.domain.dto.user.requests.UserUpdateDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

import static net.axel.sharehope.util.UpdateUtils.updateField;

@Entity
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppUser implements UserDetails {
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Action> actions = new ArrayList<>();

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

    public void updateUser(UserUpdateDTO updateDTO) {
        updateField(updateDTO.username(), this.username, newValue -> this.username = newValue);
        updateField(updateDTO.email(), this.email, newValue -> this.email = newValue);
        updateField(updateDTO.phone(), this.phone, newValue -> this.phone = newValue);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) role::getRole)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
