package cz.it4i.swing_javafx_ui;

import java.io.Closeable;

public interface CloseableControl extends Closeable {

	@Override
	void close();

}
