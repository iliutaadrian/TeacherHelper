package display;

import controller.LaboratorController;
import controller.NoteController;
import controller.StudentController;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class mainGui {
    //pane
    public Pane studentPane;
    public Label numarStudenti;

    public Pane labsPane;
    public Label numarLaboratoare;

    public Pane gradePane;
    public Label numarNote;

    //table
    public TableView<Student> tableView;
    public TableColumn<Student, Integer> columnId;
    public TableColumn<Student, String> columnNume;
    public TableColumn<Student, Integer> columnGrupa;
    public TableColumn<Student, String> columnEmail;
    public TableColumn<Student, String> columnProfesor;


    //search
    public TextField getFastID;
    public ComboBox<Integer> comboGrupa;


    StudentController ctrlS;
    LaboratorController ctrlL;
    NoteController ctrlN;

    public void setCtrlS(StudentController ctrlS) {
        this.ctrlS = ctrlS;
    }

    public void setCtrlL(LaboratorController ctrlL) {
        this.ctrlL = ctrlL;
    }

    public void setCtrlN(NoteController ctrlN) {
        this.ctrlN = ctrlN;
    }

    public void init(){
        numarStudenti.setText(String.valueOf(ctrlS.numarStudenti()));
        numarLaboratoare.setText(String.valueOf(ctrlL.numarLaboratoare()));
        numarNote.setText(String.valueOf(ctrlN.numarNote()));

        //table
        columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnNume.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        columnGrupa.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        columnProfesor.setCellValueFactory(new PropertyValueFactory<>("Profesor"));

        setTableView(ctrlS.getAll());

        //auxiliare
        getFastID.setPromptText("ID");
        comboGrupa.setItems(FXCollections.observableArrayList(ctrlS.getGrupa()));
        comboGrupa.setPromptText("Grupe");
    }


    public void studentAction(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("studentFXML.fxml"));
            Parent root = loader.load();

            studentGui ctrl = loader.getController();
            ctrl.setCtrlS(ctrlS, ctrlN);
            ctrl.init();

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Student");
            window.setResizable(false);

            Scene scene = new Scene(root, 760, 312);
            window.setScene(scene);
            window.show();
            window.setOnCloseRequest(e->{
                        numarStudenti.setText(String.valueOf(ctrlS.numarStudenti()));
                        setTableView(ctrlS.getAll());
                        numarNote.setText(String.valueOf(ctrlN.numarNote()));
                    });

        } catch (IOException e) {
            new AlertBox("Eroare student", e.getMessage());
        }
    }

    public void labsAction(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("laboratoareFXML.fxml"));
            Parent root = loader.load();

            laboratoareGui ctrl = loader.getController();
            ctrl.setCtrl(ctrlL, ctrlN);
            ctrl.init();

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Laboratoare");
            window.setResizable(false);

            Scene scene = new Scene(root, 765, 312);
            window.setScene(scene);
            window.show();
            window.setOnCloseRequest(e->{
                        numarLaboratoare.setText(String.valueOf(ctrlL.numarLaboratoare()));
                        numarNote.setText(String.valueOf(ctrlN.numarNote()));
                    });
        } catch (IOException e) {
            new AlertBox("Eroare", e.getMessage());
        }
    }

    public void gradeAction(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("notaFXML.fxml"));
            Parent root = loader.load();

            noteGui ctrl = loader.getController();
            ctrl.setCtrl(ctrlS, ctrlN);
            ctrl.init();

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Nota");
            window.setResizable(false);

            Scene scene = new Scene(root, 771, 312);
            window.setScene(scene);
            window.show();
            window.setOnCloseRequest(e->numarNote.setText(String.valueOf(ctrlN.numarNote())));
        } catch (IOException e) {
            new AlertBox("Eroare", e.getMessage());
        }
    }

    public void findAction(MouseEvent mouseEvent) {
        setTableView(ctrlS.getAll());
    }

    public void studentiId(MouseEvent mouseEvent) {
        if(!getFastID.getText().equals("")){
            setTableView(ctrlS.filterID(Integer.parseInt(getFastID.getText())));
        }
    }

    public void studentiGrupa(MouseEvent mouseEvent) {
        if (!comboGrupa.getSelectionModel().isEmpty()) {
            setTableView(ctrlS.filterGrupa(comboGrupa.getValue()));
        }
    }

    private void setTableView(List list){
        tableView.setItems(FXCollections.observableArrayList(list));
    }


}
