
package cz.it4i.swing_javafx_ui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.Supplier;

import javax.swing.JDialog;

import javafx.scene.Parent;

public abstract class FXFrame<T extends Parent & CloseableControl> extends
	JDialog
{

	private static final long serialVersionUID = 1L;
	private final JFXPanel<T> fxPanel;
	
	private boolean controlClosed;

	public FXFrame(Supplier<T> fxSupplier) {
		this(null, fxSupplier);
	}

	public FXFrame(Window parent, Supplier<T> fxSupplier) {
		super(parent, ModalityType.MODELESS);
		fxPanel = new JFXPanel<>(fxSupplier);
		init();
		if (fxPanel.getControl() instanceof InitiableControl) {
			InitiableControl control = (InitiableControl) fxPanel.getControl();
			control.init(this);
		}
	}

	public JFXPanel<T> getFxPanel() {
		return fxPanel;
	}

	public void executeAdjustment(Runnable command) {
		JavaFXRoutines.runOnFxThread(() -> {
			command.run();
		});
	}
	
	@Override
	public void dispose() {
		closeControlIfNotClosed();
		super.dispose();
	}

	synchronized private void closeControlIfNotClosed() {
	 if(!controlClosed) {
			getFxPanel().getControl().close();
			controlClosed = true;
		}
	}
	
	private void init() {
		if (fxPanel.getControl() instanceof ResizeableControl) {
			ResizeableControl resizable = (ResizeableControl) fxPanel.getControl();
			addComponentListener(new ComponentAdapter() {

				@Override
				public void componentResized(ComponentEvent e) {
					resizable.setSize(e.getComponent().getSize().getWidth(), e
						.getComponent().getSize().getHeight());
				}
			});
		}
		this.setLayout(new BorderLayout());
		this.add(fxPanel, BorderLayout.CENTER);
		this.pack();
		SwingRoutines.centerOnScreen(this);
	}
}
