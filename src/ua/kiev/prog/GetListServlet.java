package ua.kiev.prog;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetListServlet extends HttpServlet {
	
	private MessageList msgList = MessageList.getInstance();

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String fromStr = req.getParameter("from");
		long from = 0;
		try {
			if(fromStr.equals("")){} //show all if empty
			else from = Integer.parseInt(fromStr);
		} catch (Exception ex) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
		}
		
		String json = msgList.toJSON(from); // from - unix timestamp
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