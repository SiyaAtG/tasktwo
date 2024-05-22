import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.Scanner;

public class XMLReader {
    public static void main(String[] args) {
        try {
            // Load the XML file
            File xmlFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            
            // Prompt the user to specify which fields they want to view
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which fields do you want to view? (name, postalZip, region, country, address, list)");
            // Read the user input and split it into an array of selected fields
            String[] selectedFields = scanner.nextLine().split("\\s*,\\s*");
            
            // Get the list of record elements from the XML document
            NodeList recordList = doc.getElementsByTagName("record");
            // Iterate over each record element
            for (int i = 0; i < recordList.getLength(); i++) {
                Node recordNode = recordList.item(i);
                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element recordElement = (Element) recordNode;
                    System.out.println("Record " + (i+1) + ":");
                    
                    // Iterate over each selected field
                    for (String field : selectedFields) {
                        // Get the list of elements with the current field name
                        NodeList fieldList = recordElement.getElementsByTagName(field);
                        // If the field exists in the current record
                        if (fieldList.getLength() > 0) {
                            // Get the first occurrence of the field
                            Node fieldNode = fieldList.item(0);
                            // Print the field name and its value
                            System.out.println(field + ": " + fieldNode.getTextContent());
                        } else {
                            // If the field doesn't exist in the current record, print "Not found"
                            System.out.println(field + ": Not found");
                        }
                    }
                    // Separate records with a line
                    System.out.println("---------------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

