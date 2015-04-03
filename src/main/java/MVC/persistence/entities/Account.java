package MVC.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "account")

public class Account implements Serializable {
    Account() {}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   //@JsonIgnore
    @Column(name = "password", unique = true, length = 30, nullable = false)
    @NotNull
    private String password;

    @Column(name = "username", length = 30, unique = true, nullable = false)
    @NotNull
    @Size(max = 30)
    private String username;

    private String grantedAuthority;
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    public String getGrantedAuthority() {
		return grantedAuthority;
	}
	public void setGrantedAuthority(String grantedAuthority) {
		this.grantedAuthority = grantedAuthority;
	}

	public Account(String name, String password) {
        this.username = name;
        this.password = password;
   }
    public Long getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }



}