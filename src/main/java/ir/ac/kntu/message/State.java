package ir.ac.kntu.message;

public enum State {
    SUBMIT,
    IN_PROGRESS,
    CLOSED,
    BACK;

    public static void printOption() {
        System.out.println("\n1.Submit\n" +
                "2.In progress\n" +
                "3.Closed\n" +
                "4.Back");
    }
}
