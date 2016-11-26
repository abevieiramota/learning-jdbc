package com.abevieiramota.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.postgresql.jdbc2.optional.PoolingDataSource;

public class ConnectionFactory {
	private DataSource dataSource;
	private int numConnections;
	private String user;
	private String pass;
	private String db;

	public ConnectionFactory() throws ClassNotFoundException {
		Properties jdbcProperties = new Properties();
		try (InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
			if (is == null) {
				throw new IllegalStateException("poxa, n achei o jdbc.properties em main/resources :~");
			}
			jdbcProperties.load(is);
		} catch (IOException e) {

			throw new IllegalStateException("Macho, to conseguindo ler não oh...");
		}

		this.user = jdbcProperties.getProperty("user");
		this.pass = jdbcProperties.getProperty("pass");
		this.db = jdbcProperties.getProperty("db");

		PoolingDataSource dataSource = new PoolingDataSource();
		dataSource.setUrl(String.format("jdbc:postgresql://localhost:5432/%s", db));
		dataSource.setUser(user);
		dataSource.setPassword(pass);
		dataSource.setMaxConnections(100);

		this.dataSource = dataSource;

		Class.forName("org.postgresql.Driver");
	}

	public Connection getPooledConnection() throws SQLException {

		Connection conn = null;
		synchronized (this) {
			conn = this.dataSource.getConnection();
			this.numConnections++;
			System.out.println("Conexões adquiridas >>>>>>>>>> " + this.numConnections);
		}

		return conn;
	}

	public Connection getConnection() throws SQLException {

		Connection connection = DriverManager.getConnection(String.format("jdbc:postgresql://localhost:5432/%s", this.db), this.user,
				this.pass);

		this.numConnections++;
		System.out.println("Conexões adquiridas >>>>>>>>>> " + this.numConnections);

		return connection;
	}
}
