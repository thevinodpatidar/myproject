

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

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("uid");
		String nm=request.getParameter("nm");
		String em=request.getParameter("em");
		String ct=request.getParameter("ct");
		String s=request.getParameter("sal");
		double sal=Double.parseDouble(s);

		
		response.setContentType("text/html");		
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Update Servlet</TITLE></HEAD>");
		out.println("<BODY>");
		RequestDispatcher rd=request.getRequestDispatcher("head.html");
		rd.include(request, response);
		try{
			Class.forName("com.mysql.jdbc.Driver");		
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/register","root","root");
			PreparedStatement pst=con.prepareStatement("update user_table set name=?,city=?,email=?,salary=? where userid=?");
			
			
			pst.setString(1, nm);
			pst.setString(2, ct);
			pst.setString(3, em);		
			pst.setDouble(4, sal);
			pst.setString(5, id);
			int r=pst.executeUpdate();
			if(r!=0){
				out.println("<h1 align=\"center\">Data Updated Successfully<h1>");			
				
//				out.println("<p><a href=\"showAll\">ShowAll</a></p>");
			}
			else{
				out.println("<h1>Error in Updatation</h1>");
				
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

