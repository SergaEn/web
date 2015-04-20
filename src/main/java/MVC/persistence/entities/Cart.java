package MVC.persistence.entities;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by VarArg on 19.04.2015.
 */
@Entity
@Table(name = "cart")
public class Cart extends MappedModel {
    public Cart(){}
    public Cart(List<Phone> phones, Account account){
        this.account = account;
        this.phones = phones;
    }

    @OneToOne
    private Account account;

    @OneToMany
    private List<Phone> phones;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }


}
