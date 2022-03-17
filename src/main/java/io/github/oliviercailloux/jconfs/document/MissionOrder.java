package io.github.oliviercailloux.jconfs.document;

import java.io.InputStream;
import org.odftoolkit.simple.SpreadsheetDocument;

/**
 * 
 * @author huong,camille
 *
 */
public class MissionOrder {
	public static void main(String[] args) throws Exception {
		new MissionOrder().generateSpreadsheetDocument();
	}

	/**
	 * open and close a file .ods
	 * 
	 * @throws Exception
	 */
	private void generateSpreadsheetDocument() throws Exception {
		try (InputStream inputStream = MissionOrder.class.getResourceAsStream("ordre_de_mission.ods");
				SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument.loadDocument(inputStream)) {
			System.out.println("File opened and ready to close!!!");
			inputStream.close();
		} catch (Exception e) {
			System.out.println("error opening file " + e.getMessage());
		}

	}
}
