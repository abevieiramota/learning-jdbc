package com.abevieiramota.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Insert {

	public static void main(String[] args)
			throws ClassNotFoundException, SQLException, URISyntaxException, IOException {
		try (Connection conn = ConnectionFactory.instance();
				InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("arquivo.csv");
				PreparedStatement stmt = conn.prepareStatement("insert into pessoa values (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS)) {

			// POSIÇÃO COMEÇA EM 1!!!!!!!!!
			stmt.setString(1, "Abelardo Vieira Mota");
			stmt.setInt(2, 100);
			stmt.setDate(3, new java.sql.Date(new Date().getTime()));

			boolean resultado = stmt.execute();
			System.out.println("Resultado: " + resultado);

			ResultSet resultSet = stmt.getGeneratedKeys();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				System.out.println("Id gerado: " + id);
			}
		}
	}
}
