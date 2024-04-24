package pe.edu.utp.csvsorterandsearcher.CSV;

/**
 * Enumeration representing the different types of fields that can be present in a CSV file.
 * This enum defines the possible data types: String, Double, Date, and Boolean.
 */
public enum FieldType {
	String,       // String
	Double,       // Double
	Date,         // LocalDate
	Boolean,	  // Boolean

	// maybe add support for time (hours-minutes-seconds) ?
}
