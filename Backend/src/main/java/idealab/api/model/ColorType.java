package idealab.api.model;

import javax.persistence.*;
import java.util.Set;



@Entity
@Table(name = "color_type")
public class ColorType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(targetEntity=PrintJob.class, mappedBy="colorTypeId")
    private Set<PrintJob> printJobs;

    @Column(name = "color", nullable = false)
    private String color;

    public ColorType() {}

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

