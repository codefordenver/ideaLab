package ideaLab.api.models;

public enum ColorChoice {
    BLUE ("Blue"),
    YELLOW ("Yellow"),
    ORANGE ("ORANGE");

    private final String name;

    ColorChoice(String name) {
        this.name = name;
    }
}