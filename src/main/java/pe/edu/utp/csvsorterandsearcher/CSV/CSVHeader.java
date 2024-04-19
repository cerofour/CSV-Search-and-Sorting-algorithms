package pe.edu.utp.csvsorterandsearcher.CSV;

import java.lang.reflect.Field;

public class CSVHeader {
	public String name;
	public FieldType type;
	public int maxLength;

	CSVHeader(String n, FieldType ty, int ml) {
		name = n;
		type = ty;
		maxLength = ml;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(FieldType type) {
		this.type = type;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
}
