package application;

public class Range {
	private double min;
	private double max;
	public Range(double min, double max) {
		super();
		this.min = min;
		this.max = max;
	}

    public String toString() {
		return "[" +min + ","+max+"]";
    }
	
}
