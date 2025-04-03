package com.banking.IgorVannelSibemou_corebanking.dto.response;


import java.util.HashSet;
import java.util.Set;

public class AuthResponseDto {

    private String username;
    private String fullName;
    private Set<String> roles = new HashSet<>();

    // Constructeur sans arguments
    public AuthResponseDto() {}

    // Constructeur avec tous les arguments
    public AuthResponseDto(String username, String fullName, Set<String> roles) {
        this.username = username;
        this.fullName = fullName;
        this.roles = roles;
    }

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
        return "AuthResponseDto{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", roles=" + roles +
                '}';
    }

    // Méthode equals (optionnelle)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthResponseDto that = (AuthResponseDto) o;

        if (!username.equals(that.username)) return false;
        if (!fullName.equals(that.fullName)) return false;
        return roles.equals(that.roles);
    }

    // Méthode hashCode (optionnelle)
    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + roles.hashCode();
        return result;
    }
}
