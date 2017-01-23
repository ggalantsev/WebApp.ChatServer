package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/room")
public class ChatHistoryServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        List<Message> list = MessageList.getInstance().getList();

        sb.append("<html>\n" +
                "  <head>\n" +
                "    <title>ChatServer</title>\n" +
                "  </head>\n" +
                "  <body style=\"background-color: #111111\">\n" +
                "  <div style=\"width: 800px;margin: 10px auto; padding:10px; min-height: 600px; color: #ddd; background-color: #181818\">\n" +
                "  <h1 style=\"margin: 20px\">Chat server is running!</h1>" +
                "  <h2 style=\"margin: 20px\">Main room</h2>");

        sb.append("<div style=\"max-width: 200px;min-width: 150px;min-height: 100px;margin: 0px 825px;top: 100px;position: fixed;background-color: #144;padding: 10px;border-left: 5px solid #388;\">\n" +
                "      <b style=\"margin-left: 10px;\">Users in this room:</b><br>\n" +
                "      <ul style=\"padding: 0 15px;\">");
        for (String s : UserList.getUsers()) {
            sb.append("<li>"+s+"</li>\n");
        }
        sb.append("</ul>\n" +
                "    </div>\n");

        for (Message m : list) {
            sb.append("<div style=\"width: 100%;display: inline-block; background-color: #222222; margin-bottom: 5px;padding: 5px 0;\">\n" +
                    "      <div style=\"min-width: 15%;float: left; padding: 1%;text-align: right\">\n" +
                    "        <b>")
                    .append(m.getFrom()).append("</b><br><div style=\"color: #888\">")
                    .append(m.getDateString()).append("</div>\n" +
                    "      </div>\n" +
                    "      <div style=\"max-width: 78%; float: left; padding: 1%; border-left: 1px solid #555\">")
                    .append(m.getTo()!=null?("<b><ins>"+m.getTo()+",</b></ins><br>"):"")
                    .append(m.getText()).append("</div>\n" +
                    "    </div>\n" +
                    "    ");
        }
        sb.append("  </div>\n" +
                "  </body>\n" +
                "</html>");
        PrintWriter pw = resp.getWriter();
        pw.println(sb.toString());
        pw.close();
    }
}
