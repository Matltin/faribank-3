package ir.ac.kntu.box;

public enum BoxType {
    SAVING("saving"),
    REMAINING("remaining"),
    PROFIT("profit");

    private final String type;

    BoxType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
