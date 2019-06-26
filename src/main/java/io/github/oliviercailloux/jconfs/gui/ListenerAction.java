package io.github.oliviercailloux.jconfs.gui;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.primitives.Doubles;

/**
 * @author nikola 
 * Class that contains common methods for verification of text fields
 */
public class ListenerAction {

	/**
	 * check if the character is a letter or "-" or a whitespace, if not you can't
	 * put the character
	 */
	public static void checkTextInput(VerifyEvent e) {
		if (!e.text.matches("[a-zA-ZÀ-ú -]*")) {
			e.doit = false;
		}
	}

	/**
	 * check if the character is an integer, if not you can't
	 * put the character
	 */
	public static void checkNumberInput(VerifyEvent e) {
		if (e.text.matches("[a-zA-ZÀ-ú -]*")) {
			e.doit = false;
		}
	}

	/**
	 * check that the fee is a double, if not you can't put the character
	 */
	public static void checkDoubleInput(VerifyEvent e) {
		Text txtOfField = (Text) e.widget;
		if (Doubles.tryParse(txtOfField.getText() + e.text) == null) {
			e.doit = false;
		}
	}

	/**
	 * Method that block the field
	 */
	public static void inputFieldBlock(VerifyEvent e) {
		e.doit = false;      
	}

	/**
	 * Method that unblock the field
	 */
	public static void inputFieldUnblock(VerifyEvent e) {
		e.doit = true;      
	}
}
