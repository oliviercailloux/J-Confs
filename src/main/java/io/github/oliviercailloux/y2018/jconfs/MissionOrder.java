package io.github.oliviercailloux.y2018.jconfs;

import java.io.IOException;
import java.io.InputStream;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
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
 * @throws Exception
 */
	private void generateSpreadsheetDocument() throws Exception {
		try (InputStream inputStream = MissionOrder.class.getResourceAsStream("missionOrder.ods");
				SpreadsheetDocument spreadsheetDoc = SpreadsheetDocument.loadDocument(inputStream)) {
			System.out.println("File opened and ready to close!!!");
			//Cell positionCell = spreadsheetDoc.getTableByName("B").getCellByPosition("A1");
			//positionCell.setStringValue("ploum");
			//spreadsheetDoc.save("demo9s.ods");
			
	        inputStream.close();
		}
		catch (Exception e){
			System.out.println("error opening file " + e.getMessage());
		}
		
		
	}
}


