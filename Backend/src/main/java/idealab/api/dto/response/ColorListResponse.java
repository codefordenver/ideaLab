package idealab.api.dto.response;

import java.util.List;

public class ColorListResponse {
    private List<ColorResponse> colors;

    public ColorListResponse(){}

    public ColorListResponse(List<ColorResponse> colors){
        this.colors = colors;
    }

    public List<ColorResponse> getColors() {
        return colors;
    }

    public void setColors(List<ColorResponse> colors) {
        this.colors = colors;
    }
}
