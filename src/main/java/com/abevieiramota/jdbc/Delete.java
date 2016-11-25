package com.abevieiramota.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {

	public static void main(String[] args)
			throws ClassNotFoundException, SQLException, URISyntaxException, IOException {
		try (Connection conn = ConnectionFactory.instance();
				InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("arquivo.csv");
				Statement stmt = conn.createStatement()) {

			stmt.execute("delete from pessoa where id > 3");

			int count = stmt.getUpdateCount();
			
			System.out.println(count + " linhas atualizadas");
		}
	}
}
