package com.bridgelabz;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	public void getEmployee(LocalDate start, LocalDate end) {
		ArrayList<Employee> empSelected = new ArrayList<>();
		String select = "SELECT * FROM payroll_service WHERE START_DATE BETWEEN ? AND ?";
		String sDate = String.valueOf(start);
		String eDate = String.valueOf(end);
		try {
			preparedStatement = connection.prepareStatement(select);
			preparedStatement.setString(1, sDate);
			preparedStatement.setString(2, eDate);
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

				empSelected.add(employee);
			}
			for (Employee employee : empSelected) {
				System.out.println(employee);
			}

		} catch (SQLException e) {
			throw new EmployeeException("Invalid date");
		}
	}
	
	public void calculate() {
		Scanner sc = new Scanner(System.in);

		final int EXIT = 6;
		int choice = 0;
		while (choice != EXIT) {
			System.out.println("enter your choice\n1. SUM\n2. AVG\n3. MIN\n4. MAX  \n5.COUNT\n6.EXIT\n");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				calculateQuery("SELECT GENDER, SUM(basic_pay) FROM payroll_service GROUP BY GENDER");
				break;

			case 2:
				calculateQuery("SELECT GENDER, AVG(basic_pay) FROM payroll_service GROUP BY GENDER");
				break;

			case 3:
				calculateQuery("SELECT GENDER, MIN(basic_pay) FROM payroll_service GROUP BY GENDER");
				break;
			case 4:
				calculateQuery("SELECT GENDER, MAX(basic_pay) FROM payroll_service GROUP BY GENDER");
				break;
			case 5:
				calculateQuery("SELECT GENDER, COUNT(basic_pay) FROM payroll_service GROUP BY GENDER");
				break;
			}
		}
	}

	public void calculateQuery(String calculate) {
		List<Employee> result = new ArrayList<Employee>();

		try {
			preparedStatement = connection.prepareStatement(calculate);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Employee employee = new Employee();
				employee.setGENDER(resultSet.getString(1));
				employee.setBasic_pay(resultSet.getDouble(2));

				result.add(employee);
			}
			if (calculate.contains("COUNT")) {
				for (Employee i : result) {
					System.out.println("GENDER: " + i.getGENDER() + " COUNT: " + i.getBasic_pay());
				}
			} else {
				for (Employee i : result) {
					System.out.println("GENDER: " + i.getGENDER() + " Basic pay: " + i.getBasic_pay());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}