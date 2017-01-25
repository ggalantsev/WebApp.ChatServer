package ua.kiev.prog;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetListServlet extends HttpServlet {
	private RoomList roomList = RoomList.getInstance();


    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	String fromStr = req.getParameter("from");
		if (fromStr==null||fromStr.equals("")){fromStr = "0";}
    	String room = req.getParameter("room");
    	if (room==null||room.equals("")){room = "Main";}
		long from;
		try {
			 from = Integer.parseInt(fromStr);
		} catch (Exception ex) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
		}
		
		String json = roomList.getMessageList(room).toJSON(from); // from - unix timestamp
		if (json != null) {
			OutputStream os = resp.getOutputStream();
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);
			os.write(buf);
		}
	}
}

//{"list":
//		[
//		 {
// 		  "date":"Jan 6, 2017 9:57:28 PM",
//		  "from":"sf",
//		  "text":"assalsk;"
//       },
//		 {"date":"Jan 6, 2017 9:57:31 PM","from":"sf","text":"dddfdfvd"},
//		 {"date":"Jan 6, 2017 9:59:26 PM","from":"sf","text":"232lnlsd"}
//		 ]
//}