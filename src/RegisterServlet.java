

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/doReg")
public class RegisterServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("uid");
		String nm=request.getParameter("nm");
		String ct=request.getParameter("ct");
		String em=request.getParameter("em");
		String s=request.getParameter("sal");
		int sal=Integer.parseInt(s);
		String pwd=request.getParameter("pwd");
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<head><title>Register Servlet</title></head>");
		out.println("<body>");
		RequestDispatcher rd=request.getRequestDispatcher("head.html");
		rd.include(request, response);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","root");
			Statement st = con.createStatement();
			System.out.println("Connected to Database");
			String db = "create database if not exists register";
			String usedb = "use register";
			st.executeUpdate(db);
			st.execute(usedb);
//			DatabaseMetaData dbm = con.getMetaData();
//			ResultSet rs = dbm.getTables(null, null, this.getTableName(),null);
			String createDB = "CREATE TABLE IF NOT EXISTS user_table"+ 
							  "(userid VARCHAR(25),"+
							  "name VARCHAR(30),"+
							  "city VARCHAR(10),"+
							  "email VARCHAR(20),"+
							  "password VARCHAR(20),"+
							  "salary int(20))";
			st.executeUpdate(createDB);
			PreparedStatement pst=con.prepareStatement("insert into user_table(userid,name,city,email,password,salary) values(?,?,?,?,?,?)");
			pst.setString(1,id);
			pst.setString(2, nm);
			pst.setString(3, ct);
			pst.setString(4, em);
			pst.setString(5, pwd);
			pst.setInt(6, sal);
			int r=pst.executeUpdate();
			if(r!=0) {
				out.println("<h1>Registered Successfully</h1>");
				out.println("<h2>With UserID: "+id+"</h2>");
				out.println("<p><button><a href=\"reg.html\">Register</a></button></p>");
				out.println("<p><button><a href=\"login.html\">Login</a></button></p>");
			}else {
				out.println("<h1>Error In Registration</h1>");
				out.println("<p><a href=\"reg.html\">Register</a></p>");
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
