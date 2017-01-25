package ua.kiev.prog;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class UserList {

    private static final HashMap<String,User> users = new HashMap<>();
    private SortedSet<User> usersInRoom = new TreeSet<>((user, t1) -> user.getName().compareTo(t1.getName()));

    public static int autorize(String user, String pass, HttpSession session) {
        if (users.get(user) != null) {
            if (users.get(user).checkPass(pass)){
                users.get(user).setSession(session);
                return 1; // autorized +
            } else {
                return -1; // unautorized -
            }
        } else {
            users.put(user, new User(user,pass,session));
            return 0; // Registered +
        }
    }

    public static void setSession(String username, HttpSession session){
        users.get(username).setSession(session);
    }

    public static boolean isUserExist(String username){
        return users.containsKey(username);
    }
    public static boolean isOnline(String username){
        if (users.get(username) != null){
           return users.get(username).isOnline();
        } return false;
    }

    public static ArrayList<String> getUsers() {
        ArrayList<String> list = new ArrayList<>();
        users.forEach(
                    (s, user) -> {
                    if(user.isOnline())
                        list.add(s.concat(" (online)"));
                    else list.add(s);
                    });
        return list;
    }

    public static ArrayList<String> getUsersOnline() {
        ArrayList<String> list = new ArrayList<>();
        users.forEach((s, user) -> {if(user.isOnline()) list.add(s);} );
        return list;
    }

    public void addUserInRoom(String name){
        if(this.usersInRoom.contains(users.get(name)))
            return;
        this.usersInRoom.add(users.get(name));
    }

    public ArrayList<String> getUsersInRoom() {
        ArrayList<String> rew = new ArrayList<>();
        usersInRoom.forEach(
                ( user) -> {
                    if(user.isOnline())
                        rew.add(user.getName().concat(" (online)"));
                    else rew.add(user.getName());
                });
        return rew;
    }
}
