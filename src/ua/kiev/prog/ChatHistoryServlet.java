package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(urlPatterns = "/room")
public class ChatHistoryServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String room = req.getParameter("name");
        if (room==null||room.equals("")){room = "Main";}
        StringBuilder sb = new StringBuilder();
        RoomList roomList = RoomList.getInstance();
        List<Message> list = roomList.getMessageList(room).getList();

        sb.append("<html>\n" +
                "  <head>\n" +
                "    <title>ChatServer</title>\n" +
                "  </head>\n" +
                "  <body style=\"background-color: #111111\">\n" +
                "  <div style=\"width: 800px;margin: 10px auto; padding:10px; min-height: 600px; color: #ddd; background-color: #181818\">\n" +
                "  <h1 style=\"margin: 25px\">Chat server is running!</h1>" +
                "  <h2 style=\"margin: 20px\">"+room+" room</h2>");

        sb.append("<div style=\"width: 175px;min-height: 100px;margin: 0px 600px;top: 20px;position: fixed;background-color: rgba(28, 97, 97, 0.5);padding: 10px;border-right: 5px solid 5px solid rgba(62, 181, 181, 0.8);\">\n" +
                "      <b style=\"margin-left: 10px;\">Users in this room:</b><br>\n" +
                "      <ul style=\"padding: 0 15px;\">");
        for (Object s : roomList.getUserList(room).getUsersInRoom()) {
            sb.append("<li>").append(s).append("</li>\n");
        }
        sb.append("</ul>\n" +
                "    </div>\n");

        for (Message m : list) {
            sb.append("<div style=\"width: 100%;display: inline-block; background-color: rgba(13, 99, 123, 0.3); margin-bottom: 5px;padding: 5px 0;\">\n" +
                    "      <div style=\"min-width: 15%;float: left; padding: 1%;text-align: right\">\n" +
                    "        <b>")
                    .append(m.getFrom()).append("</b><br><div style=\"color: #888\">")
                    .append(m.getDateString()).append("</div>\n" +
                    "      </div>\n" +
                    "      <div style=\"max-width: 78%; float: left; padding: 1%; border-left: 1px solid #555\">")
                    .append(m.getTo()!=null?("<b><ins style=\"color: orange;\">"+m.getTo()+",</b></ins><br>"):"")
                    .append(m.getText()).append("</div>\n" +
                    "    </div>\n" +
                    "    ");
        }
        sb.append("  </div>\n" +
                "  </body>\n" +
                "</html>");

            OutputStream os = resp.getOutputStream();
            byte[] buf = sb.toString().getBytes(StandardCharsets.UTF_8);
            os.write(buf);
    }
}
