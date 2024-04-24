package pe.edu.utp.csvsorterandsearcher.Utilities;

import pe.edu.utp.csvsorterandsearcher.CSV.CSVHeader;
import pe.edu.utp.csvsorterandsearcher.CSV.FieldType;

import java.util.stream.Stream;

/**
 * This class provides static methods to export data in different formats
 * like CSV, HTML, TXT, XML and JSON. Data can be exported by providing
 * column headers and data in the form of string arrays.
 * TODO: The two-dimensional array must have the data horizontally
 *       so that it can be exported correctly
 *
 */
public class ExportModes {

    private static StringBuilder content;

    /**
     * Export the data as a CSV file.
     * @param headers column headers
     * @param data data to export (Data horizontally)
     * @param path output file path
     */
    public static void ExportAsCSV(CSVHeader[] headers, String[][] data, String path){
        content = new StringBuilder();
        Stream<CSVHeader> stream = Stream.of(headers);
        content.append(
                String.join(",", stream.map(i -> i.name)
                        .toArray(String[]::new)));
        content.append("\n");
        // String[] -> refers to the String[] constructor
        // :: -> reference operator
        // new -> create instance
        for (String[] row: data)
            content.append(String.join(",", row)).append("\n");

        Utilities.saveData(content, path);
        content.delete(0, content.length());
    }

    /**
     * Export the data as a HTML file.
     * @param headers column headers
     * @param data data to export (Data horizontally)
     * @param path output file path
     */
    public static void ExportAsHTML(CSVHeader[] headers, String[][] data, String path) {
        content = new StringBuilder();
        content.append("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                	<meta charset="UTF-8">
                	<meta name="viewport" content="width=device-width, initial-scale=1.0">
                	<title>DATA EXPORT</title>
                </head>
                <body>
                	<center><h3>DATA EXPORT BY CSVSorterAndSearcher:</h3></center>
                	<center>
                 		<table border="2px">
        """);
        content.append("<tr style=\"color: RED;\"><br/>\n");
        for (CSVHeader header : headers)
            content.append(String.format("\t\t\t\t<th>&nbsp;&nbsp;%s&nbsp;&nbsp;</th>\n", header.name));

        content.append("\t\t\t</tr>\n");

        for (String[] row : data) {
            content.append("\t\t\t<tr>\n");
            for (String rowdata : row)
                content.append(String.format("""
                        <td><center>&nbsp;&nbsp;%s&nbsp;&nbsp;</center></td>""", rowdata));
            content.append("\t\t\t</tr>\n");
        }

        content.append("""
                		</table>
                	</center>
                </body>
                </html>""");

        Utilities.saveData(content, path);
        content.delete(0, content.length());

    }

    /**
     * Export the data as a TXT file.
     * @param headers column headers
     * @param data data to export (Data horizontally)
     * @param path output file path
     */
    public static void ExportAsTXT(CSVHeader[] headers, String[][] data, String path){
        content = new StringBuilder();
        content.append("// Data Export By: CSVSorterAndSearcher\n\n");
        content.append("[*] Headers:\n");
        content.append(
                String.join(" ", Stream.of(headers).map(i -> i.name).toArray(String[]::new))
        );
        content.append("\n\n[*] Rows: \n");

        for (String[] row: data)
            content.append(String.join(" ", row)).append("\n");

        Utilities.saveData(content, path);
        content.delete(0, content.length());
    }

    /**
     * Export the data as a XML file.
     * @param headers column headers
     * @param data data to export (Data horizontally)
     * @param path output file path
     */
    public static void ExportAsXML(CSVHeader[] headers, String[][] data, String path){
        content = new StringBuilder();
        content.append("<data>\n\t<header>\n");
        for (CSVHeader header: headers)
            content.append(String.format("\t\t<column>%s</column>\n", header.name));

        content.append("\t</header>\n");

        for (String[] row : data) {
            content.append("\t<row>\n");
            for (String rowdata : row)
                content.append(String.format("\t\t<rowdata>%s</rowdata>\n", rowdata));
            content.append("\t</row>\n");
        }

        content.append("</data>");
        Utilities.saveData(content, path);
        content.delete(0, content.length());
    }

    /**
     * Export the data as a JSON file.
     * @param headers column headers
     * @param data data to export (Data horizontally)
     * @param path output file path
     */
    public static void ExportAsJSON(CSVHeader[] headers, String[][] data, String path){
        content = new StringBuilder();
        content.append("{\n\t\"data\": [\n\t{\n");

        for (int i = 0; i < headers.length; i++){
            content.append(String.format("\t\t\"column\": \"%s\",\n\t\t\"rows\": ", headers[i].name));
            content.append("[");
            for (String[] datum : data) {
                if (headers[i].type == FieldType.Double)
                    content.append(String.format("%s, ", datum[i]));
                else
                    content.append(String.format("\"%s\", ", datum[i]));
            }
            content.delete(content.length()-2, content.length());
            content.append("]\n\t}");
            if(headers.length - 1 != i)
                content.append(",\n\t{");
            content.append("\n");
        }

        content.append("\n\t]\n}");
        Utilities.saveData(content, path);
        content.delete(0, content.length());
    }

}
