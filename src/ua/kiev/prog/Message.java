package ua.kiev.prog;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
	private LocalDateTime date = LocalDateTime.now();
	private String from;
	private String to;
	private String text;

	public Message(String from, String text) {
		this.from = from;
		this.text = text;
	}

	public String toJSON() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	
	public static Message fromJSON(String s) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(s, Message.class);
	}
	
	@Override
	public String toString() {
		if (this.getTo()==null){
			return new StringBuilder().append("[").append(getDateString())
					.append(", From: ").append(from)
					.append("] ").append(text)
					.toString();
		} else
		return new StringBuilder().append("[").append(getDateString())
				.append(", From: ").append(from).append(", To: ").append(to)
				.append("] ").append(text)
                .toString();
	}

	public int send(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
	
		OutputStream os = conn.getOutputStream();
		try {
			String json = toJSON();
			os.write(json.getBytes(StandardCharsets.UTF_8));
			return conn.getResponseCode();
		} finally {
			os.close();
		}
	}
	
	public LocalDateTime getDate() {
		return date;
	}

	public String getDateString(){
		return new StringBuilder().append(date.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault())).append(" ")
				.append(date.getDayOfMonth()).append(" ")
				.append(date.getHour()).append(":")
				.append(date.getMinute()).toString();
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
