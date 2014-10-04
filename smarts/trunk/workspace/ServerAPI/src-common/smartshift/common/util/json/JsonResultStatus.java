package smartshift.common.util.json;

/**
 * The set of predefined result statuses
 * 
 * @author fevans
 */
public enum JsonResultStatus {
    Error("error"), Success("success"), Unauthorized("unauthorized");

    private String stringValue;

    JsonResultStatus(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * @return a string representation of the status
     */
    @Override
    public String toString() {
        return stringValue;
    }
}
