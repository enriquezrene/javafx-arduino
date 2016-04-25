package ec.edu.udla.domain.dao;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import ec.edu.udla.domain.PojoBase;
import ec.edu.udla.domain.Usuario;

public class UsuarioDao extends AbstractDao {

	public List<Usuario> findAll() {
		String sql = "SELECT * FROM usuario ORDER BY id";
		List<Usuario> usuarios = conexion.getJdbcTemplate().query(sql,
				new BeanPropertyRowMapper<Usuario>(Usuario.class));
		return usuarios;
	}

	// TODO: agregar otra forma de concatenar cadenas
	public List<Usuario> findUser(String nombreDeUsuario, String password) {
		String sql = "SELECT * FROM usuario WHERE nombre_de_usuario='" + nombreDeUsuario + "' AND contrasena='"
				+ password + "'";
		List<Usuario> usuarios = conexion.getJdbcTemplate().query(sql,
				new BeanPropertyRowMapper<Usuario>(Usuario.class));
		return usuarios;
	}

	public void save(PojoBase entidad) {
		Usuario usuario = (Usuario) entidad;
		String sql = "insert into usuario" + " (nombre, apellido, email, nombre_de_usuario, contrasena) "
				+ "values (?, ?, ?, ?, ?)";
		Object[] params = new Object[] { usuario.getNombre(), usuario.getApellido(), usuario.getEmail(),
				usuario.getNombreDeUsuario(), usuario.getContrasena() };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
		conexion.getJdbcTemplate().update(sql, params, types);
	}

	public void update(PojoBase entidad) {
		Usuario usuario = (Usuario) entidad;
		String sql = "UPDATE usuario set nombre = ?, apellido=?, email=?, nombre_de_usuario=?, contrasena=? "
				+ "where id=?";
		Object[] params = new Object[] { usuario.getNombre(), usuario.getApellido(), usuario.getEmail(),
				usuario.getNombreDeUsuario(), usuario.getContrasena(), usuario.getId() };
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.INTEGER };
		conexion.getJdbcTemplate().update(sql, params, types);
	}

	public void delete(int id) {
		String sql = "delete from usuario where id = " + id;
		conexion.getJdbcTemplate().update(sql);
	}

}
