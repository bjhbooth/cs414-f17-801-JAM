package edu.colostate.cs.cs414.a3.jam.db;

import java.lang.Exception;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import edu.colostate.cs.cs414.a3.jam.business.GMSBO;
import edu.colostate.cs.cs414.a3.jam.business.EquipmentBO;

// Perform database specific operations for EquipmentBO data. 
public class EquipmentDB extends BaseDB implements DAO<EquipmentBO>
{
	// Private method used by methods of this class to create an EquipmentBO
	// object from the current result indexed in a result set
	private EquipmentBO buildEquipmentObject(ResultSet rs)
	{
		String name = null;
		int quantity = 0;
		byte[] byteArr = null;
		
		try  
		{
			name = rs.getString("Name");
			quantity = rs.getInt("Quantity");
			
			// Retrieve the image as a byte[].  
			// The view (if a GUI) can convert this to a BufferedImage.  
			// Not done here so that the model / business logic does
			// not know about windows (should the UI be a console that 
			// just wants to write out to a file, etc.
			byteArr = rs.getBytes("Picture");

			//Blob blob = rs.getBlob("Picture");
			//byteArr = blob.getBytes(1, (int)blob.length());  // 1 = starting position
		} catch (Exception e)  {
			// Error reading byte array.  Leave it null.  
		} 

		EquipmentBO equipment = new EquipmentBO(name, byteArr, quantity);
		return equipment;
		
	}  // end buildEquipmentObject

	// ******************************************************************************************
	//
	// Implement method of DAO interface.  Retrieve all TrainerBO objects in the DB.
	//
	// ******************************************************************************************
	
