

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

@WebServlet("/showAll")
public class ShowAllServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
Cookie[]cookies=	request.getCookies();
		
		String uid=null;		
		if (cookies!=null){
			for(Cookie ck : cookies){
				if(ck.getName().equals("userid")){
					uid=ck.getValue();
				}			
			}
		}		
		if(uid==null){
			response.sendRedirect("invalid.html");
		}
		
		
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
			PreparedStatement pst=con.prepareStatement("select * from user_table");
			ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				out.println("<table border=\"1px\">");
				do {
					String id=rs.getString("userid");
					String name=rs.getString("name");
					String ct=rs.getString("city");
					out.println("<tr><td>"+id+"</td><td>"+name+"</td><td>"+ct+"</td><td><a href=\"delete?uid="+id+"\">Delete</a></td><td><a href=\"details?uid="+id+"\">More Details</a></td><td><a href=\"edit?uid="+id+"\">Update Details</a></td></tr>");
				}while(rs.next());
				out.println("</table>");
			}else {
				out.println("<h2 width=\"200px\">Sorry !</h2>");
			}
			pst.close();
			con.close();
	}catch(Exception e) {
		out.println("Error "+e);
	}
    out.println("</body>");
    out.println("</html>");
}
}