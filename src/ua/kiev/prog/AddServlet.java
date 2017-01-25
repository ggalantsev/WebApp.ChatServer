package ua.kiev.prog;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends HttpServlet {

	private RoomList roomList = RoomList.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String room = req.getParameter("room");
        if (room==null||room.equals("")){room = "Main";}
        if(!UserList.isUserExist("Webform")) {
            UserList.autorize("WebForm","none",req.getSession());
        }
        roomList.getMessageList(room)
                .addMessage(new Message(
                        "WebForm",
                        req.getParameter("message").replaceAll("\n","<br>"),
                        room
                ));
        roomList.getUserList(room)
                .addUserInRoom("WebForm");//"WebForm");

        resp.sendRedirect("/room?name="+room);
    }

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);
        Message msg = Message.fromJSON(bufStr);
        String room = msg.getRoom();
        if (msg != null) {
            if (msg.getText().length()>7&&msg.getText().substring(0, 8).toLowerCase().equals("getusers")){
                resp.setHeader("getUsers", Arrays.deepToString(UserList.getUsers().toArray()));
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);return;
            } else
            if (msg.getText().length()>8&&msg.getText().substring(0, 9).toLowerCase().equals("isonline:")){
                String user = msg.getText().substring(9, msg.getText().length());
                resp.setHeader("isOnline","User \""+user+"\" is " +
                        (UserList.isOnline(user)?"online.":"offline."));
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);return;
            } else
            if (msg.getText().length()>2&&msg.getText().substring(0, 3).toLowerCase().equals("to:")) {
                msg.setTo(msg.getText().substring(3, msg.getText().indexOf(' ')));
                msg.setText(msg.getText().substring(msg.getText().indexOf(' ')+1, msg.getText().length()));
            }
            if (room==null||room.equals("")){room = "Main";}
            roomList.getUserList(room)
                    .addUserInRoom(msg.getFrom());
            UserList.setSession(msg.getFrom(),req.getSession());
            roomList.getMessageList(room).addMessage(msg);
        } else
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

	private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}
