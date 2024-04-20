package pe.edu.utp.csvsorterandsearcher.Utilities;

import pe.edu.utp.csvsorterandsearcher.CSV.CSVHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class ExportModes {

    private static StringBuilder content;

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
        for (int i = 0; i < data[0].length;i++){
            for (int j = 0; j < headers.length; j++) {
                content.append(String.format("%s,", data[j][i]));
            }
            content.deleteCharAt(content.length()-1);
            if (data[0].length - 1 != i)
                content.append("\n");
        }
        Utilities.saveData(content, path);
        content.delete(0, content.length()-1);
    }

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
        for (CSVHeader header : headers) {
            content.append(String.format("\t\t\t\t<th>&nbsp;&nbsp;%s&nbsp;&nbsp;</th>\n", header.name));
        }
        content.append("\t\t\t</tr>\n");

        for (int i = 0; i < data[0].length;i++){
            content.append("\t\t\t<tr>\n");
            for (int j = 0; j < headers.length; j++) {
                content.append(String.format("""
                        <td><center>&nbsp;&nbsp;%s&nbsp;&nbsp;</center></td>""", data[j][i]));
            }
            content.append("\t\t\t</tr>\n");
        }
        content.append("""
                		</table>
                	</center>
                </body>
                </html>""");

        Utilities.saveData(content, path);
        content.delete(0, content.length()-1);

    }

    public static void ExportAsTXT(){

    }

    public static void ExportAsXML(CSVHeader[] headers, String[][] data, String path){
        content = new StringBuilder();
        content.append("<data>\n\t<header>\n");
        for (CSVHeader header: headers){
            content.append(String.format("\t\t<column>%s</column>\n", header.name));
        }
        content.append("\t</header>\n");
        for (int i = 0; i < data[0].length;i++){
            content.append("\t<row>\n");
            for (int j = 0; j < headers.length; j++) {
                content.append(String.format("\t\t<rowdata>%s</rowdata>\n", data[j][i]));
            }
            content.append("\t</row>\n");
        }
        content.append("</data>");
        Utilities.saveData(content, path);
        content.delete(0, content.length());
        System.out.println(content.toString());

    }

    public static void ExportAsJSON(CSVHeader[] headers, String[][] data, String path){
        content = new StringBuilder();
        content.append("{\n\t\"data\": [\n\t{\n");
        Stream<String> stream;
        for (int i = 0; i < headers.length; i++) {
            stream = Stream.of(data[i]).map(element -> "\""+element+"\"");
            content.append(String.format("\t\t\"column\": \"%s\",\n\t\t\"rows\": ", headers[i].name));
            content.append(Arrays.toString(stream.toArray()));
            content.append("\n\t}");
            if(headers.length - 1 != i)
                content.append(",\n\t{");
            content.append("\n");
        }
        content.append("\n\t]\n}");
        Utilities.saveData(content, path);
        content.delete(0, content.length()-1);
        // clean content data
    }

}
