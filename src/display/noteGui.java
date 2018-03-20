package display;

import controller.LaboratorController;
import controller.NoteController;
import controller.StudentController;
import domain.Laboratoare;
import domain.Nota;
import domain.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import service.LaboratorService;
import service.NotaService;
import service.StudentService;
import validator.*;

import java.util.ArrayList;
import java.util.List;

public class noteGui {
    public ComboBox<String> comboFiltrare;
    public CheckBox checkFiltrare;

    public TableView<Nota> tableView;
    public TableColumn<Object, Object> columnIdNota;
    public TableColumn<Object, Object> columnIdStudent;
    public TableColumn<Object, Object> columnIdLaborator;
    public TableColumn<Object, Object> columnPredare;
    public TableColumn<Object, Object> columnValoare;
    public TableColumn<Nota, String> columnNumeStudent;

    public TextField fieldIdStudent;
    public TextField fieldIdNota;
    public TextField fieldIdLaborator;
    public TextField fieldPredare;
    public TextField fieldValoare;


    private NoteController ctrl;
    private StudentController ctrlS;

    public void setCtrl(StudentController ctrlS,NoteController ctrl) {
        this.ctrl = ctrl;
        this.ctrlS = ctrlS;
    }


    public void init(){
        columnIdNota.setCellValueFactory(new PropertyValueFactory<>("IdNota"));
        columnIdStudent.setCellValueFactory(new PropertyValueFactory<>("IdStudent"));
        columnIdLaborator.setCellValueFactory(new PropertyValueFactory<>("IdLaborator"));
        columnPredare.setCellValueFactory(new PropertyValueFactory<>("Predare"));
        columnValoare.setCellValueFactory(new PropertyValueFactory<>("Valoare"));
        columnNumeStudent.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(ctrlS.findOne(x.getValue().getIdStudent()).getNume()));

        setTableView(ctrl.getAll());

        List<String> comboList = new ArrayList<>();
        comboList.add("IdNota");
        comboList.add("IdStudent");
        comboList.add("IdLaborator");
        comboList.add("Predare");
        comboList.add("Valoare");
        comboFiltrare.setItems(FXCollections.observableArrayList(comboList));
        comboFiltrare.setPromptText("Categorii");
    }

    private void setTableView(List list){
        tableView.setItems(FXCollections.observableArrayList(list));

    }

    private void clear(){
        fieldIdNota.setText("");
        fieldIdLaborator.setText("");
        fieldIdStudent.setText("");
        fieldValoare.setText("");
        fieldPredare.setText("");

        checkFiltrare.setSelected(false);
    }

    public void actionAdauga(MouseEvent mouseEvent) {
        try {
            ctrl.adauga(Integer.parseInt(fieldIdNota.getText()),
                    Integer.parseInt(fieldIdStudent.getText()),
                    Integer.parseInt(fieldIdLaborator.getText()),
                    Integer.parseInt(fieldPredare.getText()),
                    Integer.parseInt(fieldValoare.getText()));
        } catch (ValidatorException e) {
            new AlertBox("Eroare Adaugare", e.getMessage());
        }
        clear();
        setTableView(ctrl.getAll());
    }

    public void actionSterge(MouseEvent mouseEvent) {
        try {
            ctrl.remove(Integer.parseInt(fieldIdNota.getText()));
        } catch (ValidatorException e) {
            new AlertBox("Eroare Stergere", e.getMessage());
        }
        clear();
        setTableView(ctrl.getAll());
    }

    public void actionModifica(MouseEvent mouseEvent) {
        try {
            ctrl.modifica(Integer.parseInt(fieldIdNota.getText()),
                    Integer.parseInt(fieldValoare.getText()),
                    Integer.parseInt(fieldPredare.getText()));
        } catch (ValidatorException e) {
            new AlertBox("Eroare modifica", e.getMessage());
        }
        clear();
        setTableView(ctrl.getAll());
    }

    public void checkSelected(ActionEvent actionEvent) {
        if(checkFiltrare.isSelected()) {
            if (!comboFiltrare.getSelectionModel().isEmpty()) {
                if (comboFiltrare.getValue().equals("IdNota")) {
                    if (!fieldIdNota.getText().equals(""))
                        setTableView(ctrl.filterIdNota(Integer.parseInt(fieldIdNota.getText())));
                }
                if (comboFiltrare.getValue().equals("IdStudent")) {
                    if (!fieldIdStudent.getText().equals(""))
                        setTableView(ctrl.filterIdStudent(Integer.parseInt(fieldIdStudent.getText())));
                }
                if (comboFiltrare.getValue().equals("IdLaborator")) {
                    if (!fieldIdLaborator.getText().equals(""))
                        setTableView(ctrl.filterIdLaborator(Integer.parseInt(fieldIdLaborator.getText())));
                }
                if (comboFiltrare.getValue().equals("Predare")) {
                    if (!fieldPredare.getText().equals(""))
                        setTableView(ctrl.filterPredare(Integer.parseInt(fieldPredare.getText())));
                }
                if (comboFiltrare.getValue().equals("Valoare")) {
                    if (!fieldValoare.getText().equals(""))
                        setTableView(ctrl.filterValoare(Integer.parseInt(fieldValoare.getText())));
                }

            }
        }
        else
            setTableView(ctrl.getAll());
    }

    public void tableSelect(MouseEvent mouseEvent) {
        fieldIdNota.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getIdNota()));
        fieldIdStudent.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getIdStudent()));
        fieldIdLaborator.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getIdLaborator()));
        fieldPredare.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getPredare()));
        fieldValoare.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getValoare()));
    }

    public void actionUndo(MouseEvent mouseEvent) {
        ctrl.undo();
        clear();
        setTableView(ctrl.getAll());
    }
}
