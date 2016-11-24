package com.abevieiramota.jdbccopyin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Read {

	public static void main(String[] args)
			throws ClassNotFoundException, SQLException, URISyntaxException, IOException {
		try (Connection conn = ConnectionFactory.instance();
				InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("arquivo.csv");
				Statement stmt = conn.createStatement()) {
			boolean resultado = stmt.execute("select * from pessoa");
			
			if (resultado) {
				try (ResultSet rs = stmt.getResultSet()) {
					// por padrão começa antes do primeiro elemento
					while (rs.next()) {
						System.out.println(rs.getString("nome"));
					}
				}
			} else {
				System.out.println("veio nada hein");
			}
		}
	}
}
