package MVC.persistence.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by varArg on 31.03.2015.
 */
@Entity
@Table(name = "phone")


public class Phone implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer count;


    @ElementCollection
    private List<String> images;
    private String description;
    private Double cost;


    public Phone() {
    }

    public Phone(String name, Integer count, List<String> images, String description, Double cost) {
        this.name = name;
        this.count = count;
        this.images = images;
        this.description = description;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public List<String> getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "cost=" + cost +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", images=" + images +
                ", description='" + description + '\'' +
                '}';
    }

    public Double getCost() {
        return cost;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(Integer count) {
        if (this.count.equals(0)) this.count = 10;
        this.count = this.count - count;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

}
