package idealab.api.model;

public enum EmployeeRole {
    ADMIN ("Admin"),
    STAFF ("Staff");

    private final String name;

    EmployeeRole(String name) {
        this.name = name;
    }

    public String getText() {
        return this.name;
    }

    public static EmployeeRole fromString(String text) {
        for (EmployeeRole e : EmployeeRole.values()) {
            if (e.name.equalsIgnoreCase(text)) {
                return e;
            }
        }
        return null;
    }
}