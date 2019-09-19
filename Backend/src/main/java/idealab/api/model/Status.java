package idealab.api.model;

public enum Status {
    PENDING_REVIEW("PENDING_REVIEW", true),
    FAILED("FAILED", true),
    PRINTING("PRINTING", true),
    PENDING_CUSTOMER_RESPONSE("PENDING_CUSTOMER_RESPONSE", true),
    REJECTED("REJECTED", true),
    COMPLETED("COMPLETED", true),
    ARCHIVED("ARCHIVED", true);

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