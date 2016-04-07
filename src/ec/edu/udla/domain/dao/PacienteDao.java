package ec.edu.udla.domain.dao;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import ec.edu.udla.domain.Paciente;

public class PacienteDao {

	public List<Paciente> findAll() {
		String sql = "SELECT * FROM paciente ORDER BY id";
		List<Paciente> pacientes = ConexionPostgreSQL.getInstance().getJdbcTemplate().query(sql,
				new BeanPropertyRowMapper<Paciente>(Paciente.class));
		return pacientes;
	}

	public void saveOrUpdate(Paciente paciente) {
		if (paciente.getId() != 0) {
			update(paciente);
		} else {
			save(paciente);
		}
	}

	public void save(Paciente paciente) {
		String sql = "insert into paciente" + " (nombre, apellido, cedula, email, direccion, telefono, peso, estatura) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = new Object[] { paciente.getNombre(), paciente.getApellido(), paciente.getCedula(),
				paciente.getEmail(), paciente.getDireccion(), paciente.getTelefono(), paciente.getPeso(),
				paciente.getEstatura() };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.NUMERIC, Types.INTEGER };
		ConexionPostgreSQL.getInstance().getJdbcTemplate().update(sql, params, types);
	}

	public void update(Paciente paciente) {
		String sql = "UPDATE paciente set nombre = ?, apellido=?, cedula=?, email=?, direccion=?, telefono=?, peso=?, estatura=? "
				+ "where id=?";
		Object[] params = new Object[] { paciente.getNombre(), paciente.getApellido(), paciente.getCedula(),
				paciente.getEmail(), paciente.getDireccion(), paciente.getTelefono(), paciente.getPeso(),
				paciente.getEstatura(), paciente.getId() };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.NUMERIC, Types.INTEGER, Types.INTEGER };
		ConexionPostgreSQL.getInstance().getJdbcTemplate().update(sql, params, types);
	}

	public void delete(int id) {
		String sql = "delete from paciente where id = " + id;
		ConexionPostgreSQL.getInstance().getJdbcTemplate().update(sql);
	}

}
