package edu.colostate.cs.cs414.a3.jam.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.colostate.cs.cs414.a3.jam.business.CustomerBO;
import edu.colostate.cs.cs414.a3.jam.business.GMSBO;

//Perform database specific operations for TrainerBO objects. 
public class CustomerDB extends BaseDB implements DAO<CustomerBO>
{

	// Private method to create a CustomerBO object from the current result indexed in a result set
	private CustomerBO buildCustomerObject(ResultSet rs) throws SQLException
	{
		String customerID = rs.getString("CustomerID");

		String status = rs.getString("MemberStatus");
		MemberStatus eStatus = CustomerBO.MemberStatus.INACTIVE;
		if (status != null) 
		{
			if ( status.equals(CustomerBO.MemberStatus.ACTIVE.toString()) )
				eStatus = CustomerBO.MemberStatus.ACTIVE;
			else 
				eStatus = CustomerBO.MemberStatus.INACTIVE;  // Anything else is inactive
		}
			
		String firstName = rs.getString("FirstName");
		String lastName = rs.getString("LastName");
		String phone = rs.getString("Phone");
		String email = rs.getString("Email");
		String insProvider = rs.getString("InsuranceProvider");
		String street = rs.getString("Street");
		String city = rs.getString("City");
		String state = rs.getString("State");
		String zip = rs.getString("Zip");
		
		CustomerBO customer = new CustomerBO (customerID, eStatus, firstName, lastName, phone, email, insProvider, street, city, state, zip);
		
		return customer;
		
	}  // end buildTrainerObject

	
	
	
	
	FIX - 
	
	Add 
	update 
	Delet  obj
	delete string
	
	
	
	
	
	
	
	
	
	
	
	// ******************************************************************************************
	//
	// Implement methods of DAO interface.  Retrieve all CustomerBO objects in the DB.
	//
	// ******************************************************************************************
	
	// --------------------------------------------
	// Retrieve a single CustomerBO with the specified customer id.
	//
	// Map(DATA) - CustomerBO 
	// Map(VALID) - Booealn.TRUE if success
	// Map(<Attributes>) - Message for each field with error (if any).
	//     ex:  Map.get(CustomerBO.CustomerAttribute.ID) / "Error:  Duplicate customer ID."
	// --------------------------------------------
	public Map<String, Object> get(String customerID)
	{
		Hashtable<String, Object> results = new Hashtable<String, Object>();

		// Be sure valid search criteria.
		if ( (customerID == null) || (customerID.length() == 0) )
		{
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			results.put(CustomerBO.CustomerAttribute.ID.toString(), "Customer ID not provided.");
			// Don't add null for DATA
		}
		
		String sql = "SELECT Customers.CustomerID, Customer.MemberStatus, Person.FirstName, Person.LastName, Person.Phone, Person.email, Person.InsuranceProvider, "
				   +         "Addresses.Street, Addresses.City, Addresses.State, Addresses.Zip "
				   + "FROM   Customers, Person, Addresses "
				   + "WHERE Customers.CustomerID = '" + customerID + "' AND Customers.PersonID = Person.PersonID AND Addresses.PersonID = Person.PersonID"; 
		
		System.out.println("CustomerDB.get executing " + sql);
		
		try (PreparedStatement ps = getPreparedStatement(sql))
		{
			ResultSet rs = ps.executeQuery();
			// Create a CustomerBO for the object in the result set and add it to the Hashtable to return. 
			if (rs.next())
			{	
				CustomerBO customer = buildCustomerObject(rs); 
				results.put(GMSBO.Result.DATA.toString(), customer);
				results.put(GMSBO.Result.VALID.toString(), Boolean.TRUE);
				rs.close();
				return results;
			} 
			else 
			{
				results.put(CustomerBO.CustomerAttribute.ID.toString(), "Customer " + customerID + " not found.");
				results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
				// Don't add null TrainerBO to Hashtable
				rs.close();
				return results;
			}
			
		} catch (SQLException e) {
				results.put(GMSBO.Result.UNKNOWN.toString(), "Error while retrieveing customer " + customerID + "\n" + e.getMessage());
				results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
				// Don't add null object to Hashtable. 
				return results;
		}  // end catch 

	}  // end get(String customerID)

	
	
