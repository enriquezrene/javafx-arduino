package ec.edu.udla.ui;

import java.io.IOException;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.ui.regions.RegionsContainer;
import ec.edu.udla.ui.regions.adminusuarios.AdministrarUsuarioController;
import ec.edu.udla.ui.regions.arduino.LeerInformacionArduinoController;
import ec.edu.udla.ui.regions.arduinointegration.ConexionArduinoController;
import ec.edu.udla.ui.regions.help.HelpController;
import ec.edu.udla.ui.regions.login.IntroController;
import ec.edu.udla.ui.regions.login.LoginController;
import ec.edu.udla.ui.regions.offline.LecturasOfflineController;
import ec.edu.udla.ui.regions.pacientes.IngresarPacienteController;
import ec.edu.udla.ui.regions.sms.SmsController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    public static final String PANTALLA_DATOS_PACIENTE = "IngresarPaciente.fxml";
    public static final String PANTALLA_LEER_INFORMACION = "LeerInformacionArduino.fxml";
    public static final String PANTALLA_LOGIN = "Login.fxml";
    public static final String PANTALLA_ADMINISTRACION_USUARIOS = "AdministracionUsuarios.fxml";
    public static final String PANTALLA_MEDIDAS_GLUCOSA = "LecturasGlucosa.fxml";
    public static final String PANTALLA_CONEXION_ARDUINO = "ConexionArduino.fxml";
    public static final String PANTALLA_AYUDA = "help.fxml";
    public static final String PANTALLA_OFFLINE = "LecturasOffLine.fxml";
    public static final String PANTALLA_HOME = "Intro.fxml";

    private RegionsContainer mainContainer;

    @Override
    public void start(Stage primaryStage) throws Exception {

        cargarPantallas();

        mainContainer.setCurrentScreen(PANTALLA_HOME);
//        mainContainer.setId("a");
        mainContainer.setPrimaryStage(primaryStage);

        Menu menuInicio = new Menu("Inicio");
        MenuItem opcionIngresarPacientes = crearItemDeMenu("Pacientes", PANTALLA_DATOS_PACIENTE);
        MenuItem opcionAdministrarUsuarios = crearItemDeMenu("Administrar usuarios", PANTALLA_ADMINISTRACION_USUARIOS);

        Menu menuLecturas = new Menu("Lecturas de glucosa");
        MenuItem opcioneOffline = crearItemDeMenu("Lecturas recibidas", PANTALLA_OFFLINE);
        menuLecturas.getItems().add(opcioneOffline);

        Menu menuAyuda = new Menu("Ayuda");
        MenuItem opcionAyuda = crearItemDeMenu("Mostrar ayuda", PANTALLA_AYUDA);
        menuAyuda.getItems().addAll(opcionAyuda);

        MenuItem opcionLeerInformacionArduino = crearItemDeMenu("Establecer conexion", PANTALLA_LEER_INFORMACION);

        MenuItem opcionEnvioSms = new MenuItem("Enviar SMS");
        opcionEnvioSms.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        opcionEnvioSms.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(SmsController.class.getResource("Sms.fxml"));
                Parent node = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("SMS");
                dialogStage.initModality(Modality.NONE);
                dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(node);
                scene.getStylesheets().add("bootstrapfx.css");
                scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
                dialogStage.setScene(scene);
                dialogStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        MenuItem opcionSalir = new MenuItem("Salir");
//        opcionSalir.setOnAction(event -> {
//            try {
//                FXMLLoader loader = new FXMLLoader(SmsController.class.getResource("Sms.fxml"));
//                Parent node = loader.load();
//                Stage dialogStage = new Stage();
//                dialogStage.setTitle("SMS");
//                dialogStage.initModality(Modality.NONE);
//                dialogStage.initOwner(primaryStage);
//                Scene scene = new Scene(node);
//                scene.getStylesheets().add("bootstrapfx.css");
//                scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
//                dialogStage.setScene(scene);
//                dialogStage.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

        menuInicio.getItems().addAll(opcionIngresarPacientes, opcionAdministrarUsuarios, opcionEnvioSms);


        mainContainer.cargarOpcionesDeMenu(menuInicio, menuLecturas, menuAyuda);


//        Scene sceneHome = new Scene(mainContainer);
//        sceneHome.getStylesheets().add("bootstrapfx.css");
//        sceneHome.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
////        primaryStage.setTitle("Home");
//        primaryStage.setScene(sceneHome);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        primaryStage.show();
//        for (double i = 0; i < 999999999; i++) {
//            new Object();
//        }
//        primaryStage.hide();




        mainContainer.setCurrentScreen(PANTALLA_HOME);
        Scene scene = new Scene(mainContainer,880, 610);
        scene.getStylesheets().add("bootstrapfx.css");
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Sistema de monitoreo de glucosa");
        primaryStage.setScene(scene);
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        mainContainer.setHostServices(getHostServices());

        //Thread.sleep(2100);
//        mainContainer.setCurrentScreen(PANTALLA_HOME);
/*
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (double i = 0; i < 999999999; i++) {
                    new Object();
                }
                mainContainer.setCurrentScreen(PANTALLA_LOGIN);
            }
        });*/
//        Thread.sleep(2000);
//        mainContainer.setCurrentScreen(PANTALLA_LOGIN);
    }

    private void cargarPantallas() throws IOException {
        mainContainer = new RegionsContainer();

        mainContainer.loadScreen(PANTALLA_HOME, IntroController.class.getResource(PANTALLA_HOME));
        mainContainer.loadScreen(PANTALLA_OFFLINE, LecturasOfflineController.class.getResource(PANTALLA_OFFLINE));
        mainContainer.loadScreen(PANTALLA_DATOS_PACIENTE,
                IngresarPacienteController.class.getResource(PANTALLA_DATOS_PACIENTE));
        mainContainer.loadScreen(PANTALLA_LOGIN, LoginController.class.getResource(PANTALLA_LOGIN));
        mainContainer.loadScreen(PANTALLA_LEER_INFORMACION,
                LeerInformacionArduinoController.class.getResource(PANTALLA_LEER_INFORMACION));
        mainContainer.loadScreen(PANTALLA_ADMINISTRACION_USUARIOS,
                AdministrarUsuarioController.class.getResource(PANTALLA_ADMINISTRACION_USUARIOS));
        mainContainer.loadScreen(PANTALLA_CONEXION_ARDUINO,
                ConexionArduinoController.class.getResource(PANTALLA_CONEXION_ARDUINO));


        mainContainer.loadScreen(PANTALLA_AYUDA, HelpController.class.getResource(PANTALLA_AYUDA));
    }

    private MenuItem crearItemDeMenu(String menuItemLabel, String screenToShow) throws Exception {
        MenuItem menuItem = new MenuItem(menuItemLabel);

        menuItem.setOnAction(event -> {
            mainContainer.setCurrentScreen(screenToShow);
        });
        return menuItem;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
    	ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("B");
    	Thread.sleep(2000L);
    }
}
