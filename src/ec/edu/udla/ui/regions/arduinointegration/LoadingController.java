package ec.edu.udla.ui.regions.arduinointegration;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.domain.util.Context;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sun.util.resources.cldr.ta.CalendarData_ta_IN;

import java.net.URL;
import java.util.*;

/**
 * Created by rene on 06/05/16.
 */
public class LoadingController implements Observer, Initializable {


    public static final String INICIO_CONEXION = "Inicializa";
    public static final String FIN_CONEXION = "Fin";
    public static final String INICIO_LECTURA = "InicioLectura";
    public static final String PROCESO_LECTURA = "ProcesoLectura";
    public static final String FIN_LECTURA = "FinLectura";
    private Map<String, String> mensajes;

    @FXML
    private Label mensaje;

    private ConexionArduinoController conexionArduinoController;


    @Override
    public void update(Observable o, Object texto) {
        Platform.runLater(() -> {
            if (texto != null) {
                mensaje.setText(buscarMensajeQueContieneCadena(texto.toString(), INICIO_CONEXION, FIN_CONEXION));
                if (texto.toString().contains(FIN_CONEXION)) {
                    mensaje.setText("La informacion se lee, se demora, ventana cierra sola");

             /*       new Runnable(){

                        @Override
                        public void run() {*/
                            for (int i = 1; i < 5; i++) {
                                ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(i+"");

                                Date fechaIni = new Date();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(new Date());
                                calendar.add(Calendar.SECOND, 5);
                                while (new Date().getTime()!=calendar.getTimeInMillis()){

                                }


//                                try {
//                                    Thread.sleep(5000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                            }
                    /*
                            Stage stage = (Stage) mensaje.getScene().getWindow();
                        stage.close();
//                            ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("2");
                        }
                    }.run();*/

             //       Context.getInstance().put("Leer", true);
//                    ComunicadorPuertoSerial.obtenerInstancia().removeObservers(this);
//                    try {
//                        Thread.sleep(1000L);
                        Stage stage = (Stage) mensaje.getScene().getWindow();
                        stage.close();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });
    }


    private String buscarMensajeQueContieneCadena(String cadena, String... mensajes) {
        for (int i = 0; i < mensajes.length; i++) {
            if (cadena.contains(mensajes[i])) {
                return this.mensajes.get(mensajes[i]);
            }
        }
        return cadena;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ComunicadorPuertoSerial.obtenerInstancia().addObservers(this);
        mensajes = new HashMap<>();
        mensajes.put(INICIO_CONEXION, "Estableciendo conexion con el dispositivo...");
        mensajes.put(FIN_CONEXION, "La informacion se lee, se demora, ventana cierra sola");
        mensajes.put(INICIO_LECTURA, "El proceso de descarga de informacion esta empezando...");
        mensajes.put(PROCESO_LECTURA, "Se esta realizando la lectura de informacion...");
        mensajes.put(FIN_LECTURA, "La informacion ha sido leida exitosamente.");
        Object val = Context.getInstance().get("Leer");
        System.out.println(val);
//        if (val /!= null && Boolean.valueOf(val.toString())) {


            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//        }
    }
}
