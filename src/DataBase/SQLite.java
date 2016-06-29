package DataBase;

//import java.sql.*;

public class SQLite {
	public void DataBase(){
		/*Scanner scanner = new Scanner(System.in);
		System.out.println("nome");
		String name = scanner.nextLine();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");

			stmt = c.createStatement();
			String sql = "CREATE TABLE HighScores " +
					"(ID INT PRIMARY KEY    	NOT NULL," +
					" POINTS         INT     	NOT NULL, " + 
					" NAME           CHAR(50) 	NOT NULL, " + 
					" TIME 			 INT 	 	NOT NULL, " + 
					" LEVEL			 INT 	 	NOT NULL)"; 
			stmt.executeUpdate(sql);


			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql2 = "INSERT INTO HIGHSCORES (ID,POINTS,NAME,TIME,LEVEL) " +
					"VALUES (1, ?, ?, ?, ? );";
			PreparedStatement ps = c.prepareStatement(sql2);
			ps.setInt(1, 5);
			ps.setString(2, name);
			ps.setInt(3, time);
			ps.setInt(4, level);

			ps.executeUpdate();

			stmt.close();
			c.commit();
			c.close();

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}*/


		/*Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			String sql2 = "INSERT INTO HIGHSCORES (ID,POINTS,NAME,TIME,LEVEL) " +
					"VALUES (2, ?, ?, ?, ? );";
			PreparedStatement ps = c.prepareStatement(sql2);
			ps.setInt(1, points2);
			ps.setString(2, "Rodrigo");
			ps.setInt(3, time);
			ps.setInt(4, level);

			ps.executeUpdate();

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM HighScores;" );
			while(rs.next()) {
				int id = rs.getInt("id");
				String  name = rs.getString("name");
				int points  = rs.getInt("points");
				System.out.println( "ID = " + id);
				System.out.println( "NAME = " + name);
				System.out.println( "AGE = " + points);
				System.out.println();
			}
			rs.close();
			stmt.close();
			c.close();
		}catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Operation done successfully");*/
	}
}
