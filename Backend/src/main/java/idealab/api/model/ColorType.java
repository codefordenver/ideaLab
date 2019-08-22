package idealab.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "color_type")
public class ColorType {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToMany(targetEntity=PrintModel.class, mappedBy="colorTypeId")   
    private Set<PrintModel> printModel;

    @Column(name = "color", nullable = false)
    private String color;

    public ColorType(Integer id, String color) {
        
        this.color = color;
    }

    //getters and setters
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

