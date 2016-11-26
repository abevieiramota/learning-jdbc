package com.abevieiramota.jdbc;

import java.io.IOException;
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
		try (Connection conn = new ConnectionFactory().getConnection();
				PreparedStatement stmt = conn.prepareStatement("insert into pessoa values (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS)) {

			conn.setAutoCommit(false);
			try {
				extracted("Abelardo", 27, new Date(), stmt);
				Thread.sleep(1000000l);
				extracted("Juliana", 28, new Date(), stmt);
				conn.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				conn.rollback();
				System.out.println("rollback efetuado");
			}
		}
	}

	private static void extracted(String nome, int idade, Date dataNascimento, PreparedStatement stmt)
			throws SQLException {

		if (idade == 28) {
			throw new IllegalArgumentException("to dando erro");
		}
		// POSIÇÃO COMEÇA EM 1!!!!!!!!!
		stmt.setString(1, nome);
		stmt.setInt(2, idade);
		stmt.setDate(3, new java.sql.Date(dataNascimento.getTime()));

		boolean resultado = stmt.execute();
		System.out.println("Resultado: " + resultado);

		try (ResultSet resultSet = stmt.getGeneratedKeys()) {

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				System.out.println("Id gerado: " + id);
			}
		}
	}
}
