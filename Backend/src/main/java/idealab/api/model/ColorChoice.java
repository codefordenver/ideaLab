package idealab.api.model;

public enum ColorChoice {
    BLUE ("Blue"),
    YELLOW ("Yellow"),
    ORANGE ("ORANGE");

    private final String name;

    ColorChoice(String name) {
        this.name = name;
    }
}