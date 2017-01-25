package ua.kiev.prog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LoginServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] bytes = requestBodyToArray(req);

        String s = new String(bytes, StandardCharsets.UTF_8);
        String[] loginPass = s.split(":");
        System.out.println("New login request");
        System.out.println("Login: "+loginPass[0] +", Password: "+ loginPass[1] +".");
        int autorize = UserList.autorize(loginPass[0], loginPass[1],req.getSession());
        if (autorize==1){
            System.out.println("1: Login success!");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader("Response","Login success!" );
        } else if (autorize == 0){
            System.out.println("0: User registered!");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader("Response","User registered!" );
        } else if (autorize == -1){
            System.out.println("-1: Incorrect password!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setHeader("Response","Incorrect password!" );
        }
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
