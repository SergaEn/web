package MVC.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by VarArg on 19.04.2015.
 */
@Entity
@Table(name = "basket")
public class Basket extends MappedModel {
    public Basket() {
    }

    public Basket(List<Phone> phones, Account account) {
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
