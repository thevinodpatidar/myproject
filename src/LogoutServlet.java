

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Cookie[]cookies=	request.getCookies();
		
		String uid=null;
		String uname=null;
		if (cookies!=null){
			for(Cookie ck : cookies){
				if(ck.getName().equals("userid")){
					uid=ck.getValue();
					ck.setMaxAge(0);
					response.addCookie(ck);
				}else if(ck.getName().equals("uname")){
					uname=ck.getValue();
					ck.setMaxAge(0);
					response.addCookie(ck);
				}				
			}
		}		
		if(uid==null){
			response.sendRedirect("invalid.html");
			return;
		}
		
		response.sendRedirect("index.html");
		
	}

}
