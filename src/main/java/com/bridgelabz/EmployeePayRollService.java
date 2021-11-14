package com.bridgelabz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayRollService {

	public ArrayList<Employee> empList;
	PreparedStatement preparedStatement;
	Connection connection = EmployeeConfig.getConfig();

	public List<Employee> queryExecute(String query) {
		empList = new ArrayList<Employee>();
		try {
			preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Employee employee = new Employee();
				
				 	employee.setID(resultSet.getInt("ID"));
	                employee.setNAME(resultSet.getString("Name"));
	                employee.setEmployee_ID(resultSet.getInt("employee_ID"));
	                employee.setPhone_number(resultSet.getInt("phone_number"));
	                employee.setAddress(resultSet.getString("address"));
	                employee.setDepartment(resultSet.getString("department"));
	                employee.setDepartment_ID(resultSet.getInt("department_ID"));
	                employee.setGENDER(resultSet.getString("GENDER"));
	                employee.setBasic_pay(resultSet.getDouble("basic_pay"));
	                employee.setDeductions(resultSet.getDouble("deductions"));
	                employee.setTaxable_pay(resultSet.getDouble("taxable_pay"));
	                employee.setTax(resultSet.getDouble("tax"));
	                employee.setNet_pay(resultSet.getDouble("net_pay"));
	                employee.setSALARY(resultSet.getDouble("SALARY"));
	                employee.setSTART_DATE(resultSet.getString("START_DATE"));
	                
				empList.add(employee);
			}
		} catch (SQLException e) {
			throw new EmployeeException("invalid column label");
		}
		return empList;
	}

	public void display() {
		for (Employee i : empList) {
			System.out.println(i.toString());
		}
	}

	public double updateBasicPay(String NAME, double basic_pay) {
		String UPDATE = "UPDATE payroll_service SET basic_pay = ? WHERE NAME = ?";
		try {
			preparedStatement = connection.prepareStatement(UPDATE);
			preparedStatement.setDouble(1, basic_pay);
			preparedStatement.setString(2, NAME);
			preparedStatement.executeUpdate();
			System.out.println("update successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sql = "SELECT * FROM payroll_service";
		queryExecute(sql);
		for (Employee employee : empList) {
			if (employee.getNAME().equals(NAME)) {
				return employee.getBasic_pay();
			}
		}
		return 0.0;
	}

}