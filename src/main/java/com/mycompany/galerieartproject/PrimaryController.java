package com.mycompany.galerieartproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PrimaryController {

    @FXML private TextField txtTitre;
    @FXML private TextField txtArtiste;
    @FXML private TextField txtGenre;
    @FXML private TextField txtPrix;
    @FXML private TextField txtQuantite;
    @FXML private CheckBox chkDisponible;

    @FXML private TableView<Oeuvre> tableOeuvres;
    @FXML private TableColumn<Oeuvre, Integer> colId;
    @FXML private TableColumn<Oeuvre, String> colTitre;
    @FXML private TableColumn<Oeuvre, String> colArtiste;
    @FXML private TableColumn<Oeuvre, String> colGenre;
    @FXML private TableColumn<Oeuvre, Double> colPrix;
    @FXML private TableColumn<Oeuvre, Integer> colQuantite;
    @FXML private TableColumn<Oeuvre, Boolean> colDisponible;

    @FXML private Button btnAjouter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Button btnVider;

    private final OeuvreService oeuvreService = new OeuvreService();
    private final ObservableList<Oeuvre> oeuvreList = FXCollections.observableArrayList();
    private Oeuvre oeuvreSelectionnee;

    @FXML
    public void initialize() {
        // 1. Associer les colonnes du TableView aux attributs de la classe Oeuvre
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colArtiste.setCellValueFactory(new PropertyValueFactory<>("artiste"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        // 2. Charger les données de la base de données dans le tableau
        rafraichirTableau();
    }

    @FXML
    void handleAjouter() {
        try {
            Oeuvre nouvelleOeuvre = new Oeuvre();
            nouvelleOeuvre.setTitre(txtTitre.getText());
            nouvelleOeuvre.setArtiste(txtArtiste.getText());
            nouvelleOeuvre.setGenre(txtGenre.getText());
            nouvelleOeuvre.setPrix(Double.parseDouble(txtPrix.getText()));
            nouvelleOeuvre.setQuantite(Integer.parseInt(txtQuantite.getText()));
            nouvelleOeuvre.setDisponible(chkDisponible.isSelected());

            oeuvreService.saveOeuvre(nouvelleOeuvre);
            rafraichirTableau();
            handleVider();
        } catch (NumberFormatException e) {
            System.err.println("Erreur de saisie dans le prix ou la quantité.");
        }
    }

    @FXML
    void handleModifier() {
        if (oeuvreSelectionnee != null) {
            try {
                oeuvreSelectionnee.setTitre(txtTitre.getText());
                oeuvreSelectionnee.setArtiste(txtArtiste.getText());
                oeuvreSelectionnee.setGenre(txtGenre.getText());
                oeuvreSelectionnee.setPrix(Double.parseDouble(txtPrix.getText()));
                oeuvreSelectionnee.setQuantite(Integer.parseInt(txtQuantite.getText()));
                oeuvreSelectionnee.setDisponible(chkDisponible.isSelected());

                oeuvreService.updateOeuvre(oeuvreSelectionnee);
                rafraichirTableau();
                handleVider();
            } catch (NumberFormatException e) {
                System.err.println("Erreur de saisie dans le prix ou la quantité.");
            }
        }
    }

    @FXML
    void handleSupprimer() {
        if (oeuvreSelectionnee != null) {
            oeuvreService.deleteOeuvre(oeuvreSelectionnee.getId());
            rafraichirTableau();
            handleVider();
        }
    }

    @FXML
    void handleTableSelection(MouseEvent event) {
        oeuvreSelectionnee = tableOeuvres.getSelectionModel().getSelectedItem();
        if (oeuvreSelectionnee != null) {
            txtTitre.setText(oeuvreSelectionnee.getTitre());
            txtArtiste.setText(oeuvreSelectionnee.getArtiste());
            txtGenre.setText(oeuvreSelectionnee.getGenre());
            txtPrix.setText(String.valueOf(oeuvreSelectionnee.getPrix()));
            txtQuantite.setText(String.valueOf(oeuvreSelectionnee.getQuantite()));
            chkDisponible.setSelected(oeuvreSelectionnee.isDisponible());
        }
    }

    @FXML
    void handleVider() {
        txtTitre.clear();
        txtArtiste.clear();
        txtGenre.clear();
        txtPrix.clear();
        txtQuantite.clear();
        chkDisponible.setSelected(true);
        tableOeuvres.getSelectionModel().clearSelection();
        oeuvreSelectionnee = null;
    }

    private void rafraichirTableau() {
        oeuvreList.clear();
        List<Oeuvre> obBase = oeuvreService.getAllOeuvres();
        if (obBase != null) {
            oeuvreList.addAll(obBase);
        }
        tableOeuvres.setItems(oeuvreList);
    }
}