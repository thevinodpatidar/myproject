

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/doCalcList")
public class CalculatorListServlet extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s1=request.getParameter("no1");
		String s2=request.getParameter("no2");
		String oprs[]=request.getParameterValues("oprs");
		
		double n1=Double.parseDouble(s1);
		double n2=Double.parseDouble(s2);
		
		PrintWriter out=response.getWriter();
		
		out.println("<html>");
		out.println("<head><title>CalcList</title></head>");
		out.println("<body>");
		RequestDispatcher rd=request.getRequestDispatcher("head.html");
		rd.include(request, response);
		out.println("<h1>Calculator List</h1><br>");
		if(oprs!=null) {
			for(String op:oprs) {
				if(op.equals("A"))
				{
					double r=n1+n2;
					out.println("<h3 align=\"center\">Addition :"+r+"</h3>");
				}else if(op.equals("S"))
				{
					double r=n1-n2;
					out.println("<h3 align=\"center\">Subtraction :"+r+"</h2>");
				}else if(op.equals("M"))
				{
					double r=n1*n2;
					out.println("<h3 align=\"center\">Multipliction :"+r+"</h3>");
				}else if(op.equals("D"))
				{
					double r=n1/n2;
					out.println("<h3 align=\"center\">Division :"+r+"</h3>");
				}
			}
		}else
		{
			out.println("Operator Not Selected");
			
		}
		out.println("</body>");
		out.println("</html>");
	}

}
