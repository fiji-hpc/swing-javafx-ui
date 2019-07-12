package cz.it4i.swing_javafx_ui;

import java.util.function.Supplier;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public abstract class FXFrameNative<T extends Parent&CloseableControl> {

	private JFXPanel<T> fxPanel;
	private Stage stage;


	public FXFrameNative(Supplier<T> fxSupplier) {
		new javafx.embed.swing.JFXPanel();
		JavaFXRoutines.runOnFxThread(() -> {
			stage = new Stage();
			stage.setTitle("My New Stage Title");
			stage.setScene(new Scene(fxSupplier.get(), 450, 450));
			stage.show();
		});
	}
	
	public void setVisible(boolean b) {
		JavaFXRoutines.runOnFxThread(() -> {
			if(b) {
				stage.show();
			} else {
				stage.hide();
			}
		});
	}	

	


	public JFXPanel<T> getFxPanel() {
		return fxPanel;
	}
}