	// --------------------------------------------
	// Returns all CustomerBO objects.
	//
	// Map(DATA) - List of CustomerBO 
	// Map(VALID) - Booealn.TRUE if success
	// Map(<Attributes>) - Message for each field with error (if any).
	//     ex:  Map.get(TrainerBO.TrainerAttribute.HOURS) / "Error:  Expected value > 0."
	// --------------------------------------------
	public Map<String, Object> getAll()
	{
		String sql = "SELECT Customers.CustomerID, Customer.MemberStatus, Person.FirstName, Person.LastName, Person.Phone, Person.email, Person.InsuranceProvider, "
				   +         "Addresses.Street, Addresses.City, Addresses.State, Addresses.Zip "
				   + "FROM   Customers, Person, Addresses "
				   + "WHERE Customers.PersonID = Person.PersonID AND Addresses.PersonID = Person.PersonID"
				   + "ORDER BY Person.LastName, Person.FirstName"; 

		System.out.println("TrainerDB.getAll executing " + sql);
		

		Hashtable<String,Object> results = new Hashtable<String,Object>();
		List<CustomerBO> customers = new ArrayList<>();
		ResultSet rs = null;
		
		try (PreparedStatement ps = getPreparedStatement(sql))
		{
			rs = ps.executeQuery();
			
			// For each row of the result set create a TrainerBO and add it to the ResultSet
			CustomerBO curCustomer;
			while (rs.next())
			{
				curCustomer = buildCustomerObject(rs);
				customers.add(curCustomer);
			}  // end while
			rs.close();			
		} catch (SQLException e) {
			results.put(GMSBO.Result.UNKNOWN.toString(), "Unable to retrieve data.\n" + e.getMessage());
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			customers.clear();
			results.put(GMSBO.Result.DATA.toString(), customers);
			return results;
		}  // end catch 

		results.put(GMSBO.Result.VALID.toString(), Boolean.TRUE);
		results.put(GMSBO.Result.DATA.toString(), customers);

		return results;
	}  // end getAll


