package ec.edu.udla.lectorinformacion.arduino;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class ComunicadorPuertoSerial {

	private static ComunicadorPuertoSerial instancia;

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
				SerialPort.PARITY_NONE);
		puertoSerial.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
		puertoSerial.addEventListener(new LectorFlujosPuertoSerial(puertoSerial), SerialPort.MASK_RXCHAR);
	}

	public void cerrarConexion() throws Exception {
		puertoSerial.closePort();
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

}
