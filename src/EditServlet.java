

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


@WebServlet("/edit")
public class EditServlet extends HttpServlet {

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
		
		
		
		String id=request.getParameter("uid");
		
		response.setContentType("text/html");		
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Registeration Servlet</TITLE></HEAD>");
		out.println("<BODY>");
		RequestDispatcher rd=request.getRequestDispatcher("head.html");
		rd.include(request, response);
		try{
			Class.forName("com.mysql.jdbc.Driver");		
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/register","root","root");
			PreparedStatement pst=con.prepareStatement("select * from user_table where userid=?");
			
			pst.setString(1, id);
			ResultSet rs=pst.executeQuery();
			if(rs.next()){					
				
					String name=rs.getString("name");
					String ct=rs.getString("city");
					String em=rs.getString("email");
					double sal=rs.getDouble("salary");
					
					out.println("<form action=\"update\">");
					out.println("<input type=\"hidden\" name=\"uid\" value=\""+uid+"\">");
									
					out.println("<table border=\"1\">");
					out.println("<tr><th>User id</th><td>"+id+"</td></tr>");
					out.println("<tr><th>Name</th><td><input type=\"text\" name=\"nm\" value=\""+name+"\"></td></tr>");
					out.println("<tr><th>City</th><td><input type=\"text\" name=\"ct\" value=\""+ct+"\"></td></tr>");
					out.println("<tr><th>Email</th><td><input type=\"text\" name=\"em\" value=\""+em+"\"></td></tr>");
					out.println("<tr><th>Salary</th><td><input type=\"text\" name=\"sal\" value=\""+sal+"\"></td></tr>");
					out.println("</table>");		
					out.println("<button type=\"submit\">Update</button>");
				out.println("</table>");
				out.println("</form>"); 
			}else{
				out.println("<h2>Sorry! No Data Found</h2>");
					}
			pst.close();
			con.close();
		}catch (Exception e) {		
		out.println("Error "+e);
		}		
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();	
	}

}
