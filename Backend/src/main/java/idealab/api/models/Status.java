package idealab.api.models;

public enum Status {
    PENDING_REVIEW ("Pending Review"),
    FAILED ("Failed"),
    PRINTING ("Printing"),
    PENDING_CUSTOMER_RESPONSE ("Pending Customer Response"),
    REJECTED ("Rejected"),
    COMPLETED ("Completed"),
    ARCHIVED ("Archived");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}