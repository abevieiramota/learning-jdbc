package com.abevieiramota.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class CopyIn {

	public static void main(String[] args)
			throws ClassNotFoundException, SQLException, IOException, URISyntaxException {

		try (Connection conn = ConnectionFactory.instance();
				InputStream is = ConnectionFactory.class.getClassLoader().getResourceAsStream("arquivo.csv")) {

			CopyManager cm = new CopyManager((BaseConnection) conn);

			// NULL = 'NULL' como indicador de que a coluna deve ser nula
			// ENCODING = 'ISO-8859-1'
			cm.copyIn("COPY pessoa FROM STDIN NULL 'NULL' ENCODING 'ISO-8859-1'", is);
		}
	}

}
