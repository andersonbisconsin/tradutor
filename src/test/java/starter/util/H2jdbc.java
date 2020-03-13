package starter.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2jdbc {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test";

	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";
	
	public String[] valoresLeitura;

	public String tabela = "Kingdom_comes";

	public void createTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();
			String drop = "DROP TABLE " + tabela;
			stmt.executeUpdate(drop);
			String sql = "CREATE TABLE " + tabela + " " 
					+ "(gameFile VARCHAR(255), " 
					+ " chaveIngame VARCHAR(255), "
					+ " valorIngles VARCHAR(6000), " 
					+ " valorTraduzido VARCHAR(6000), " 
					+ " pronto VARCHAR(10))";
			stmt.executeUpdate(sql);
			System.out.println("Created table in given database...");

			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}

	public void inserir(String[][] valores) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			System.out.println("Connected database successfully...");
			for (int i = 0; i < valores.length; i++) {
				// STEP 3: Execute a query
				stmt = conn.createStatement();
				String sql = "INSERT INTO " + tabela
						+ " (gameFile, chaveIngame, valorIngles, valorTraduzido, pronto) VALUES ('" 
						+ valores[i][0]	+ "', '" 
						+ valores[i][1] + "', '" 
						+ valores[i][2] + "', '" 
						+ valores[i][3] + "', '"
						+ (valores[i][4]==null ? "pendente" : valores[i][4].equalsIgnoreCase("tratar") ? "tratar" : "pendente") + "')";

				stmt.executeUpdate(sql);
			}

			System.out.println("Inserted records into the table...");

			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}

	public boolean  ler() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			System.out.println("Connected database successfully...");
			stmt = conn.createStatement();
			String sql = "SELECT TOP 1 * FROM " + tabela + " WHERE PRONTO = 'pendente'";
			
			//Pesquisas
			//pendente
			//tratar
			//pronto
			//uso
			
			ResultSet rs = stmt.executeQuery(sql);
//			if (rs.next() ==  false ) {
//		         System .out.println ( "ResultSet vazio em Java" );
//		         return false;
//	         }
			String[] valores = new String[5];
			
			// STEP 4: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				valores[0] = rs.getString("gameFile");
				valores[1] = rs.getString("chaveIngame");
				valores[2] = rs.getString("valorIngles");
				valores[3] = rs.getString("valorTraduzido");
				valores[4] = rs.getString("pronto");

				// Display values
				System.out.print("<gameFile>: " + valores[0]);
				System.out.print(", <chaveIngame>: " + valores[1]);
				System.out.print(", <valorIngles>: " + valores[2]);
				System.out.print(", <valorTraduzido>: " + valores[3]);
				System.out.println(", <pronto>: " + valores[4]);
			}
			// STEP 5: Clean-up environment
			rs.close();
			
			System.out.println("Connected database successfully...");
			sql = "UPDATE " + tabela + " SET pronto = 'uso' WHERE chaveIngame = '" + valores[1] + "'";
			stmt.executeUpdate(sql);
			
//			SELECT COUNT(*) FROM KINGDOM_COMES where pronto = 'uso'
//
//			UPDATE KINGDOM_COMES  SET pronto = 'pendente' WHERE pronto  = 'uso'

			
			valoresLeitura = valores;
			
			return true;
			
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		return false;
	}
	
	public String[][] lerArquivo(String nomeArquivo) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			System.out.println("Connected database successfully...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM " + tabela + " WHERE gameFile = '"+ nomeArquivo +"'";
			
			//Pesquisas
				//pendente
				//tratar
				//pronto
				//uso
			
			ResultSet rs = stmt.executeQuery(sql);
//			if (rs.next() ==  false ) {
//		         System .out.println ( "ResultSet vazio em Java" );
//		         return false;
//	         }
			String[][] valores = new String[50557][5];
			
			// STEP 4: Extract data from result set
			int i = 0;
			while (rs.next()) {
				// Retrieve by column name
				valores[i][0] = rs.getString("gameFile");
				valores[i][1] = rs.getString("chaveIngame");
				valores[i][2] = rs.getString("valorIngles");
				valores[i][3] = rs.getString("valorTraduzido");
				valores[i][4] = rs.getString("pronto");

				// Display values
				System.out.print("<gameFile>: " + valores[i][0]);
				System.out.print(", <chaveIngame>: " + valores[i][1]);
				System.out.print(", <valorIngles>: " + valores[i][2]);
				System.out.print(", <valorTraduzido>: " + valores[i][3]);
				System.out.println(", <pronto>: " + valores[i][4]);
				i++;
			}
			// STEP 5: Clean-up environment
			rs.close();
			
//			SELECT COUNT(*) FROM KINGDOM_COMES where pronto = 'uso'
//
//			UPDATE KINGDOM_COMES  SET pronto = 'pendente' WHERE pronto  = 'uso'

			return valores;
			
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		return null;
	}

	public void atualizar(String valor, String valorChave) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to a database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			System.out.println("Connected database successfully...");
			stmt = conn.createStatement();
			String sql = "UPDATE " + tabela + " SET valorTraduzido = '"+ valor +"', pronto = 'pronto' WHERE chaveIngame = '" + valorChave + "'";
			System.out.println(sql);
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}

	public void excluir(String valorChave) {
		Connection conn = null;
		Statement stmt = null;

		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();
			String sql = "DELETE FROM " + tabela + "WHERE chaveIngame = " + valorChave;
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}
}