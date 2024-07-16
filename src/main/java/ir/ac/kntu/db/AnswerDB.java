package ir.ac.kntu.db;

import ir.ac.kntu.Message;

import java.util.ArrayList;
import java.util.List;

public class AnswerDB {
    private static List<Message> messageList;

    public AnswerDB() {
        messageList = new ArrayList<>();
    }

    public static List<Message> getMessageList() {
        return messageList;
    }

    public static void add(Message message) {
        messageList.add(message);
    }

    public static void remove(Message message) {
        messageList.remove(message);
    }

    public static int size() {
        return messageList.size();
    }

    public static void printMessage() {
        int counter = 1;
        for (Message message : messageList) {
            System.out.println(counter + "." + "message branch : " + message.getMessageOption()
                    + "phone number : " + message.getPhoneNumber());
            counter++;
        }

    }
}
