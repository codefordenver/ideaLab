package idealab.api.model;

public enum EmployeeRole {
    ADMIN ("ADMIN"),
    STAFF ("STAFF");

    private final String name;

    EmployeeRole(String name) {
        this.name = name;
    }

    public String getText() {
        return this.name;
    }

    public static EmployeeRole fromValue(String text) {
        for (EmployeeRole e : EmployeeRole.values()) {
            if (e.name.equalsIgnoreCase(text)) {
                return e;
            }
        }
        return null;
    }
}