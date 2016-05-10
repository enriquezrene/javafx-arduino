package ec.edu.udla.domain.dao;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexionPostgreSQL {

	private Properties DB_PROPERTIES;
	private JdbcTemplate JDBC_TEMPLATE;

	private static ConexionPostgreSQL instance;

	public static ConexionPostgreSQL getInstance() {
		if (instance == null) {
			instance = new ConexionPostgreSQL();
		}
		return instance;
	}

	private ConexionPostgreSQL() {
		DB_PROPERTIES = new Properties();
		DB_PROPERTIES.put("driverClassName", "org.postgresql.Driver");
		DB_PROPERTIES.put("url", "jdbc:postgresql://pellefant-02.db.elephantsql.com:5432/jtythyyj");
		DB_PROPERTIES.put("username", "jtythyyj");
		DB_PROPERTIES.put("password", "I3rOJyBzEmMSEFA97X6BWkS2RYG1mZFH");
	}

	public Properties getDbProperties() {
		return DB_PROPERTIES;
	}

	public JdbcTemplate getJdbcTemplate() {
		if (JDBC_TEMPLATE == null) {
			buildJdbcTemplate();
		}
		return JDBC_TEMPLATE;

	}

	private void buildJdbcTemplate() {

		try {
			DataSource ds = BasicDataSourceFactory.createDataSource(DB_PROPERTIES);
			JDBC_TEMPLATE = new JdbcTemplate(ds);
		} catch (Exception e) {
			throw new RuntimeException("Ocurrio un error al realizar la conexion");
		}
	}

}
