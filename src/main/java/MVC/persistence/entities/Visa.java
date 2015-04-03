package MVC.persistence.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;


/**
 * Created by varArg on 31.03.2015.
 */
@Entity
@Table(name = "visa")

public class Visa implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String firstName;


    private String lastName;


    private String cartName;


    private String cartNumber;


    private Integer cvv;

    @Temporal(TemporalType.DATE)
    private Calendar expirationDate;
    private Double summ;


    public Visa() {
    }

    @Override
    public String toString() {
        return "Visa{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cartName='" + cartName + '\'' +
                ", cartNumber=" + cartNumber +
                ", cvv=" + cvv +
                ", expirationDate=" + expirationDate +
                '}';
    }

    public Visa(String lastName, String cartName, String cartNumber, Integer cvv, Calendar expirationDate, String firstName, Double summ) {

        this.lastName = lastName;
        this.cartName = cartName;
        this.cartNumber = cartNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.firstName = firstName;
        this.summ = summ;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCartName() {
        return cartName;
    }

    public String getCartNumber() {
        return cartNumber;
    }

    public Integer getCvv() {
        return cvv;
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public Double getSumm() {
        return summ;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public void setCartNumber(String cartNumber) {
        this.cartNumber = cartNumber;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setSumm(Double summ) {
        this.summ = summ;
    }


}
