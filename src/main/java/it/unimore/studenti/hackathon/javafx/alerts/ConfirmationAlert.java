package it.unimore.studenti.hackathon.javafx.alerts;

import it.unimore.studenti.hackathon.javafx.utils.JFXDefs;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.Optional;

@SuppressWarnings("unused")
public final class ConfirmationAlert extends Alert {

    //Attributes
    public final boolean result;

    //Constructors
    public ConfirmationAlert(String title, String header, String content){
        super(AlertType.CONFIRMATION);
        this.setResizable(true);
        ((Stage) getDialogPane().getScene().getWindow()).getIcons().add(JFXDefs.AppInfo.LOGO);
        if(title!=null) setTitle(title);
        if(header!=null) setHeaderText(header);
        if(content!=null) {
            TextArea area = new TextArea(content);
            area.setFocusTraversable(false);
            area.setWrapText(true);
            area.setEditable(false);
            getDialogPane().setContent(area);
        }
        getDialogPane().getScene().getStylesheets().clear();
        getDialogPane().getScene().getStylesheets().add(JFXDefs.Resources.CSS.CSS_THEME);
        Optional<ButtonType> result = showAndWait();
        this.result = result.isPresent() && result.get().equals(ButtonType.OK);
    }
    public ConfirmationAlert(String header, String content){
        this(null, header, content);
    }
    public ConfirmationAlert(){
        this(null,null,null);
    }

}