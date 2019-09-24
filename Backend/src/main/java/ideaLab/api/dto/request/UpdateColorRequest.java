package idealab.api.dto.request;

public class UpdateColorRequest {

    private Integer id;
    private String color;
    private Boolean available;

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

    @Override
    public String toString() {
        return "AddColorRequest{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", available='" + available + '\'' +
                '}';
    }
}
