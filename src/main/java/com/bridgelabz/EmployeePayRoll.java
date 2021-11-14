package com.bridgelabz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeePayRoll {
	public static void main(String[] args) {
        String FETCH = "SELECT * FROM payroll_service";
        ArrayList<Employee> empList = new ArrayList<Employee>();
        EmployeeConfig eConfig = new EmployeeConfig();
        
		PreparedStatement preparedStatement;
        try {
            preparedStatement = eConfig.getConfig().prepareStatement(FETCH);
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
            for (Employee i : empList) {
                System.out.println(i.toString());
            }
        }
		catch (SQLException e) {
			System.out.println(e);
           // throw new EmployeeException("invalid column label");
        }
    }
}
