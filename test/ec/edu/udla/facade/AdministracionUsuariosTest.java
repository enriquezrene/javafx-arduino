package ec.edu.udla.facade;

import org.junit.Test;

import ec.edu.udla.domain.Usuario;

/**
 * Created by rene on 25/04/16.
 */
public class AdministracionUsuariosTest {

	private AdministracionUsuariosFacade service;

	private String contrasena;
	private String nombreUsuario;

	public AdministracionUsuariosTest() {
		contrasena = "1234";
		nombreUsuario = "test";
		service = new AdministracionUsuariosFacade();
	}

	@Test(expected = RuntimeException.class)
	public void siLasCredencialesSonIncorrectasDebeArrojarUnaException() {
		service.login("usuario-invalido", "pwd-invalido");
	}

	@Test
	public void eliminarUsuario() {
		int idUsuarioEliminar = 99;
		service.eliminar(idUsuarioEliminar);
	}

	@Test
	public void siLasCredencialesSonValidasNoSeDebeArrojarNingunError() {
		crearUsuarioSiNoExiste();
		service.login(nombreUsuario, contrasena);
	}

	private void crearUsuarioSiNoExiste() {
		Usuario usuario = new Usuario();
		usuario.setNombreDeUsuario(nombreUsuario);
		usuario.setContrasena(contrasena);
		try {
			service.crear(usuario);
		} catch (Exception e) {
			// Si el usuario ya existe no hacer nada
		}
	}
}
