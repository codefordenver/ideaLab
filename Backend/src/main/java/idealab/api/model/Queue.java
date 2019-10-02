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
public class Queue extends RecordTimestamp {
    
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne()
    @JoinColumn(name="fk_print_job_id", referencedColumnName = "id", nullable = false)
    private PrintJob printJobId;
    
    @Column(name = "rank", nullable = false)
    private Integer rank;

    public Queue() {}

    public Queue(Integer rank) {
        this.rank = rank;
    }

    //getters and setters
    public Integer getRank() {
        return rank;
    }

    public void setEmailHash(Integer rank) {
        this.rank = rank;
    }

}

