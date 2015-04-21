package MVC.persistence.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by varArg on 31.03.2015.
 */
@Entity
@Table(name = "phones")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Phone extends MappedModel {

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(Integer count) {
        this.count = count;
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
