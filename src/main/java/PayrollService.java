import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PayrollService {
	public static void main(String[] args) throws Exception {
		
		String url="jdbc:mysql://localhost:3306/payroll_service";
		String userName="root";
		String pass="root";
		Class.forName("com.mysql.cj.jdbc.Driver"); //2(a).load
		Connection con = DriverManager.getConnection(url,userName,pass);
		Statement st=con.createStatement();
		
		String query="select * from Payroll_Service;";
		ResultSet rs= st.executeQuery(query);
		rs.next();
		String name=rs.getString("NAME");
		
		System.out.println(name);
		
		st.close();
		con.close();
	}

}
