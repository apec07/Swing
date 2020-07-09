// Utility for Account
import java.io.*;
import java.util.*;
import java.sql.*;

public class Utility {

	public static boolean writeToFile (String pathName,Account account){
		try {
		FileOutputStream fos = new FileOutputStream (pathName);
		ObjectOutputStream oos = new ObjectOutputStream (fos);
		oos.writeObject (account);
		oos.flush();
		oos.close();
		fos.close();
		} catch (IOException ex) {
		System.err.println ("Trouble writing - "+ex);
		return false;
	  }
		
		return true;
	}
	
	public static Account readFromAccount(String pathName){
		Account account;
		try {
		FileInputStream fis = new FileInputStream (pathName);
		ObjectInputStream ois = new ObjectInputStream (fis);
		account = (Account)(ois.readObject());
		ois.close();
		fis.close();
		} catch (IOException ex) {
		System.err.println ("Trouble reading - "+ex);
		return null;
	  } catch (ClassNotFoundException ex){
	  System.err.println ("Trouble Casing - "+ex);
		return null;
		}
		return account;
	}

	public static boolean MySQLConnect(){
		 try {
            Class.forName("com.mysql.jdbc.Driver"); 
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
	}
	
	public static List<Account> readFromMySQL(){
		
		List<Account> mList = new ArrayList<>();
		
		 try {
        		Properties prop = new Properties();
            prop.load(new FileInputStream("JDBC.properties"));
            Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/db_morgan?"+"autoReconnect=true&useSSL=false", prop);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from account");

            while (rs.next()) {
            		Account account = new Account();
                int _id = rs.getInt(1);
                String user = rs.getString(2);
                String password = rs.getString(3);

                System.out.print(" _ID= " + _id);
                System.out.print(" User= " + user);
                System.out.print(" Password= " + password);
                System.out.print("\n");
                account.setUser(user);
                account.setPassword(password);
                mList.add(account);
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return null;
        }
        
        return mList;
	}
	
	public static int WriteToMySQL(Account account){
		int updateNum;
		String user = account.getUser();
		String password = account.getPassword();
		System.out.println("WriteToMySQL "+ account);
		if (user==null || user.trim().length()==0){
			System.out.println("WriteToMySQL return");
			return 0;
		}
		
		String sqlUrl = "INSERT INTO ACCOUNT (USER,PASSWORD) VALUES (\""+user+"\",\""+password+"\");";
		
		try {
        		Properties prop = new Properties();
            prop.load(new FileInputStream("JDBC.properties"));
            Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/db_morgan?"+"autoReconnect=true&useSSL=false", prop);
            Statement stmt = con.createStatement();
            updateNum = stmt.executeUpdate(sqlUrl);
         
            stmt.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("SQLException: " + ex.getMessage());
            return 0;
        }
		return updateNum;
	}

}