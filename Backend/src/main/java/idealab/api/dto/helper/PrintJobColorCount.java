package idealab.api.dto.helper;

public class PrintJobColorCount {
    private String color;
    private Integer count;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PrintJobColorCount{" +
                "color='" + color + '\'' +
                ", count=" + count +
                '}';
    }
}