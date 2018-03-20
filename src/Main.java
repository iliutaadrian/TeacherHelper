import com.sun.security.ntlm.Client;
import controller.LaboratorController;
import controller.NoteController;
import controller.StudentController;
import display.mainGui;
import domain.Laboratoare;
import domain.Nota;
import domain.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.LaboratorService;
import service.NotaService;
import service.StudentService;
import validator.*;

public class Main  extends Application {
    private Ivalidator<Student> valS = new StudentValidator();
    private StudentService serS = new StudentService("files/student.txt",valS);
    private StudentController ctrlS = new StudentController(serS);

    private Ivalidator<Laboratoare> valL = new LaboratorValidator();
    private LaboratorService serL = new LaboratorService("files/laboratoare.txt",valL);
    private LaboratorController ctrlL = new LaboratorController(serL);

    private Ivalidator<Nota> valN = new NotaValidator();
    private NotaService serN = new NotaService("files/nota.txt", serS, serL, valN);
    private NoteController ctrlN = new NoteController(serN);

//    ui consola = new ui(ctrlN, ctrlS, ctrlL);
//    consola.start_menu();

    public static void main(String[] args) throws ValidatorException {
        launch(args);

    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("display/mainFXML.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root, 860, 464);
        scene.getStylesheets().add("display/style.css");

        stage.setTitle("Simulator MAP");
        stage.setScene(scene);
        stage.show();

        mainGui ctrl = loader.getController();
        ctrl.setCtrlS(ctrlS);
        ctrl.setCtrlL(ctrlL);
        ctrl.setCtrlN(ctrlN);
        ctrl.init();
    }
}
