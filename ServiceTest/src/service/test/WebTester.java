package service.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import com.emp.main.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebTester {

	private static Client client;
	private static Logger Log=null;
	
	@Rule public TestName testName = new TestName();
	
	@BeforeClass
 	public static void setUp(){		
		client = ClientBuilder.newClient();		
		PropertyConfigurator.configure("config/log4j.properties");
		Log = Logger.getRootLogger();			
	}
		
	/**
	 * Test welcome page of employee service
	 */
	@Test
	public void test1_WelcomePage(){		
		String baseURL = Utility.getPropertyValue("config/config.properties", "REST_SERVICE_URL");
		
		WebTarget wt = client.target(baseURL);
		Response objResponse = wt.request().get();
		
		Log.info("Testing - Welcome page content");
		
		if(objResponse.getStatus()!=200){
			Log.info("Failed with HTTP error code -");
			Log.debug(objResponse.getStatus());
			Assert.assertEquals(200, objResponse.getStatus());
			Log.info(testName.getMethodName() + " - Test case failed");
			throw new RuntimeException("Failed with HTTP error code - " + objResponse.getStatus());
		}			
		else{			
			String result = objResponse.readEntity(String.class);
			
			Assert.assertEquals(true,result.contains("This is Employee Service..!!!"));
			Log.info(testName.getMethodName() + " - Test case passed");
		}						
	}
	
	/**
	 * Verify if service returns list of employees contains three employees initially
	 */
	@Test
	public void test2_GetAllEployees_Initial(){		
		String baseURL = Utility.getPropertyValue("config/config.properties", "GET_EMP_ALL");
		
		WebTarget wt = client.target(baseURL);
		Response objResponse = wt.request().accept("application/xml").get();
		
		Log.info("Testing -if employee list is empty initially");
		
		if(objResponse.getStatus()!=200){
			Log.info("Failed with HTTP error code -");
			Log.debug(objResponse.getStatus());
			Assert.assertEquals(200, objResponse.getStatus());
			Log.info(testName.getMethodName() + " - Test case failed");
			throw new RuntimeException("Failed with HTTP error code - " + objResponse.getStatus());
		}			
		else{			
			GenericType<ArrayList<Employee>> list = new GenericType<ArrayList<Employee>>() {};
			ArrayList<Employee> emps  = objResponse.readEntity(list);
			
			Assert.assertEquals(3,emps.size());
			Log.info(testName.getMethodName() + " - Test case passed");
		}						
	}

	/**
	 * Verify if service uses http POST to create employee
	 */
	@Test
	public void test3_CreateEmployee(){		
		Employee createEmp = new Employee();		
		createEmp.setEmpID("4");
		createEmp.setEmail("Fourth@email.com");
		createEmp.setName("Fourth");
		
		String createURL = Utility.getPropertyValue("config/config.properties", "CREATE_EMP");
		
		WebTarget wt = client.target(createURL);
		Response objResponse = wt.request("application/xml").post(Entity.entity(createEmp, MediaType.APPLICATION_XML));
					
		Log.info("Testing-Create employee with HTTP POST");	
		
		if(objResponse.getStatus()!=200){
			Log.info("Failed with HTTP error code -");
			Log.debug(objResponse.getStatus());
			Assert.assertEquals(200, objResponse.getStatus());
			Log.info(testName.getMethodName() + " - Test case failed");
			throw new RuntimeException("Failed with HTTP error code - " + objResponse.getStatus());
		}			
		else{
			Employee res = objResponse.readEntity(Employee.class);
			
			Assert.assertTrue(createEmp.equals(res));
			Log.info(testName.getMethodName() + " - Test case passed");
		}						
	}

	/**
	 * Verify if service uses http get to retrieve employee info by empID
	 */
	@Test
	public void test4_GetEmployeeByID(){
		String empID = Utility.getPropertyValue("config/config.properties", "IP_GET_ID");
		String getURL = Utility.getPropertyValue("config/config.properties", "GET_EMP");
		
		WebTarget wt = client.target(getURL);
		Response objResponse = wt.path(empID).request().accept("application/xml").get();	
		
		Log.info("Testing -Get employee with HTTP GET");
		
		if(objResponse.getStatus()!=200){
			Log.info("Failed with HTTP error code -");
			Log.debug(objResponse.getStatus());
			Assert.assertEquals(200, objResponse.getStatus());
			Log.info(testName.getMethodName() + " - Test case failed");
			throw new RuntimeException("Failed with HTTP error code - " + objResponse.getStatus());	
		}			
		else{
			Employee emp = objResponse.readEntity(Employee.class);	
			
			Assert.assertEquals(empID,emp.getEmpID());
			Log.info(testName.getMethodName() + " - Test case passed");
		}			
	}
	
	/**
	 * Verify if services uses HTTP put to update employee name
	 */
	@Test
	public void test5_UpdateEmployeeByID(){
		String empID = Utility.getPropertyValue("config/config.properties", "IP_UPADTE_ID");
		String empName = Utility.getPropertyValue("config/config.properties", "IP_UPDATE_NAME");
		String updateURL = Utility.getPropertyValue("config/config.properties", "UPDATE_EMP");
		
		WebTarget objWT = client.target(updateURL);
		Response objResponse = objWT.path(empID).path(empName).request("application/xml").put(Entity.text(""));
		
		Log.info("Testing - Update employee name with HTTP PUT");
		
		if(objResponse.getStatus()!=200){
			Log.debug(objResponse.getStatus());
			Log.info(testName.getMethodName() + " - Test case failed");
			throw new RuntimeException("Failed with HTTP error code - " + objResponse.getStatus());			
		}
		else{				
			Employee resEmp = objResponse.readEntity(Employee.class);
			Assert.assertEquals(empName,resEmp.getName());
			Log.info(testName.getMethodName() + " - Test case passed");
		}		
	}
	
	/**
	 * Verify if service uses HTTP delete to delete employee
	 */
	@Test
	public void test6_DeleteEmployeeByID(){
		String empID = Utility.getPropertyValue("config/config.properties", "IP_DELETE_ID");
		String deleteURL = Utility.getPropertyValue("config/config.properties", "DELETE_EMP");
		
		WebTarget objWT = client.target(deleteURL);
		Response objResponse = objWT.path(empID).request("application/xml").delete();
		
		Log.info("Testing - Delete employee name with HTTP DELETE");	
		
		if(objResponse.getStatus()!=200){
			Log.info("Failed with HTTP error code -");
			Log.debug(objResponse.getStatus());
			Log.info(testName.getMethodName() + " - Test case failed");
			throw new RuntimeException("Failed with HTTP error code - " + objResponse.getStatus());		
		}
		else{			
			String result = objResponse.readEntity(String.class);
			
			Assert.assertEquals(result,"employee deleted successfully");
			Log.info(testName.getMethodName() + " - Test case passed");
		}
	}
	
	@AfterClass
	public static void tearDown(){
	
	}
	
}
