package ec.edu.udla.arduino.comunicacion;

import java.util.Observable;
import java.util.Observer;

import ec.edu.udla.arduino.io.LectorFlujosPuertoSerial;
import javafx.scene.control.TextArea;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ComunicadorPuertoSerial implements Observer {

	private static ComunicadorPuertoSerial instancia;
	private LectorFlujosPuertoSerial lectorFlujosPuertoSerial;
	private TextArea area;

	public static ComunicadorPuertoSerial obtenerInstancia() {
		if (instancia == null) {
			instancia = new ComunicadorPuertoSerial();
		}
		return instancia;
	}

	private ComunicadorPuertoSerial() {
		super();
	}

	private SerialPort puertoSerial;

	public void configurarConexion(String puertoParaEstablecerConexion) throws Exception {
		puertoSerial = new SerialPort(puertoParaEstablecerConexion);
		puertoSerial.openPort();
		puertoSerial.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE, true, false);
		puertoSerial.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
		lectorFlujosPuertoSerial = new LectorFlujosPuertoSerial(puertoSerial);
		lectorFlujosPuertoSerial.addObserver(this);
		puertoSerial.addEventListener(lectorFlujosPuertoSerial, SerialPort.MASK_RXCHAR);
	}

	public void cerrarConexion() throws Exception {
		puertoSerial.closePort();
	}

	public void asignarCampoParaEscribirSalida(TextArea area) {
		this.area = area;
	}

	public void enviarCadenaDeTexto(String cadenaDeTexto) {
		try {
			puertoSerial.writeBytes(cadenaDeTexto.getBytes());
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	public String[] obtenerPuertosSerialesDisponibles() {
		String[] puertosSerialesDisponibles = SerialPortList.getPortNames();
		return puertosSerialesDisponibles;
	}

	@Override
	public void update(Observable o, Object linea) {
		area.setText(area.getText() + linea.toString().trim() + "\n");
	}

}
