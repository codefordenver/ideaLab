package idealab.api.model;

public enum EmployeeRole {
    ADMIN ("Admin"),
    STAFF ("Staff");

    private final String name;

    EmployeeRole(String name) {
        this.name = name;
    }
}