	// --------------------------------------------
	// Add a single single TrainerBO with the specified (unique) name.
	//
	// Map(DATA) - The saved TrainerBO 
	// Map(VALID) - Booealn.TRUE if success
	// Map(<Attributes>) - Message for each field with error (if any).
	//     ex:  Map.get(TrainerBO.TrainerAttribute.HOURS) / "Error:  Expected value > 0."
	// --------------------------------------------
	public Map<String, Object> add(TrainerBO trainer)
	{
		Hashtable<String, Object> results = new Hashtable<String, Object>();
		
		// Be sure valid search criteria.
		if ( (trainer == null) || (trainer.getEmployeeID() == null) || (trainer.getEmployeeID().length() == 0) )
		{
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			results.put(EmployeeBO.EmployeeAttribute.ID.toString(), "Employee ID not provided.");
			// Don't add null for DATA
		}

		PreparedStatement ps = null;
		String sql = null; 
		
		// Key provided.  Attempt to save.  Watch for duplicate. 
		try 
		{
			// Base class will establish a connection with the database
			// which it maintains as an attribute. 
			
			// ------------------------------------------
			// If key is not unique will throw an exception.
			// If that is the case, check with a query if the 
			// ID is a duplicate.  Don't use getErrorID since 
			// vendor specific.  Assume not a duplicate for now. 
			// ------------------------------------------
			
			// Get a prepared statement with the insert statement. 
			sql = "INSERT INTO Person (FirstName, LastName, Phone, email, InsuranceProvider) VALUES (?, ?, ?, ?, ?)";
//			Connection c = getConnection();
//			PreparedStatement ps = c.prepareStatement(sql);
			ps = getPreparedStatement(sql);
			// Set the five parameters - - First, Last, Phone, Email, Insurance
			ps.setString(1, trainer.getFirstName());
			ps.setString(2, trainer.getLastName());
			ps.setString(3, trainer.getPhone());
			ps.setString(4,  trainer.getEmail());
			ps.setString(5,  trainer.getInsuranceProvider());

			System.out.println("Executing sql TrainerBO.add = " + sql);
			// Execute the insert
			ps.executeUpdate();
			
			System.out.println("Done");
			
			// Get the key of the primary key generated for hte Person table
			ResultSet rs = ps.getGeneratedKeys();
			int personID;
			if (rs.next()) 
			{
				personID = rs.getInt(1);
				System.out.println("Person object created for trainer ADD = " + personID);
			}
			else
			{
				results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
				results.put(GMSBO.Result.DATA.toString(), trainer);
				results.put(GMSBO.Result.UNKNOWN.toString(), "UNKNOWN:  Unable to update database.");
				return results;
			}	
			

			// Update the Employee Table
			sql = "INSERT INTO Employees (EmployeeID, PersonID) VALUES (?, ?)";
			ps = getPreparedStatement(sql);
			// Set the two parameters - - employee id and person id 
			ps.setString(1, trainer.getEmployeeID());
			ps.setInt(2, personID);

			System.out.println("Executing sql TrainerBO.add = " + sql);
			// Execute the insert 
			ps.executeUpdate();
		
			System.out.println("Done");
			
			// Update the Trainer Table
			sql = "INSERT INTO Trainers (Hours, Qualificadtions, PersonID) VALUES (?, ?, ?)";
			ps = getPreparedStatement(sql);
			// Set the three parameters - - Hours, Qualifications, and Person ID
			ps.setInt(1, trainer.getHours());
			ps.setString(2, trainer.getQualifications());
			ps.setInt(3, personID);
			
			System.out.println("Executing sql TrainerBO.add = " + sql);
			// Execute the insert 
			ps.executeUpdate();
		
			System.out.println("Done");
			
			// Update the Addresses Table
			sql = "INSERT INTO Addressses (Street, City, State, Zip) VALUES (?, ?, ?, ?, ?)";
			ps = getPreparedStatement(sql);
			// Set the five parameters - - Street, CIty, State, Zip, PersonID
			Address address = trainer.getPrimaryAddress();
			ps.setString(1, address.getStreet());
			ps.setString(2, address.getCity());
			ps.setString(3, address.getState());
			ps.setString(4,  address.getZip());
			ps.setInt(5, personID);
			
			System.out.println("Executing sql TrainerBO.add = " + sql);
			// Execute the insert 
			ps.executeUpdate();

			System.out.println("Done");
			
			results.put(GMSBO.Result.VALID.toString(), Boolean.TRUE);
			results.put(GMSBO.Result.DATA.toString(), trainer);

			// Success!
			return results;
			
		} catch (Exception eOuter) {

			// ------------------------------------------
			// An exception occurred.  getErrorCode is vendor 
			// specific, so check for duplicate with a query 
			// to determine if a row already exists with that key.  
			// ------------------------------------------
			try {
				if (ps != null)
					ps.close();
				
				sql = "SELECT EmployeeID from Employees WHERE EmployeeID = '" + trainer.getEmployeeID() + "'";
				System.out.println("Checking for dup with " + sql);
				PreparedStatement ps2 = getPreparedStatement(sql);
				System.out.println("Got prepared statement... going to execute");
				ResultSet rs = ps2.executeQuery();
				if (rs.next())
				{
					// 	DUPLICATE ENTRY - A row already exists with that unique key.
					System.out.println("Duplicate entry!");
					results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
					results.put(EmployeeBO.EmployeeAttribute.ID.toString(), "Employee ID already exists.");
					if (trainer != null)
						results.put(GMSBO.Result.DATA.toString(), trainer);

					return results;
				}
			 } catch (Exception eInner) {
					results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
					results.put(GMSBO.Result.UNKNOWN.toString(), "ERROR:  Unable to save trainer information.\n" + eInner.toString());
					if (trainer != null)
						results.put(GMSBO.Result.DATA.toString(), trainer);
					return results;
			 }  // end inner try/catch
			
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			results.put(GMSBO.Result.UNKNOWN.toString(), "ERROR:  Unable to save trainer information.\n" + eOuter.toString());
			if (trainer != null)
				results.put(GMSBO.Result.DATA.toString(), trainer);

			return results;
		}  // end outer try/catch
		
	}  // end add
	

