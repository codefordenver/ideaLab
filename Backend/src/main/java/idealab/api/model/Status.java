package idealab.api.model;

public enum Status {
    PENDING_REVIEW("Pending Review", true),
    FAILED("Failed", true),
    PRINTING("Printing", true),
    PENDING_CUSTOMER_RESPONSE("Pending Customer Response", true),
    REJECTED("Rejected", true),
    COMPLETED("Completed", true),
    ARCHIVED("Archived", true);

    private final String name;
    private final boolean isValid;

    Status(String name, boolean isValid) {
        this.name = name;
        this.isValid = isValid;
    }

    public String getName() {
        return this.name;
    }

    public boolean isValid(){
        return isValid;
    }

    public static Status fromValue(String text) {
        for (Status s : Status.values()) {
            if (s.name.equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }
} 