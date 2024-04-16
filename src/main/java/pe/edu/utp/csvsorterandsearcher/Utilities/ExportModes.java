package pe.edu.utp.csvsorterandsearcher.Utilities;

import java.util.ArrayList;

public class ExportModes {

    private static StringBuilder content;

    public static void ExportAsCSV(){

    }

    public static void ExportAsHTML(String[] headers, ArrayList<String[]> data, String path) {
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
                	<center><h3>DATA EXPORT:</h3></center>
                	<center>
                 		<table border="2px">
        """);
        content.append("<tr style=\"color: chocolate;\">\n");
        for (String header : headers) {
            content.append(String.format("\t\t\t\t<th>%s</th>\n", header));
        }
        content.append("\t\t\t</tr>\n");

        for (int i = 0; i < data.size();i++){
            content.append("\t\t\t<tr>\n");
            for (String dataRow : data.get(i)){
                content.append(String.format("""
                        <td><center>%s</center></td>""", dataRow));
            }
            content.append("\t\t\t</tr>\n");
        }
        content.append("""
                		</table>
                	</center>
                </body>
                </html>""");

        Utilities.saveData(content, path);

    }

    public static void ExportAsTXT(){

    }

    public static void ExportAsXML(){

    }

    public static void ExportAsJSON(String[] headers, ArrayList<String[]> data, String path){

    }

}
