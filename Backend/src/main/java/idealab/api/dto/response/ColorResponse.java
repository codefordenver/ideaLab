package idealab.api.dto.response;


import java.time.LocalDateTime;
import java.util.List;

public class ColorResponse {
    private Integer id;
    private String color;
    private Boolean available;

    public ColorResponse(){}

    public ColorResponse(Integer id,  String color, Boolean available){
        this.id = id;
        this.color = color;
        this.available = available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}