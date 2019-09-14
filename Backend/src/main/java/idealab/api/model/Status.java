package idealab.api.model;

public enum Status {
    PENDING_REVIEW("Pending Review"),
    FAILED("Failed"),
    PRINTING("Printing"),
    PENDING_CUSTOMER_RESPONSE("Pending Customer Response"),
    REJECTED("Rejected"),
    COMPLETED("Completed"),
    ARCHIVED("Archived");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Status fromName(String text) {
        for (Status s : Status.values()) {
            if (s.name.equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }
} 