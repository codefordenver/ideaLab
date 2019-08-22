package idealab.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "queue")
public class Queue {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne()
    @JoinColumn(name="fk_print_model_id", referencedColumnName = "id", nullable = false)
    private PrintModel printModelId;
    
    @Column(name = "rank", nullable = false)
    private Integer rank;

    public Queue(Integer id, Integer rank) {
        this.id = id;
        this.rank = rank;
    }

    //getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setEmailHash(Integer rank) {
        this.rank = rank;
    }

}

