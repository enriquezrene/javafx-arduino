package ec.edu.udla.ui.regions.adminusuarios;

import java.net.URL;
import java.util.ResourceBundle;

import ec.edu.udla.domain.Usuario;
import ec.edu.udla.domain.dao.UsuarioDao;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.DrawableRegion;
import ec.edu.udla.ui.regions.RegionsContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class AdministrarUsuarioController extends AbstractController implements Initializable, DrawableRegion {

	protected RegionsContainer container;
	private UsuarioDao usuarioDao;

	@FXML
	private TextField nombre, apellido, email, nombreUsuario;

	@FXML
	private PasswordField password;

	private Usuario usuario;

	@FXML
	private ImageView ImageView;

	@FXML
	private TableView<Usuario> usuarios;

	public AdministrarUsuarioController() {
		usuarioDao = new UsuarioDao();
	}

	@FXML
	private void guardarDatos() {
		if (usuario == null) {
			usuario = new Usuario();
		}

		usuario.setContrasena(password.getText());
		usuario.setNombreDeUsuario(nombreUsuario.getText());
		usuario.setNombre(nombre.getText());
		usuario.setApellido(apellido.getText());
		usuario.setEmail(email.getText());

		usuarioDao.saveOrUpdate(usuario);
		usuarios.setItems(FXCollections.observableArrayList(usuarioDao.findAll()));
		limpiarTextField(apellido, email, nombre, nombreUsuario, password);
		usuario = null;
	}

	private void limpiarTextField(TextField... textFields) {
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setText("");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createTable();
	}

	private TableView<Usuario> createTable() {

		usuarios.setItems(FXCollections.observableArrayList(usuarioDao.findAll()));
		usuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		addColumnToTable(createColumn("Nombre", "nombre"), usuarios);
		addColumnToTable(createColumn("Apellido", "apellido"), usuarios);
		addColumnToTable(createColumn("Nombre de usuario", "nombreDeUsuario"), usuarios);
		addColumnToTable(createColumn("Email", "email"), usuarios);

		ContextMenu menu = new ContextMenu();
		MenuItem editarMenuItem = new MenuItem("Editar");
		MenuItem eliminarMenuItem = new MenuItem("Eliminar");
		menu.getItems().addAll(editarMenuItem, eliminarMenuItem);
		usuarios.setContextMenu(menu);

		editarMenuItem.setOnAction(event -> {
			usuario = usuarios.getSelectionModel().getSelectedItem();
			nombre.setText(usuario.getNombre());
			apellido.setText(usuario.getApellido());
			nombreUsuario.setText(usuario.getNombreDeUsuario());
			email.setText(usuario.getEmail());
			password.setText(usuario.getContrasena());
		});

		eliminarMenuItem.setOnAction(event -> {
			Usuario usuario = usuarios.getSelectionModel().getSelectedItem();
			usuarioDao.delete(usuario.getId());
			usuarios.setItems(FXCollections.observableArrayList(usuarioDao.findAll()));
		});

		return usuarios;
	}

//	void addColumnToTable(TableColumn<Usuario, String> column, TableView<Usuario> table) {
//		table.getColumns().add(column);
//	}

	private TableColumn<Usuario, String> createColumn(String columnHeader, String fieldName) {
		TableColumn<Usuario, String> column = new TableColumn<>(columnHeader);
		column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
		column.setMaxWidth(1f * Integer.MAX_VALUE * 25);
		return column;
	}

}
