package ec.edu.udla.facade;

import java.util.List;

import ec.edu.udla.domain.Usuario;
import ec.edu.udla.domain.dao.UsuarioDao;

public class AdministracionUsuariosFacade {

	private UsuarioDao usuarioDao;

	public AdministracionUsuariosFacade() {
		usuarioDao = new UsuarioDao();
	}

	public void login(String nombreDeUsuario, String pwd) {
		List<Usuario> usuarios = usuarioDao.findUser(nombreDeUsuario, pwd);
		if (usuarios.isEmpty()) {
			throw new RuntimeException("Credenciales incorrectas");
		}
	}

	public void crear(Usuario usuario) {
		List<Usuario> usuarios = usuarioDao.findUser(usuario.getNombreDeUsuario(), usuario.getContrasena());
		if (usuarios.isEmpty()) {
			usuarioDao.save(usuario);
		} else {
			throw new RuntimeException("Ya existe un usuario registrado con las credenciales provistas");
		}
	}

	public void eliminar(int idUsuarioEliminar) {
		usuarioDao.delete(idUsuarioEliminar);

	}

}
