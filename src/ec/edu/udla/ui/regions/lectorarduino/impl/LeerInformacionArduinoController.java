package ec.edu.udla.ui.regions.lectorarduino.impl;

import java.net.URL;
import java.util.ResourceBundle;

import ec.edu.udla.lectorinformacion.arduino.ComunicadorPuertoSerial;
import ec.edu.udla.ui.regions.DrawableRegion;
import ec.edu.udla.ui.regions.RegionsContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class LeerInformacionArduinoController implements Initializable, DrawableRegion {

	private RegionsContainer regionsContainer;

	@FXML
	private ComboBox puertosSeriales;

	@Override
	public void setContainer(RegionsContainer container) {
		this.regionsContainer = container;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] puertosDisponibles = ComunicadorPuertoSerial.obtenerInstancia().obtenerPuertosSerialesDisponibles();
//		System.out.println(puertosDisponibles.length);
		for (int i = 0; i < puertosDisponibles.length; i++) {
			puertosSeriales.getItems().add(puertosDisponibles[i]);
		}
	}

}
