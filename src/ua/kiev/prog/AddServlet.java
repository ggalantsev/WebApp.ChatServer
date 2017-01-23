package ua.kiev.prog;

import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends HttpServlet {

	private MessageList msgList = MessageList.getInstance();
//	private RoomList roomList = MessageList.getInstance();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        Message msg = Message.fromJSON(bufStr);
        if (msg != null) {
            if (msg.getText().length()>2&&msg.getText().substring(0, 3).toLowerCase().equals("to:")) {
                msg.setTo(msg.getText().substring(3, msg.getText().indexOf(' ')));
                msg.setText(msg.getText().substring(msg.getText().indexOf(' ')+1, msg.getText().length()));
            }
            msgList.add(msg);
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
