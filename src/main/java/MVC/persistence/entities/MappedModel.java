package MVC.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by en on 16.04.2015.
 */
@MappedSuperclass
public abstract class MappedModel implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}