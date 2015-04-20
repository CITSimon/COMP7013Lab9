import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBConnectionTester 
{

	public static void main(String[] args)
	{
		Connection conn = null;
		ArrayList<AutoCloseable> dbObjects = new ArrayList<AutoCloseable>();
		try
		{			
			//Setup the driver. This instantiates
			//the Driver class which is in the mySQL jar file.
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://db4free.net:3306/simondb?"+"user=simonlong&password=password");
			dbObjects.add(conn);
			
			System.out.println("Connection Successful!!");
			
			//Create a PreparedStatment (name, 
			PreparedStatement insertPlayerStatement = 
					conn.prepareStatement("INSERT into simondb.PLAYERS values (?,?,?)");
			dbObjects.add(insertPlayerStatement);
			
			insertPlayerStatement.setString(1, "john");
			insertPlayerStatement.setInt(2, 25);
			insertPlayerStatement.setInt(3, 7);
			
			insertPlayerStatement.executeUpdate();
			System.out.println("Update statement executed!!");
		}
		catch(ClassNotFoundException cnf)
		{
			System.out.println(cnf.getMessage());
		}
		catch(SQLException sqlEx)
		{
			System.out.println(sqlEx.getMessage());
		}
		
		//Clean up of connections after we are finished doing 
		//database stuff.
		for(AutoCloseable currDbObject : dbObjects)
		{
			try
			{
				currDbObject.close();
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
	}
	
}
