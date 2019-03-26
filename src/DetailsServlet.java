

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/details")
public class DetailsServlet extends HttpServlet {
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
			PreparedStatement pst=con.prepareStatement("select * from user_table where userid=?");
			pst.setString(1,uid);
			ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				out.println("<table align=\"center\" border=\"1px\" cellpadding=\"2\" cellspacing=\"4\">");
				do {
					String id=rs.getString("userid");
					String name=rs.getString("name");
					String ct=rs.getString("city");
					String em=rs.getString("email");
					double sal=rs.getDouble("salary");
					out.println("<tr><td>User Id</td><td>"+id+"</td></tr>");
					out.println("<tr><td>Name</td><td>"+name+"</td></tr>");
					out.println("<tr><td>City</td><td>"+ct+"</td></tr>");
					out.println("<tr><td>Email</td><td>"+em+"</td></tr>");
					out.println("<tr><td>Salary</td><td>"+sal+"</td></tr>");
				}while(rs.next());
				out.print("</table>");
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
