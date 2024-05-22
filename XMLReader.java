import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class XMLReader {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList recordList = doc.getElementsByTagName("record");
            for (int i = 0; i < recordList.getLength(); i++) {
                Node recordNode = recordList.item(i);
                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element recordElement = (Element) recordNode;
                    System.out.println("Record " + (i+1) + ":");
                    System.out.println("Name: " + recordElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Postal/Zip: " + recordElement.getElementsByTagName("postalZip").item(0).getTextContent());
                    System.out.println("Region: " + recordElement.getElementsByTagName("region").item(0).getTextContent());
                    System.out.println("Country: " + recordElement.getElementsByTagName("country").item(0).getTextContent());
                    System.out.println("Address: " + recordElement.getElementsByTagName("address").item(0).getTextContent());
                    System.out.println("List: " + recordElement.getElementsByTagName("list").item(0).getTextContent());
                    System.out.println("---------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

