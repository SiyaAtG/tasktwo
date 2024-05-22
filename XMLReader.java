import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class XMLReader {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Which fields do you want to view? (name, postalZip, region, country, address, list)");
            String[] selectedFields = scanner.nextLine().split("\\s*,\\s*");

            // Validate selected fields
            for (String field : selectedFields) {
                if (!isValidField(field)) {
                    System.out.println("Invalid field: " + field);
                    return; // Exit the program if an invalid field is found
                }
            }

            NodeList recordList = doc.getElementsByTagName("record");

            System.out.println("["); // Start of JSON array

            for (int i = 0; i < recordList.getLength(); i++) {
                Node recordNode = recordList.item(i);
                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element recordElement = (Element) recordNode;

                    // Start of JSON object for each record
                    System.out.println("  {");

                    for (int j = 0; j < selectedFields.length; j++) {
                        String field = selectedFields[j];
                        NodeList fieldList = recordElement.getElementsByTagName(field);
                        if (fieldList.getLength() > 0) {
                            Node fieldNode = fieldList.item(0);
                            String fieldValue = fieldNode.getTextContent();
                            // Output field name and value as JSON key-value pair
                            System.out.println("    \"" + field + "\": \"" + fieldValue + "\"");
                            // Add comma if not the last field
                            if (j < selectedFields.length - 1) {
                                System.out.println(",");
                            }
                        } else {
                            // Output field name with "Not found" value as JSON key-value pair
                            System.out.println("    \"" + field + "\": \"Not found\"");
                            if (j < selectedFields.length - 1) {
                                System.out.println(",");
                            }
                        }
                    }

                    // End of JSON object for each record
                    System.out.println("  }");

                    // Add comma if not the last record
                    if (i < recordList.getLength() - 1) {
                        System.out.println(",");
                    }
                }
            }

            System.out.println("]"); // End of JSON array

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Function to validate selected fields
    private static boolean isValidField(String field) {
        String[] validFields = {"name", "postalZip", "region", "country", "address", "list"};
        for (String validField : validFields) {
            if (validField.equals(field)) {
                return true;
            }
        }
        return false;
    }
}

