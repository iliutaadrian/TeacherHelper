package display;

import controller.LaboratorController;
import controller.NoteController;
import domain.Laboratoare;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.LaboratorService;
import validator.Ivalidator;
import validator.LaboratorValidator;
import validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;

public class laboratoareGui {
    public ComboBox<String> comboFiltrare;
    public CheckBox checkFiltrare;

    public TextField fieldDescriere ;
    public TextField fieldId;
    public TextField fieldDeadline;

    private LaboratorController ctrl;
    private NoteController ctrlN;

    public void setCtrl(LaboratorController ctrl, NoteController ctrlN) {
        this.ctrl = ctrl;
        this.ctrlN = ctrlN;
    }

    public TableView<Laboratoare> tableView;
    public TableColumn<Laboratoare, Integer> columnId;
    public TableColumn<Laboratoare, String> columnDescriere;
    public TableColumn<Laboratoare, Integer> columnDeadline;
    
    public void init(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnDescriere.setCellValueFactory(new PropertyValueFactory<>("Descriere"));
        columnDeadline.setCellValueFactory(new PropertyValueFactory<>("Deadline"));

        setTableView(ctrl.getAll());

        List<String> comboList = new ArrayList<>();
        comboList.add("Id");
        comboList.add("Descriere");
        comboList.add("Deadline");
        comboFiltrare.setItems(FXCollections.observableArrayList(comboList));
        comboFiltrare.setPromptText("Categorii");
    }

    private void setTableView(List list){
        tableView.setItems(FXCollections.observableArrayList(list));
    }

    private void clear(){
        fieldId.setText("");
        fieldDeadline.setText("");
        fieldDescriere.setText("");
        checkFiltrare.setSelected(false);
    }

    public void actionAdauga(MouseEvent mouseEvent) {
        try {
            ctrl.adauga(Integer.parseInt(fieldId.getText()),
                    fieldDescriere.getText(),
                    Integer.parseInt(fieldDeadline.getText()));
        } catch (ValidatorException e) {
            new AlertBox("Eroare Adaugare", e.getMessage());
        }
        clear();
        setTableView(ctrl.getAll());
    }

    public void actionSterge(MouseEvent mouseEvent) {
        try {
            ctrl.remove(Integer.parseInt(fieldId.getText()));
            ctrlN.sterge_laborator(Integer.parseInt(fieldId.getText()));
        } catch (ValidatorException e) {
            new AlertBox("Eroare Stergere", e.getMessage());
        }
        clear();
        setTableView(ctrl.getAll());
    }

    public void actionModifica(MouseEvent mouseEvent) {
        try {
            ctrl.modifica(Integer.parseInt(fieldId.getText()),
                    Integer.parseInt(fieldDeadline.getText()));
        } catch (ValidatorException e) {
            new AlertBox("Eroare modifica", e.getMessage());
        }
        clear();
        setTableView(ctrl.getAll());
    }

    public void checkSelected(ActionEvent actionEvent) {
        if(checkFiltrare.isSelected()) {
            if (!comboFiltrare.getSelectionModel().isEmpty()) {
                if (comboFiltrare.getValue().equals("Id")) {
                    if (!fieldId.getText().equals(""))
                        setTableView(ctrl.filterID(Integer.parseInt(fieldId.getText())));
                }
                if (comboFiltrare.getValue().equals("Descriere")) {
                    setTableView(ctrl.filterDescriere(fieldDescriere.getText()));
                }
                if (comboFiltrare.getValue().equals("Deadline")) {
                    if (!fieldId.getText().equals(""))
                        setTableView(ctrl.filterDeadline(Integer.parseInt(fieldDeadline.getText())));
                }
            }
        }
        else
            setTableView(ctrl.getAll());
    }

    public void tableSelect(MouseEvent mouseEvent) {
        fieldId.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getId()));
        fieldDescriere.setText(tableView.getSelectionModel().getSelectedItem().getDescriere());
        fieldDeadline.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getDeadline()));
    }

    public void actionUndo(MouseEvent mouseEvent) {
        ctrl.undo();
        clear();
        setTableView(ctrl.getAll());
    }
}
