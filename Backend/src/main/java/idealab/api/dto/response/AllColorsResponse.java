package idealab.api.dto.response;

import java.util.List;

public class AllColorsResponse {
    private List<ColorResponse> colors;

    public AllColorsResponse(){}

    public AllColorsResponse(List<ColorResponse> colors){
        this.colors = colors;
    }

    public List<ColorResponse> getColors() {
        return colors;
    }

    public void setColors(List<ColorResponse> colors) {
        this.colors = colors;
    }
}
