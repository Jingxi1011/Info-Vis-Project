package application;

public class Data{
	private double [] values;
	private String label = "";
		
	public Data(double[] values, String label) {
		super();
		this.values = values;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public double getValue(int index){
		return values[index];
	}
	
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(label);
		stringBuffer.append('[');
		for (double value : values) {
			stringBuffer.append(value);
			stringBuffer.append(',');
		}
		
		stringBuffer.append(']');
	return stringBuffer.toString();
	}

}
