package ir.ac.kntu.db;

import ir.ac.kntu.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageDB {
    private List<Message> messageList;

    public MessageDB() {
        messageList = new ArrayList<>();
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public int size() {
        return messageList.size();
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public void addMessage(Message message) {
        messageList.add(message);
    }

    public void printMessage() {
        int counter = 1;
        for (Message message : messageList) {
            System.out.println(counter + "." + message.getMessage());
            counter++;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MessageDB messageDB = (MessageDB) obj;
        return Objects.equals(messageList, messageDB.messageList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messageList);
    }
}
