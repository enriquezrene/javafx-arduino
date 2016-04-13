package ec.edu.udla.lectorinformacion.arduino;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class LectorFlujosPuertoSerial implements SerialPortEventListener {

	private SerialPort puertoSerial;

	public LectorFlujosPuertoSerial(SerialPort puertoSerial) {
		this.puertoSerial = puertoSerial;
	}

	public synchronized void serialEvent(SerialPortEvent event) {
		if (event.isRXCHAR()) {
			try {
				Thread.sleep(2000);
				String buffer = puertoSerial.readString();
				if (buffer != null && !buffer.isEmpty() && !buffer.equals("null"))
					System.out.print(buffer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}