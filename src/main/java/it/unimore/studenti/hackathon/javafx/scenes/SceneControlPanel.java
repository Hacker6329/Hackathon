package it.unimore.studenti.hackathon.javafx.scenes;

import it.italiandudes.idl.common.Logger;
import it.unimore.studenti.hackathon.data.ClientData;
import it.unimore.studenti.hackathon.javafx.components.SceneController;
import it.unimore.studenti.hackathon.javafx.controllers.ControllerSceneControlPanel;
import it.unimore.studenti.hackathon.javafx.utils.JFXDefs;
import it.unimore.studenti.hackathon.utils.Defs;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public final class SceneControlPanel {

    // Scene Generator
    @NotNull
    public static SceneController getScene(@NotNull final ArrayList<@NotNull ClientData> clientData) {
        try {
            FXMLLoader loader = new FXMLLoader(Defs.Resources.get(JFXDefs.Resources.FXML.FXML_CONTROL_PANEL));
            Parent root = loader.load();
            ControllerSceneControlPanel controller = loader.getController();
            controller.setClientData(clientData);
            controller.configurationComplete();
            return new SceneController(root, controller);
        } catch (IOException e) {
            Logger.log(e);
            System.exit(-1);
            return null;
        }
    }
}
