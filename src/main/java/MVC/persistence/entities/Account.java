package MVC.persistence.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "accounts")

public class Account implements Serializable {
    Account() {
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "password", length = 255, nullable = false)
    @NotNull
    private String password;


    @Column(name = "username", length = 30, unique = true, nullable = false)
    @NotNull
    private String username;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<UserRole> userRole = new HashSet<UserRole>(1);

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Account(String name, String password) {
        this.username = name;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


}