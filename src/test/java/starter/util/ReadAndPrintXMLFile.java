package starter.util;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;

public class ReadAndPrintXMLFile {

	public String[][] carregaXML(File file, int noRows, int noColums){
        String[][] numbersArray = new String[noRows][noColums];

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            Document document = docBuilderFactory.newDocumentBuilder().parse(file);
            Element rootElement = document.getDocumentElement(); 

            NodeList rowList = rootElement.getElementsByTagName("Row");
            if ((rowList != null)) 
                for (int i = 0; i < rowList.getLength(); i++) {
                    NodeList columnsList = rowList.item(i).getChildNodes();
                    if ((columnsList != null)) {
                    	
                    	//Coluna "gameFile"
                    	numbersArray[i][0] = file.getName();
                    	if(columnsList.item(0).getTextContent().contains("ui_codex_name") || 
                    			columnsList.item(0).getTextContent().contains("_uiName") || 
        						columnsList.item(2).getTextContent().contains("&"))
                    	{
                        	numbersArray[i][4] = "tratar";
                        }
                    	
                        for (int j = 0; j < columnsList.getLength(); j++) {
                            String valorCell = columnsList.item(j).getTextContent().trim().replace("\n", "").replace("'", "´");
                            System.out.println("(" +i + "," + j + ") " + valorCell);

                            numbersArray[i][j+1] = valorCell;
                        }
                    }
                }

            return numbersArray;
        }
        catch (Exception c){}
        return null;
	}
}