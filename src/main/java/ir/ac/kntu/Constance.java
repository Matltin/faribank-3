package ir.ac.kntu;

public class Constance {

    private static long wage = 500;

    private static long simWage = 200;

    private static long fariFariWage = 0;

    private static long fariCardWage = 300;

    private static long fariPole = 2;

    private static long fariPaya = 2000;

    private static long profit = 5;

    public static final long MILE_SECOND = 1000L * 60 * 60 * 24 * 30;

    public static final int VALUE_TO_DISPLAY = 3;

    public static final String BLACK = "\033[0;30m";   // BLACK

    public static final String RED = "\033[0;31m";     // RED

    public static final String GREEN = "\033[0;32m";   // GREEN

    public static final String YELLOW = "\033[0;33m";  // YELLOW

    public static final String BLUE = "\033[0;34m";    // BLUE

    public static final String PURPLE = "\033[0;35m";  // PURPLE

    public static final String CYAN = "\033[0;36m";    // CYAN

    public static final String WHITE = "\033[0;37m";   // WHITE

    public static final String RESET = "\u001B[0m";

    public static long getWage() {
        return wage;
    }

    public static void setWage(long wage) {
        Constance.wage = wage;
    }

    public static long getSimWage() {
        return simWage;
    }

    public static void setSimWage(long simWage) {
        Constance.simWage = simWage;
    }

    public static long getFariFariWage() {
        return fariFariWage;
    }

    public static void setFariFariWage(long fariFariWage) {
        Constance.fariFariWage = fariFariWage;
    }

    public static long getFariCardWage() {
        return fariCardWage;
    }

    public static void setFariCardWage(long fariCardWage) {
        Constance.fariCardWage = fariCardWage;
    }

    public static long getFariPole() {
        return fariPole;
    }

    public static void setFariPole(long fariPole) {
        Constance.fariPole = fariPole;
    }

    public static long getFariPaya() {
        return fariPaya;
    }

    public static void setFariPaya(long fariPaya) {
        Constance.fariPaya = fariPaya;
    }

    public static long getProfit() {
        return profit;
    }

    public static void setProfit(long profit) {
        Constance.profit = profit;
    }
}
