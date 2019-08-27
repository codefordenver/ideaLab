package idealab.api.model;

import javax.persistence.*;
import java.util.Set;



@Entity
@Table(name = "color_type")
public class ColorType {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToMany(targetEntity=PrintJob.class, mappedBy="colorTypeId")
    private Set<PrintJob> printJobs;

    @Column(name = "color", nullable = false)
    private String color;

    public ColorType() {
    }
    
    public ColorType(String color) {
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

