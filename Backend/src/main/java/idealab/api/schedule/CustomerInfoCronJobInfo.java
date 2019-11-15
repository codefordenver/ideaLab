package idealab.api.schedule;

import idealab.api.model.CustomerInfo;

import java.time.LocalDateTime;

public class CustomerInfoCronJobInfo {

    Integer id;
    LocalDateTime createdAt;

    public CustomerInfoCronJobInfo(CustomerInfo that)
    {
        this.id = that.getId();
        this.createdAt = that.getCreatedAt();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
