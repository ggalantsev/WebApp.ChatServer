package ua.kiev.prog;

public class Room {

    private MessageList messageList;
    private UserList userList;

    public Room(MessageList messageList, UserList userList) {
        this.messageList = messageList;
        this.userList = userList;
    }

    public MessageList getMessageList() {
        return messageList;
    }

    public UserList getUserList() {
        return userList;
    }
}
