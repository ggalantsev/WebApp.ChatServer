package ua.kiev.prog;

import java.util.HashMap;

public class RoomList {

    private static final HashMap<String,Room> roomList = new HashMap<>();

    public static RoomList getInstance() {
        return new RoomList();
    }

    public MessageList getMessageList(String roomName){
        if (roomList.get(roomName)==null)
            roomList.put(roomName, new Room(new MessageList(), new UserList()));
        return roomList.get(roomName).getMessageList();
    }

    public UserList getUserList(String roomName){
        if (roomList.get(roomName)==null)
            roomList.put(roomName, new Room(new MessageList(), new UserList()));
        return roomList.get(roomName).getUserList();
    }



}