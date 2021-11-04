
package cz.it4i.swing_javafx_ui;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SimpleDialog {

	private SimpleDialog() {
		// Empty private constructor to prevent creation of new object.
	}

	public static void showWarning(String header, String message) {
		showAlert(AlertType.WARNING, header, message, MaterialDesign.MDI_ALERT);
	}

	public static void showError(String header, String message) {
		showAlert(AlertType.ERROR, header, message, MaterialDesign.MDI_CLOSE_BOX);
	}

	public static void showInformation(String header, String message) {
		showAlert(AlertType.INFORMATION, header, message,
			MaterialDesign.MDI_ALERT_CIRCLE);
	}

	private static void showAlert(AlertType type, String header, String message,
		Ikon icon)
	{
		Alert alert = new Alert(type);
		alert.setTitle(getDialogTitlePrefix(type) + " Dialog");
		alert.setHeaderText(header);
		alert.setContentText(message);

		// Add icon:
		Image myImage = IconHelperMethods.convertIkonToImage(icon);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(myImage);

		alert.showAndWait();
	}

	private static String getDialogTitlePrefix(AlertType alertType) {
		switch (alertType) {
			case INFORMATION:
				return "Information";
			case WARNING:
				return "Warning";
			case ERROR:
				return "Error";
			default:
				throw new IllegalArgumentException("Not supporter alert type " +
					alertType);
		}
	}

	public static File fileChooser(Stage stage, String title) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);

		// Set icon:
		Image myImage = IconHelperMethods.convertIkonToImage(
			MaterialDesign.MDI_FILE);
		stage.getIcons().add(myImage);

		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			return selectedFile;
		}
		return null;
	}

	public static File directoryChooser(Stage stage, String title) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle(title);

		// Set icon:
		Image myImage = IconHelperMethods.convertIkonToImage(
			MaterialDesign.MDI_FOLDER);
		stage.getIcons().add(myImage);

		File selectedFile = directoryChooser.showDialog(stage);
		if (selectedFile != null) {
			return selectedFile;
		}
		return null;
	}

	public static void showException(String header, String message,
		Exception ex)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception");
		alert.setHeaderText(header);
		alert.setContentText(message);

		// Create expandable Exception text field.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		// Set icon:
		Image myImage = IconHelperMethods.convertIkonToImage(
			MaterialDesign.MDI_CLOSE_BOX);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(myImage);

		alert.showAndWait();
	}
}
