package ua.kiev.prog;

import java.util.HashMap;

public class RoomList {

    private static final HashMap<String,MessageList> roomList = new HashMap<>();

    public static MessageList getMessageList(String roomName) {
        return roomList.get(roomName);
    }

    public static RoomList getInstance() {
        return new RoomList();
    }
}
