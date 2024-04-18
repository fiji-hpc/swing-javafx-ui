package cz.it4i.swing_javafx_ui;

import java.awt.Dimension;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.extern.slf4j.Slf4j;

/**
 * JFXPanel makes the link between Swing (IJ) and JavaFX plugin.
 */
@Slf4j
public class SwingAndJavaFXLinker<T extends Parent> extends javafx.embed.swing.JFXPanel {
	private static final long serialVersionUID = 1L;


	private transient final T control;

	public SwingAndJavaFXLinker(Supplier<T> fxSupplier) {
		Platform.setImplicitExit(false);
		control = fxSupplier.get();
		// The call to runLater() avoid a mix between JavaFX thread and Swing thread.
		try {
			JavaFXRoutines.runOnFxThread(this::initFX).get();
		}
		catch (ExecutionException exc) {
			//log.error(exc.getMessage(), exc);
			System.err.println(exc.getMessage());
		}
		catch (InterruptedException exc) {
			Thread.currentThread().interrupt();
			//log.error(exc.getMessage(), exc);
			System.err.println(exc.getMessage());
		}
	}

	private void initFX() {
		// Init the root layout
		// Show the scene containing the root layout.
		Scene scene = new Scene(control);
		this.setScene(scene);
		this.setVisible(true);

		// Resize the JFrame to the JavaFX scene
		Dimension dim = new Dimension((int) scene.getWidth(), (int) scene.getHeight());
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.setPreferredSize(dim);
	}
	
	public T getControl() {
		return control;
	}

}