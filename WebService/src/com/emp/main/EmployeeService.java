package com.emp.main;

import javax.ws.rs.PathParam;

import java.util.ArrayList;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Employee service allows to manage all employees
 * @author Shweta
 *
 */
@Path("/employee")
@Produces(MediaType.APPLICATION_XML)
public class EmployeeService {
	
	/**
	 * Get all employee information
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
 	public ArrayList<Employee> getAllEmployee(){
		return loadEmpDB();
	}
	
	/**
	 * Find employee by employee id
	 * @param empID
	 * @return
	 */
	@GET
	@Path("/get/{empId}")
	@Produces(MediaType.APPLICATION_XML)
	public Employee getEmployee(@PathParam("empId") String empID){
		for(Employee emp:loadEmpDB()){
			if(emp.getEmpID().equals(empID))
				return emp;
		}	
		throw new NotFoundException("Employee "+ empID +" is not found");
	}
	
	/**
	 * Create new employee and automatically assigns unique employee ID
	 * @param emp
	 * @return
	 */	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Employee createEmployee(Employee emp){
		//emp.setEmpID(UUID.randomUUID().toString());
		loadEmpDB().add(emp);
		return emp;		
	}
	
	/**
	 * Find employee by ID and update his name
	 * @param empID
	 * @param empName
	 * @return
	 */	
	@PUT
	@Path("/update/{empID}/{empName}")		
	@Produces(MediaType.APPLICATION_XML)
	public Employee updateEmployee(@PathParam("empID") String empID, @PathParam("empName") String empName){
		for(Employee tmp : loadEmpDB()){
			if(tmp.getEmpID().equals(empID)){			
				tmp.setName(empName);
				return tmp;
			}				
		}
		throw new NotFoundException("Employee "+ 1 +" is not found");
	}
	
	/*@PUT
	@Path("/update/{empID}")	
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Employee updateEmployee(@PathParam("empID") String empID, Employee emp){
		for(Employee tmp : empDB){
			if(tmp.getEmpID().equals(empID)){
				tmp.setEmail(emp.getEmail());
				tmp.setEmpID(emp.getEmpID());
				tmp.setName(emp.getName());
				return emp;
			}				
		}
		throw new NotFoundException("Employee "+ 1 +" is not found");
	}*/
	
	/**
	 * Find employee by ID and delete his record
	 * @param empID
	 * @return
	 */
	@DELETE
	@Path("/delete/{empId}")
	@Produces(MediaType.APPLICATION_XML)
	public String deleteEmployee(@PathParam("empId") String empID){
		for(Employee emp : loadEmpDB()){
			if(emp.getEmpID().equals(empID)){
				int index = loadEmpDB().indexOf(emp);
				loadEmpDB().remove(index);
				return "employee deleted successfully";
			}				
		}
		throw new NotFoundException("Employee "+ empID +" is not found");
	}
	
	protected ArrayList<Employee> loadEmpDB(){	
		EmployeDB dbo = new EmployeDB();
		return dbo.loadDB();		
	}
	
	
}
