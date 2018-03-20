package display;

import controller.NoteController;
import controller.StudentController;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.StudentService;
import validator.Ivalidator;
import validator.StudentValidator;
import validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;

public class studentGui {
    public TextField fieldNume;
    public TextField fieldId;
    public TextField fieldGrupa;
    public TextField fieldEmail;
    public TextField fieldProfesor;
    
    public ComboBox<String> comboFiltrare;
    public CheckBox checkFiltrare;

    public void setCtrlS(StudentController ctrlS, NoteController ctrlN) {
        this.ctrlS = ctrlS;
        this.ctrlN = ctrlN;
    }

    private StudentController ctrlS;
    private NoteController ctrlN;

    public TableView<Student> tableView;
    public TableColumn<Student, Integer> columnId;
    public TableColumn<Student, String> columnNume;
    public TableColumn<Student, Integer> columnGrupa;
    public TableColumn<Student, String> columnEmail;
    public TableColumn<Student, String> columnProfesor;

    public void init(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnNume.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        columnGrupa.setCellValueFactory(new PropertyValueFactory<>("Grupa"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        columnProfesor.setCellValueFactory(new PropertyValueFactory<>("Profesor"));

        setTableView(ctrlS.getAll());

        List<String> comboList = new ArrayList<>();
        comboList.add("Id");
        comboList.add("Nume");
        comboList.add("Grupa");
        comboList.add("Email");
        comboList.add("Profesor");
        comboFiltrare.setItems(FXCollections.observableArrayList(comboList));
        comboFiltrare.setPromptText("Categorii");
    }

    private void setTableView(List list){
        tableView.setItems(FXCollections.observableArrayList(list));
    }

    private void clear(){
        fieldNume.setText("");
        fieldId.setText("");
        fieldGrupa.setText("");
        fieldEmail.setText("");
        fieldProfesor.setText("");
        checkFiltrare.setSelected(false);
    }
    
    public void actionAdauga(MouseEvent mouseEvent) {
        try {
            ctrlS.adauga(Integer.parseInt(fieldId.getText()),
                    fieldNume.getText(),
                    Integer.parseInt(fieldGrupa.getText()),
                    fieldEmail.getText(),
                    fieldProfesor.getText());
        } catch (ValidatorException e) {
            new AlertBox("Eroare Adaugare", e.getMessage());
        }
        clear();
        setTableView(ctrlS.getAll());
    }

    public void actionSterge(MouseEvent mouseEvent) {
        try {
            ctrlS.remove(Integer.parseInt(fieldId.getText()));
            ctrlN.sterge_student(Integer.parseInt(fieldId.getText()));
        } catch (ValidatorException e) {
            new AlertBox("Eroare Stergere", e.getMessage());
        }
        clear();
        setTableView(ctrlS.getAll());
    }

    public void actionModifica(MouseEvent mouseEvent) {
        try {
            ctrlS.modifica(Integer.parseInt(fieldId.getText()),
                    new Student(Integer.parseInt(fieldId.getText()),
                    fieldNume.getText(),
                    Integer.parseInt(fieldGrupa.getText()),
                    fieldEmail.getText(),
                    fieldProfesor.getText()));
        } catch (ValidatorException e) {
            new AlertBox("Eroare modifica", e.getMessage());
        }
        clear();
        setTableView(ctrlS.getAll());
    }

    public void checkSelected(ActionEvent actionEvent) {
        if(checkFiltrare.isSelected()) {
            if (!comboFiltrare.getSelectionModel().isEmpty()) {
                if (comboFiltrare.getValue().equals("Id")) {
                    if (!fieldId.getText().equals(""))
                        setTableView(ctrlS.filterID(Integer.parseInt(fieldId.getText())));
                }
                if (comboFiltrare.getValue().equals("Nume")) {
                    setTableView(ctrlS.filterNume(fieldNume.getText()));
                }
                if (comboFiltrare.getValue().equals("Grupa")) {
                    if (!fieldId.getText().equals(""))
                        setTableView(ctrlS.filterGrupa(Integer.parseInt(fieldGrupa.getText())));
                }
                if (comboFiltrare.getValue().equals("Email")) {
                    setTableView(ctrlS.filterEmail(fieldEmail.getText()));
                }
                if (comboFiltrare.getValue().equals("Profesor")) {
                    setTableView(ctrlS.filterProfesor(fieldProfesor.getText()));
                }
            }
        }
        else
            setTableView(ctrlS.getAll());
    }

    public void tableSelect(MouseEvent mouseEvent) {
        fieldId.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getId()));
        fieldNume.setText(tableView.getSelectionModel().getSelectedItem().getNume());
        fieldGrupa.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getGrupa()));
        fieldEmail.setText(tableView.getSelectionModel().getSelectedItem().getEmail());
        fieldProfesor.setText(tableView.getSelectionModel().getSelectedItem().getProfesor());
    }

    public void actionUndo(MouseEvent mouseEvent) {
        ctrlS.undo();
        clear();
        setTableView(ctrlS.getAll());
    }
}
