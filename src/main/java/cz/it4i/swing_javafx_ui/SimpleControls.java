
package cz.it4i.swing_javafx_ui;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;

public class SimpleControls {

	private SimpleControls() {
		// Private constructor to hide the implicit public one.
	}

	private static void setValue(Spinner<Integer> spinner, int lowerBound) {
		final int cleanValue = lowerBound;
		JavaFXRoutines.runOnFxThread(() -> {
			spinner.getEditor().setText("" + cleanValue);
			spinner.getValueFactory().setValue(cleanValue);
		});
	}

	public static void spinnerIgnoreNoneNumericInput(Spinner<Integer> spinner,
		int lowerBound, int upperBound)
	{
		spinner.getEditor().focusedProperty().addListener(event -> {
			boolean isFocused = spinner.isFocused();
			boolean isEmpty = spinner.getEditor().getText().isEmpty();
			if (!isFocused && isEmpty) {
				setValue(spinner, lowerBound);
			}
		});

		spinner.getEditor().textProperty().addListener((
			ObservableValue<? extends String> observable, String oldValue,
			String newValue) -> {
			if (newValue.isEmpty()) {
				return;
			}

			// Value should be numeric only:
			if (!newValue.matches("\\d*")) {
				JavaFXRoutines.runOnFxThread(() -> spinner.getEditor().setText(newValue
					.replaceAll("[^\\d]", "")));
			}
			// Value should be within the boundaries.
			try {
				int temp = Integer.parseInt(newValue);
				if (temp < lowerBound) {
					temp = lowerBound;
				}
				else if (temp > upperBound) {
					temp = upperBound;
				}
				setValue(spinner, temp);
			}
			catch (Exception e) {
				// Value should be an integer.
				JavaFXRoutines.runOnFxThread(() -> spinner.getEditor().setText("" +
					oldValue));
			}
		});
	}
}