	// --------------------------------------------
	// Retrieve a single EqiupmentBO with the specified name (key).
	//
	// Map(DATA) - EuqipmentBO 
	// Map(VALID) - Booealn.TRUE if success
	// Map(<Attributes>) - Message for each field with error (if any).
	//     ex:  Map.put(TrainerBO.TrainerAttribute.HOURS, "Error:  Expected value > 0.")
	// --------------------------------------------
	public Map<String, Object> get(String name)
	{
		Hashtable<String, Object> results = new Hashtable<String, Object>();

		// Be sure valid search criteria.
		if ( (name == null) || (name.length() == 0) )
		{
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			results.put(EquipmentBO.EquipmentAttribute.NAME.toString(), "Equipment name required.");
			// Don't add null for DATA
		}
		
		String sql = "SELECT Name, Picture, Quantity FROM Equipment WHERE Name = '" + name + "'";
		
		System.out.println("EquipmentDB.get(" + name + ") - - sql =" + sql);
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			ps = getPreparedStatement(sql);
			rs = ps.executeQuery();

			// Create an EquipmentBO for the object in the result set and add it to the Hashtable to return.
			if (rs.next())
			{	
				EquipmentBO equipment = buildEquipmentObject(rs);
				results.put(GMSBO.Result.DATA.toString(), equipment);
				results.put(GMSBO.Result.VALID.toString(), Boolean.TRUE);
				rs.close();
				ps.close();
				return results;
			} 
			else 
			{
				results.put(EquipmentBO.EquipmentAttribute.NAME.toString(), "Equipment " + name + " not found.");
				results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
				// Don't add null TrainerBO to Hashtable
				rs.close();
				ps.close();
				return results;
			}
			
		} catch (SQLException e) {
			results.put(GMSBO.Result.UNKNOWN.toString(), "Error while retrieveing equipment " + name + "\n" + e.getMessage());
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			// Don't add null object to Hashtable. 
			return results;
		}  // end catch 
	
	}  // end get(String name)

	
	// --------------------------------------------
	// Returns all EquipmentBO objects.
	//
	// Map(DATA) - List of EqiupmentBO 
	// Map(VALID) - Booealn.TRUE if success
	// Map(<Attributes>) - Message for each field with error (if any).
	//     ex:  Map.get(TrainerBO.TrainerAttribute.HOURS) / "Error:  Expected value > 0."
	// --------------------------------------------
	public Map<String, Object> getAll()
	{
		Hashtable<String, Object> results = new Hashtable<String, Object>();

		String sql = "SELECT Name, Picture, Quantity FROM Equipment ORDER BY Name";
		
		System.out.println("EquipmentDB.getAll() - - sql =" + sql);
		
		List<EquipmentBO> equipment = new ArrayList<>();
		ResultSet rs = null;
		try (PreparedStatement ps = getPreparedStatement(sql))
		{
			rs = ps.executeQuery();
			// For each row of the result set create an EquipmentBO and  
			// add it to the List to be returned
			EquipmentBO curEquipment;
			while (rs.next())
			{
				curEquipment = buildEquipmentObject(rs);
				equipment.add(curEquipment);
			}  // end while
			rs.close();			
		} catch (SQLException e) {
			results.put(GMSBO.Result.UNKNOWN.toString(), "Unable to retrieve data.\n" + e.getMessage());
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			equipment.clear();
			results.put(GMSBO.Result.DATA.toString(), equipment);
			return results;
		}  // end catch 

		results.put(GMSBO.Result.VALID.toString(), Boolean.TRUE);
		results.put(GMSBO.Result.DATA.toString(), equipment);

		return results;

	}  // end getAll


	// --------------------------------------------
	// Add a single single EqiupmentBO with the specified (unique) name.
    // 
    // This includes storing an image of the equipment which requires the following PreparedStatement methods
    // 1.  public void setBinaryStream(int paramIndex,InputStream stream)  throws SQLException
    // 2.  public void setBinaryStream(int paramIndex,InputStream stream,long length) throws SQLException 
	//  
	// Return 
	// Map(DATA) - The saved EquipmentBO 
	// Map(VALID) - Booealn.TRUE if success
	// Map(<Attributes>) - Message for each field with error (if any).
	//     ex:  Map.get(TrainerBO.TrainerAttribute.HOURS) / "Error:  Expected value > 0."
	// --------------------------------------------
	public Map<String, Object> add(EquipmentBO equipment)
	{
		Hashtable<String, Object> results = new Hashtable<String, Object>();
		
		// Be sure valid search criteria.
		if ( (equipment == null) || (equipment.getName() == null) || (equipment.getName().length() == 0) )
		{
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			results.put(EquipmentBO.EquipmentAttribute.NAME.toString(), "Equipment name not provided.");
			// Don't add null for DATA
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		
		// Attempt to save. 
		try 
		{
			// Base class will establish a connection with the database
			// which it maintains as an attribute.   

			// ------------------------------------------
			// If key is not unique will throw an exception.
			// If that is the case, check with a query if the 
			// ID is a DUPLICATE.  Don't use getErrorID since 
			// vendor specific.  Assume not a duplicate for now. 
			// ------------------------------------------

			// Get a prepared statement with the insert statement. 
			sql = "INSERT INTO Equipment VALUES (?, ?, ?)";
			ps = getPreparedStatement(sql);
			
			// Set the three parameters - - Name, Picture, Quantity
            ps.setString(1, equipment.getName());
            ps.setBytes(2, equipment.getImage());
            ps.setInt(3, equipment.getQuantity());
            
			System.out.println("Executing sql TrainerBO.add = " + sql);
			
            // ----------------------
            // Execute the insert
            // ----------------------
			ps.executeUpdate();
			ps.close();
		} catch(Exception eOuter)  {		
		  
			// ------------------------------------------
			// An exception occurred.  getErrorCode is vendor 
			// specific, so just do a query to determine 
			// if a row already exists with that primary key.  
			// ------------------------------------------

			try {
				if (ps != null)
					ps.close();
				
				sql = "SELECT Name from Equipment WHERE Name = '" + equipment.getName() + "'";
				System.out.println("Checking for dup with " + sql);
				PreparedStatement ps2 = getPreparedStatement(sql);
				System.out.println("Got prepared statement... going to execute");
				rs = ps2.executeQuery();
				if (rs.next())
				{
				 	//  DUPLICATE ENTRY - A row already exists with that unique key.
					System.out.println("Duplicate entry!");
					results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
					results.put(EquipmentBO.EquipmentAttribute.NAME.toString(), "Name already exists.");
					if (equipment != null)
						results.put(GMSBO.Result.DATA.toString(), equipment);

					return results;
				}
			} catch (Exception eInner) {
					results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
					results.put(GMSBO.Result.UNKNOWN.toString(), "ERROR:  Unable to save equipment information.\n" + eInner.toString());
					if (equipment != null)
						results.put(GMSBO.Result.DATA.toString(), equipment);
					return results;
			}  // end inner try/catch
			
				System.out.println("Insert failed for reason other than duplicate entry.");
				results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
				results.put(GMSBO.Result.UNKNOWN.toString(), "ERROR:  Unable to save trainer information.\n" + eOuter.toString());
				if (equipment != null)
					results.put(GMSBO.Result.DATA.toString(), equipment);

				return results;			
		}  // end outer try / catch 
		
		// Save was successful.
		System.out.println("Image stored successfully.");
		results.put(GMSBO.Result.VALID.toString(), Boolean.TRUE);
		results.put(GMSBO.Result.DATA.toString(), equipment);
		
		return results;
	}  // end add(EqiupmentBO)

	
	// --------------------------------------------
	// Update the specified EquipmentBO
	//
	// Map(DATA) - The saved EqupmentBO 
	// Map(VALID) - Booealn.TRUE if success
	// Map(<Attributes>) - Message for each field with error (if any).
	//     ex:  Map.get(TrainerBO.TrainerAttribute.HOURS) / "Error:  Expected value > 0."
	// --------------------------------------------	
	public Map<String, Object> update(EquipmentBO equipment) 
	{
		Hashtable<String,Object> results = new Hashtable<String, Object>();
		
		// Be sure valid criteria to identify Equpment to update.
		if ( (equipment == null) || (equipment.getName() == null) || (equipment.getName().length() == 0) )
		{
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			results.put(EquipmentBO.EquipmentAttribute.NAME.toString(), "Equipment name not provided.");
			// Don't add null for DATA
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		
		// Attempt to update. 
		try 
		{
			// Base class will establish a connection with the database
			// which it maintains as an attribute.   

			// Check for the existence of the object. 
			sql = "SELECT Name FROM Equipment WHERE Name = '" + equipment.getName() + "'";
			ps = getPreparedStatement(sql);
			rs = ps.executeQuery();
			// Check for a result.
			if (!rs.next())
			{
				results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
				results.put(EquipmentBO.EquipmentAttribute.NAME.toString(), "Equipment does not exist.  Unable to complete update.");
				// Don't add null for DATA
				return results;
			}  // end if
			
			// Get a prepared statement to update the database.  
			sql = "UPDATE Equipment SET Image = ?, Quantity = ? WHERE Name = '" + equipment.getName() + "'";

			System.out.println("Update about to execute " + sql);
			
			ps = getPreparedStatement(sql);
			
			// Set the three parameters - - Name, Picture, Quantity
            ps.setBytes(1, equipment.getImage());
            ps.setInt(2, equipment.getQuantity());
            
			System.out.println("Executing sql EquipmmentBO.add = " + sql);

			// Execute the update. 
			ps.executeUpdate();
			ps.close();
			
		} catch(Exception eOuter)  {		
		
			System.out.println("Update failed - exception .");
			results.put(GMSBO.Result.VALID.toString(), Boolean.FALSE);
			if (equipment != null)
				results.put(GMSBO.Result.DATA.toString(), equipment);
			results.put(GMSBO.Result.UNKNOWN.toString(), "UNKNOWN:  Unable to update database.  " + eOuter.getMessage());

			return results;			
		}  // end outer try / catch 
		
		// Update was successful.
		System.out.println("Image stored successfully.");
		results.put(GMSBO.Result.VALID.toString(), Boolean.TRUE);
		results.put(GMSBO.Result.DATA.toString(), equipment);
		
		return results;
	}  // end update(EqiupmentBO)

	
	// --------------------------------------------
	// Delete the specified EquipmentBO
	//
	// Return boolean true on success.
	// -----------------------------------------
	public boolean delete(EquipmentBO eBO) 
	{
		if (eBO == null)
			return false;
		
		return delete (eBO.getName());
		
	}  // delete (equipmentBO)

	// --------------------------------------------
	// Delete the equipment specified by String name
	//
	// Return boolean true on success.
	// -----------------------------------------
	public boolean delete(String key)
	{
		if ( (key == null) || (key.length() == 0) )
			return false;

		try {		
			String sql = "DELETE FROM Equipment WHERE Name = '?'";
			
			System.out.println("Delete executing - - " + sql);
			
			PreparedStatement ps = getPreparedStatement(sql);
			ps.setString(1, key);		
			ps.executeUpdate();

		} catch (SQLException e) {
			return false;
		}

		// Success!
		return true; 
	
	}  // delete (string)
	
}  // end class EquipmentDB
