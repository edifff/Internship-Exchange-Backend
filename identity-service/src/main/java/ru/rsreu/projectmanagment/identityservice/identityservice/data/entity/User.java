package ru.rsreu.projectmanagment.identityservice.identityservice.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USER", indexes = @Index(columnList = "email", unique = true))
@SQLRestriction("deleted_at IS NULL")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="email", nullable = false, length = 50)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    @JsonIgnore
    private String passwordHash;

    @Column(nullable = false)
    private boolean isActive=true;

    private Instant lastLoginAt;

    @CreatedDate
    private Instant createdAt;

    private Instant deletedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles=new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notifications;

    public List<? extends GrantedAuthority> getAuthorities(){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public @Nullable String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return deletedAt==null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isActive && deletedAt==null;
    }


    public boolean addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if (roles.contains(role)) {
            return false;
        }
        return roles.add(role);
    }
    public boolean deleteRole(Role role){
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        if (roles.contains(role)) {
            return false;
        }
        return roles.remove(role);
    }

}
