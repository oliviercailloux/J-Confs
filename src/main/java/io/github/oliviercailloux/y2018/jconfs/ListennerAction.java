package io.github.oliviercailloux.y2018.jconfs;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.primitives.Doubles;

public class ListennerAction {
	
	/**
	 * check if the character is a letter or "-" or a whitespace, if not you can't
	 * put the character
	 */
	public static void CheckTextInput(VerifyEvent e) {
		if (!e.text.matches("[a-zA-ZÀ-ú -]*")) {
			e.doit = false;
		}
	}
	
	/**
	 * check that the fee is a double, if not you can't put the character
	 */
	public static void CheckDoubleInput(VerifyEvent e) {
		Text txtOfField=(Text) e.widget;
		if (Doubles.tryParse(txtOfField.getText()+e.text) == null) {
			e.doit = false;
		}
	}
}
