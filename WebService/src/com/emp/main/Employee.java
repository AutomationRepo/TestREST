package com.emp.main;

import java.util.Comparator;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Employee Object
 * @author Shweta
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="employee")
public class Employee {
	
	@XmlElement(name="empID")
	private String empID;
	
	@XmlElement(name="empName")
	private String name;
	
	@XmlElement(name="empMail")
	private String email;
	
	/**
	 * Get employee id
	 * @return
	 */
	public String getEmpID() {		
		return empID;
	}
	
	/**
	 * Set employee Id
	 * @param empID
	 */
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	
	/**
	 * Get employee name
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * set employee name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get employee email
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set employee email
	 * @return
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	@SuppressWarnings("unused")
	public boolean equals(Employee emp){
		if(emp!=null || this!=null)
			return (emp.getEmpID().equals(this.getEmpID()) && emp.getName().equals(this.getName()) && emp.getEmail().equals(this.getEmail()));
		return false;
	}
	
	
}
