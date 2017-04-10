package com.emp.main;

import java.util.ArrayList;

import com.emp.utility.ReadDB;

public class EmployeDB {

	private static ArrayList<Employee> empDB = new ArrayList<Employee>();
	
	/**
	 * Initialize database with given employee details
	 */
	static {		
		empDB.add(addEmp(ReadDB.getValue("./db/empDBO.properties", "EMP1")));
		empDB.add(addEmp(ReadDB.getValue("./db/empDBO.properties", "EMP2")));
		empDB.add(addEmp(ReadDB.getValue("./db/empDBO.properties", "EMP3")));
	}
	
	/**
	 * Return database
	 * @return
	 */
	protected ArrayList<Employee> loadDB(){
		return empDB;
	}
	
	/**
	 * Create employee object with given input to be added into database
	 * @param employee
	 * @return
	 */
	private static Employee addEmp(String employee){
		String[] empInfo = employee.split(",");
		
		Employee emp = new Employee();
		emp.setEmpID(empInfo[0]);
		emp.setName(empInfo[1]);
		emp.setEmail(empInfo[2]);
	
		return emp;
	}
		
}
