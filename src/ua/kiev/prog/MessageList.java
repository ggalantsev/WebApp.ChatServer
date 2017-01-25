package ua.kiev.prog;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {

    private final Gson gson;
	private final List<Message> list = new LinkedList<>();
//	private final SortedSet<String> users = new TreeSet<>(String::compareToIgnoreCase); // users in room

	public MessageList() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

  	public synchronized void addMessage(Message m) {
		list.add(m);
	}
	
	public synchronized String toJSON(long n) { // n - unixTime
		return gson.toJson(new JsonMessages(list, n));
	}

	public List<Message> getList(){
		return list;
	}



}
