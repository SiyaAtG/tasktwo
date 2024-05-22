import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class XMLReader {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Create a handler for SAX events
            XMLHandler handler = new XMLHandler();

            File xmlFile = new File("input.xml");
            saxParser.parse(xmlFile, handler);

            // Get the user-selected fields
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

            // Print JSON output
            List<Map<String, String>> records = handler.getRecords();
            System.out.println("["); // Start of JSON array
            for (int i = 0; i < records.size(); i++) {
                Map<String, String> record = records.get(i);
                System.out.println("  {"); // Start of JSON object for each record
                for (int j = 0; j < selectedFields.length; j++) {
                    String field = selectedFields[j];
                    String value = record.getOrDefault(field, "Not found");
                    System.out.println("    \"" + field + "\": \"" + value + "\"");
                    if (j < selectedFields.length - 1) {
                        System.out.println(",");
                    }
                }
                System.out.println("  }"); // End of JSON object for each record
                if (i < records.size() - 1) {
                    System.out.println(",");
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

class XMLHandler extends DefaultHandler {
    private List<Map<String, String>> records;
    private Map<String, String> currentRecord;
    private StringBuilder data;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("record")) {
            currentRecord = new HashMap<>();
            if (records == null) {
                records = new ArrayList<>();
            }
        }
        data = new StringBuilder();
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("record")) {
            records.add(currentRecord);
        } else {
            currentRecord.put(qName, data.toString());
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }

    public List<Map<String, String>> getRecords() {
        return records;
    }
}

