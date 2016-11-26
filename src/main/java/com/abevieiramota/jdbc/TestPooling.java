package com.abevieiramota.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestPooling {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		final ConnectionFactory cf = new ConnectionFactory();
		for (int i = 0; i < 100; i++) {
			Runnable leTudo = new Runnable() {
				@Override
				public void run() {
					try (Connection conn = cf.getPooledConnection(); Statement stmt = conn.createStatement()) {
						stmt.execute("select * from pessoa");

						try (ResultSet rs = stmt.getResultSet()) {
							while (rs.next()) {
								Thread.sleep(10l);
								System.out.println(rs.getString("nome"));
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			};
			
			Thread th = new Thread(leTudo);
			th.start();
		}
	}
}
