package by.mishastoma.authservice.model;

import by.mishastoma.authservice.config.RoleConfig;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity(name = "companies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company implements CustomUserDetails {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String companyName;
    private String description;
    private String username;
    private String password;
    private Boolean isBlocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(RoleConfig.getCOMPANY_ROLE()));
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public List<String> getUserAuthoritiesAsStringList() {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority authority : getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(username, company.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
