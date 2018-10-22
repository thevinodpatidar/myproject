

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/checkLogin")
public class CheckLoginServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String uid=request.getParameter("uid");
	  String pwd=request.getParameter("pwd");
	  
	  response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head><title>Login Servlet</title></head>");
		out.println("<body>");
		RequestDispatcher rd=request.getRequestDispatcher("head.html");
		rd.include(request, response);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/register","root","");
			System.out.println("Connected to Database");
			PreparedStatement pst=con.prepareStatement("select name from user_table where userid=? and password=?");
			pst.setString(1,uid);
			pst.setString(2,pwd);
			ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				String name=rs.getString(1);
//				Cookie ck1=new Cookie("userid",uid);
//				Cookie ck2=new Cookie("uname",name);
//				response.addCookie(ck1);
//				response.addCookie(ck2);
				HttpSession session=request.getSession();
				session.setAttribute("userid",uid);
				session.setAttribute("uname",name);
				response.sendRedirect("home");
			}else {
//				out.println("<h2 align=\"center\">Sorry! Login Fail</h2>");
//				out.println("<h2 align=\"center\"<button><a href=\"login_fail.html\">Try Again</a></button></h2>");
				
				response.sendRedirect("login_fail.html");
			}
			pst.close();
			con.close();
	}catch(Exception e) {
		out.println("Error "+e);
	}
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
}
