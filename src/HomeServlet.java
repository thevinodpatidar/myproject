

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
Cookie[]cookies=	request.getCookies();
		
		String uid=null;
		String uname=null;
		if (cookies!=null){
			for(Cookie ck : cookies){
				if(ck.getName().equals("userid")){
					uid=ck.getValue();
				}else if(ck.getName().equals("uname")){
					uname=ck.getValue();
				}				
			}
		}		
		if(uid==null){
			response.sendRedirect("invalid.html");
		}
		
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Registeration Servlet</TITLE></HEAD>");
		out.println("<BODY>");

		RequestDispatcher rd=request.getRequestDispatcher("head.html");
		rd.include(request, response);

		
	
		out.println("<h2 align=\"center\">Welcome "+uname+"</h2>");
		out.println("<br><br>");
		RequestDispatcher rd1=request.getRequestDispatcher("CalculatorList.html");
		rd1.include(request, response);
//		out.println("<a href=\"showAll\">Show All</a>");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();	
	}

}
