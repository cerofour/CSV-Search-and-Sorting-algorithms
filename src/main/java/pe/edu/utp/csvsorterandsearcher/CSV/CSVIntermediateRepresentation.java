package pe.edu.utp.csvsorterandsearcher.CSV;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * CSVTable
 * @brief Represents a CSVTable.
 *          For the purposes of this project, a CSV file will NOT be represented
 *          as an array of rows (horizontal) but rather as a collection of N
 *          columns, where N is the number of fields defined by the first line of the CSV file
 *          and the columns are array marked with datatype information that contain the whole
 *          dataset under a specific field.
 */
public class CSVIntermediateRepresentation {
	private CSVHeader[] headers;
	private String[][] columns;


	CSVIntermediateRepresentation(String[] headers, FieldType[] types) {
		this.headers = new CSVHeader[headers.length];

		initializeHeaders(headers, types);

		columns = null;
	}

	private void initializeHeaders(String[] headers, FieldType[] types) {
		for (int i = 0; i < this.headers.length; i++) {
			this.headers[i] = new CSVHeader(headers[i], FieldType.String, 0 );
			//this.headers[i].name = headers[i];
			//this.headers[i].type = types[i];
			//this.headers[i].maxLength = 0;
		}
	}

	public void setTable(String[][] tab) {
		columns = tab;
	}

	public void setMaxLengths(int[] maxLengths) {
		for (int i = 0; i < Math.min(maxLengths.length, headers.length); i++)
			headers[i].maxLength = maxLengths[i];
	}

	public String[] getColumn(int n) {
		return columns[n];
	}

	public String[][] getColumns(){ return columns;}

	public CSVHeader[] getHeaders(){ return headers;}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < headers.length; i++) {
			System.out.printf("");
		}

		for (int i = 0; i < columns.length; i++) {
			for (int j = 0; j < columns[i].length; j++) {
				sb.append(String.format("%s ", columns[i][j]));
			}
			//sb.append(String.join(" ", columns[i])); You can use this to simplify said for
			sb.append("\n");
		}
		return sb.toString();
	}
}
