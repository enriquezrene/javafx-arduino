package ec.edu.udla.arduino.listener;

import ec.edu.udla.arduino.parser.LecturaGlucometroArduino;
import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.LecturaOffLine;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.LecturaOffLineDao;
import ec.edu.udla.domain.dao.PacienteDao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import org.controlsfx.control.Notifications;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by rene on 20/05/16.
 */
public class ModoOnlineListener implements Observer {

    PacienteDao pacienteDao;
    TableView<Paciente> pacientes;

    private TableView<LecturaOffLine> lecturaOffLine;

    private static ModoOnlineListener instance;

    public void setLecturaOffLine(TableView<LecturaOffLine> lecturaOffLine){
        this.lecturaOffLine=lecturaOffLine;
    }
    public void setTable(TableView<Paciente> pacientes){
        this.pacientes = pacientes;
    }

    public static ModoOnlineListener getInstance() {
        if (instance == null) {
            instance = new ModoOnlineListener();
        }
        return instance;
    }

    private ModoOnlineListener() {
        pacienteDao = new PacienteDao();
    }

    @Override
    public void update(Observable o, Object flujoArduino) {
        if (flujoArduino != null)
            procesarLectura(flujoArduino.toString().trim());
    }

    public void procesarLectura(String cadenaArduino) {
        LecturaGlucometro lecturaGlucometro = new LecturaGlucometroArduino(cadenaArduino).obtenerObjeto();
        pacienteDao.registrarLectura(lecturaGlucometro);
        pacienteDao.registrarLecturaOffLine(lecturaGlucometro);
        mostrarNotificacion(lecturaGlucometro);
        pacientes.refresh();

        List<LecturaOffLine> lecturasGlucometro = new LecturaOffLineDao().findAll();
        lecturaOffLine.setItems(FXCollections.observableArrayList(lecturasGlucometro));
        lecturaOffLine.refresh();
    }

    void mostrarNotificacion(LecturaGlucometro lecturaGlucometro) {
        Platform.runLater(() -> {
            Notifications.create().title("Informacion guardada")
                    .text("Se acaba de registrar informacion del paciente con identificacion: "
                            + lecturaGlucometro.getCedulaPaciente()).showInformation();
        });
    }

}
