package com.banking.IgorVannelSibemou_corebanking.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private Long id;
    private String username;
    private String password;  // Utilisé uniquement pour la création/modification
    private String fullName;
    private boolean active = true;
    private Set<String> roles = new HashSet<>();

    // Constructeur sans arguments
    public UserDto() {}

    // Constructeur avec tous les arguments
    public UserDto(Long id, String username, String password, String fullName, boolean active, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.active = active;
        this.roles = roles;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    // Méthode toString (optionnelle)
    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }

    // Méthode equals (optionnelle)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (active != userDto.active) return false;
        if (!id.equals(userDto.id)) return false;
        if (!username.equals(userDto.username)) return false;
        if (!fullName.equals(userDto.fullName)) return false;
        return roles.equals(userDto.roles);
    }

    // Méthode hashCode (optionnelle)
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + roles.hashCode();
        return result;
    }
}
