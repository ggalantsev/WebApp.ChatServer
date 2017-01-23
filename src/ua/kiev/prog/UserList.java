package ua.kiev.prog;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class UserList {

    private static final HashMap<String,String> users = getUsersFromFile();

    private static HashMap<String,String> getUsersFromFile(){
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(
                                    new File("d:\\users.dat")));
            return (HashMap<String, String>)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int autorize(String user,String pass) {
        if (users.get(user) != null) {
            if (users.get(user).equals(pass)){
                return 1; // autorized +
            } else {
                return -1; // unautorized -
            }
        } else {
            users.put(user, pass);
            updateUserListFile();
            return 0; // Registered +
        }
    }

    private static void updateUserListFile(){
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(
                                    new File("d:\\users.dat")));
            oos.writeObject(users);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getUsers() {
        return new ArrayList<String>(users.keySet());
    }
}
