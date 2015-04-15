package MVC.persistence.entities;


import javax.persistence.*;

@Entity
public class UserRole {

    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account user;

    private String role;

    public UserRole() {
    }

    public UserRole(Account user, String role) {
        this.user = user;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id",
            unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Account getAccount() {
        return this.user;
    }

    public void setAccount(Account user) {
        this.user = user;
    }

    @Column(name = "role", nullable = false, length = 45)
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}