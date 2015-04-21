package MVC.persistence.entities;

import MVC.config.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by en on 20.04.2015.
 */


@Entity
@Table(name = "orders")
public class Order extends MappedModel {


    private String uuid;

    @JsonSerialize(using = JsonDateSerializer.class)
    private Date orderDate;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String comments;
    private String summ;


    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account account;

    @Override
    public String toString() {
        return "Order{" +
                "uuid='" + uuid + '\'' +
                ", orderDate=" + orderDate +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", comments='" + comments + '\'' +
                ", summ='" + summ + '\'' +
                ", account=" + account +
                '}';
    }

    @ManyToMany()
    private List<Phone> phoneList;

    public Account getAccount() {
        return account;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public Order() {
    }

    public Order(String name, String email, String phone, String address, String comments) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.comments = comments;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSumm() {
        return summ;
    }

    public void setSumm(String summ) {
        this.summ = summ;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
