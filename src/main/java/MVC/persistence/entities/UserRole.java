package MVC.persistence.entities;


import javax.persistence.*;


@Entity
@Table(name = "roles")
public class UserRole extends MappedModel {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "role", nullable = false, length = 45)
    private String role;

    public UserRole() {
    }

    public UserRole(Account account, String role) {
        this.account = account;
        this.role = role;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account user) {
        this.account = user;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}