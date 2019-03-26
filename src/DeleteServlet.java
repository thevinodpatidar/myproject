

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid=request.getParameter("uid");
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head><title>Login Servlet</title></head>");
		out.println("<body>");
		RequestDispatcher rd=request.getRequestDispatcher("head.html");
		rd.include(request, response);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/register","root","root");
			System.out.println("Connected to Database");
			PreparedStatement pst=con.prepareStatement("delete from user_table where userid=?");
			pst.setString(1,uid);
			int r=pst.executeUpdate();
			if(r>0) {
				out.println("<h2>User Deleted</h2>");
				out.println("<br><br>");
				out.println("<a href=\"reg.html\">Register</a><br>");
				out.println("<a href=\"showAll\">Show All</a>");
			}else {
				out.println("<h2>Sorry! Invalid UserID</h2>");
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
