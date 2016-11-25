package com.abevieiramota.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	public static Connection instance()
			throws SQLException, ClassNotFoundException, FileNotFoundException, URISyntaxException {

		// necessário ter um arquivo jdbc.properties em src/main/resources
		// com as propriedades user, pass e db
		Properties jdbcProperties = new Properties();
		try (InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
			if(is == null) {
				throw new IllegalStateException("poxa, n achei o jdbc.properties em main/resources :~");
			}
			jdbcProperties.load(is);
		} catch (IOException e) {

			throw new IllegalStateException("Macho, to conseguindo ler não oh...");
		}

		String user = jdbcProperties.getProperty("user");
		String pass = jdbcProperties.getProperty("pass");
		String db = jdbcProperties.getProperty("db");

		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(String.format("jdbc:postgresql://localhost:5432/%s", db), user, pass);
	}

}
