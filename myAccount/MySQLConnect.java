/*
# SQL syntax
# Create Table    
CREATE TABLE IF NOT EXISTS ACCOUNT (
ID INT NOT NULL AUTO_INCREMENT,
USER varchar(50) NOT NULL UNIQUE,
PASSWORD varchar(50) NOT NULL,
NOTE varchar(200),
PRIMARY KEY ( ID ));


# Insert DATA into Table account
INSERT INTO ACCOUNT 
(USER,PASSWORD,NOTE) 
VALUES ("account1", "myPassword1","this is my note1");

INSERT INTO ACCOUNT 
(USER,PASSWORD,NOTE) 
VALUES ("account2", "myPassword2","this is my note2");

# Query DATA
select * from account;

# Delete DATA!
DELETE FROM account 
# where clause
DELETE FROM account where user = "account1";
DELETE FROM account where user = "account2";

# Drop Table
Drop Table Account;


*/

import java.sql.*;
import java.io.*;
import java.util.*;


class MySQLConnect {

    public static void main(String argv[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");  //驅動程式-第四類
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }

        try {
        		Properties prop = new Properties();
            prop.load(new FileInputStream("JDBC.properties"));
            Connection con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/db_morgan?"+"autoReconnect=true&useSSL=false", prop);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from account");

            while (rs.next()) {
                int str1 = rs.getInt(1);
                String str2 = rs.getString(2);
                String str3 = rs.getString(3);
                String str4 = rs.getString(4);

                System.out.print(" _ID= " + str1);
                System.out.print(" User= " + str2);
                System.out.print(" Password= " + str3);
                System.out.print(" note= " + str4);
                System.out.print("\n");
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }
}