	// --------------------------------------------
	// Update the specified TrainerBO
	//
	// Map(DATA) - The saved TrainerBO 
	// Map(VALID) - Booealn.TRUE if success
	// Map(<Attributes>) - Message for each field with error (if any).
	//     ex:  Map.get(TrainerBO.TrainerAttribute.HOURS) / "Error:  Expected value > 0."
	// --------------------------------------------
	public Map<String, Object> update(TrainerBO trainer)
	{
		Hashtable<String,Object> results = new Hashtable<String, Object>();

		// Be sure valid criteria to identify Trainer to update.
		if ( (trainer == null) || (trainer.getEmployeeID() == null) || (trainer.getEmployeeID().length() == 0) )
		{
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			results.put(EmployeeBO.EmployeeAttribute.ID.toString(), "Employee ID not provided.");
			// Don't add null for DATA
		}

		try {
			
			// Check for a Trainer with the given employee ID
			String sql = "SELECT PersonID FROM Employees where Employees.EmployeeID = " + trainer.getEmployeeID();
			Connection c = getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		
			int personID;
			if (rs.next())
			{
				personID = rs.getInt(1);
			}
			else
			{
				results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
				if (trainer != null)
					results.put(GMSBO.Result.DATA.toString(), trainer);
				results.put(EmployeeBO.EmployeeAttribute.ID.toString(), "Unable to locate trainer " + trainer.getEmployeeID() + " for update.");
				return results;
			}
		
			sql = "UPDATE Person SET FirstName = ?, LastName = ?, Phone = ?, Email = ?, InsuranceProvider = ? "
				+ "WHERE PersonID = '" + personID + "'";
			ps = getPreparedStatement(sql);
			ps.setString(1, trainer.getFirstName());
			ps.setString(2, trainer.getLastName());
			ps.setString(3, trainer.getPhone());
			ps.setString(4, trainer.getEmail());
			ps.setString(5, trainer.getInsuranceProvider());
			ps.executeUpdate();

			sql = "UPDATE Trainers SET Hours = ?, Qualificatsions = ? "
				+ "WHERE PersonID = " + personID;
			ps = c.prepareStatement(sql);
			ps.setInt(1, trainer.getHours());
			ps.setString(2, trainer.getQualifications());
			ps.executeUpdate();

			sql = "UPDATE Addresses SET Street = ?, City = ?, State = ?, Zip = ? "
				+ "WHERE PersonID = " + personID;
			ps = c.prepareStatement(sql);
			Address address = trainer.getPrimaryAddress();
			ps.setString(1, address.getStreet());
			ps.setString(2, address.getCity());
			ps.setString(1, address.getState());
			ps.setString(2, address.getZip());
			ps.executeUpdate();

			// Return Success!
			results.put(GMSBO.Result.VALID.toString(), Boolean.TRUE);
			results.put(GMSBO.Result.DATA.toString(), trainer);

			return results; 
		} catch (SQLException e) {
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			if (trainer != null)
				results.put(GMSBO.Result.DATA.toString(), trainer);
			results.put(GMSBO.Result.UNKNOWN.toString(), "UNKNOWN:  Unable to update database.  " + e.getMessage());

			return results;
		}
	}  // end update

	// --------------------------------------------
	// Delete the specified TrainerBO
	//
	// Return boolean true on success. 
	// --------------------------------------------
	public boolean delete(TrainerBO trainer)
	{
		if (trainer == null)
			return false;
		
		return delete (trainer.getEmployeeID());
	}  // end delete(TrainerBO)
	
	// --------------------------------------------
	// Delete the TrainerBO identified by the EmployeeID
	// --------------------------------------------
	public boolean delete(String employeeID)
	{	
		if ( (employeeID == null) || (employeeID.length() == 0) )
				return false;
	
		try {
			// Check for a Trainer with the given employee ID
			String sql = "SELECT PersonID FROM Employees where Employees.EmployeeID = " + employeeID;
			Connection c = getConnection(); 
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		
			int personID;
			if (rs.next())
				personID = rs.getInt(1);
			else
				return false;
		
			sql = "DELETE FROM Person WHERE PersonID = ?";
			ps = c.prepareStatement(sql);
			ps.setInt(1, personID);		
			ps.executeUpdate();

			sql = "DELETE FROM Trainers WHERE PersonID = ?";
			ps = c.prepareStatement(sql);
			ps.setInt(1, personID);		
			ps.executeUpdate();

			sql = "DELETE FROM Addresses WHERE PersonID = ?";
			ps = c.prepareStatement(sql);
			ps.setInt(1, personID);		
			ps.executeUpdate();

			// update Employee Table so employee no longer references a PersonID
			sql = "UPDAETE Employees SET PersonID = NULL WHERE EMployeeID = " + employeeID;
			ps.executeUpdate();

			return true; 
		} catch (SQLException e) {
			return false;
		}
	}  // end delete


}  // end class CustomerDB
