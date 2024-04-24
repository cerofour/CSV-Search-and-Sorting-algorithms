package pe.edu.utp.csvsorterandsearcher.CSV;

/**
 * This class represents a header of a CSV file, consisting of a field name and type.
 * The header name is a string that identifies the column in the CSV file, while the
 * field type specifies the type of data expected in that column.
 */
public class CSVHeader {
	public String name;
	public FieldType type;

	/**
	 * Constructor of the CSVHeader class.
	 * @param n the header name
	 * @param ty the header data type
	 */
	CSVHeader(String n, FieldType ty) {
		name = n;
		type = ty;
	}

	/**
	 * Sets the header name
	 * @param name the header name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the header data type
	 * @param type the header data type
	 */
	public void setType(FieldType type) {
		this.type = type;
	}
}
