package ec.edu.udla.lectorinformacion.arduino;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.springframework.expression.spel.generated.SpringExpressionsParser.sumExpression_return;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class LectorFlujosPuertoSerial extends Observable implements SerialPortEventListener {

	private List<String> flujoSalidaPuertoSerial;
	private SerialPort puertoSerial;

	public LectorFlujosPuertoSerial(SerialPort puertoSerial) {
		this.puertoSerial = puertoSerial;
		flujoSalidaPuertoSerial = new ArrayList<>();
	}

	public synchronized void serialEvent(SerialPortEvent event) {
		if (event.isRXCHAR()) {
			try {
				Thread.sleep(5000);
				String buffer = puertoSerial.readString();
				if (buffer != null && !buffer.isEmpty() && !buffer.equals("null")) {
					flujoSalidaPuertoSerial.add(buffer);
					System.out.println(buffer);
					setChanged();
					super.notifyObservers(buffer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> obtenerFlujoSalida() {
		return flujoSalidaPuertoSerial;
	}

}