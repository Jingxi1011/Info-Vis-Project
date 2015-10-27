package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import application.Data;
import application.Range;

public class Model {
// read data from file
  private ArrayList<String> labels = new ArrayList<String>();
  private ArrayList<Data> list  = new ArrayList<Data>();
  private ArrayList<Range> ranges = new ArrayList<Range>();
  private int line_num = 0;
  private int dim;
  
  public ArrayList<String> getLabels() {
		return labels;
	}
	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}
	public ArrayList<Data> getList() {
		return list;
	}
	public void setList(ArrayList<Data> list) {
		this.list = list;
	}
	public ArrayList<Range> getRanges() {
		return ranges;
	}
	public void setRanges(ArrayList<Range> ranges) {
		this.ranges = ranges;
	}
	public Model() {
		importValues();
	}
	public Iterator<Data> iterator() {
		return list.iterator();
	}
	public int getDim() {
		return dim;
	}
	public void setDim(int dim) {
		this.dim = dim;
	}

  public void importValues(){
		File file = new File("GDP.txt");
	   
	    try {
	    	 String thisLine = null;
	    	 BufferedReader br = new BufferedReader(new FileReader(file));
	    	 BufferedReader bt= new BufferedReader(new FileReader(file));
	         try {
	        	 //Import Labels
	        	 thisLine = br.readLine();
				 String l [] = thisLine.split(";");
				 for (int i = 1; i < l.length; i++) 
					 labels.add(l[i]); // import labels excluding name
				 while((thisLine = br.readLine()) != null){
					 line_num++;
				 }
				 
				  // Prepare Ranges
				 double lowRanges [] = new double[line_num];
				 for (int i = 0; i < lowRanges.length; i++) 
					 lowRanges[i] = Double.MAX_VALUE;
							 
				 double highRanges [] = new double[line_num];
			     for (int i = 0; i < highRanges.length; i++) 
			    	 highRanges[i] = Double.MIN_VALUE;
			     
			     String thatLine = null;
			     thatLine = bt.readLine();
			     line_num = 0;
	        	 // Import Data and adapt Ranges
				 while ((thatLine = bt.readLine()) != null) { // while loop begins here
					 
					 String values [] = thatLine.split(";");
					 double dValues [] = new double[values.length -1];
					 
					 for (int j =1; j < values.length; j++) {
						 
						 dValues[j-1] = Double.parseDouble(values[j]);
						 
						 if (dValues[j-1] <  lowRanges[line_num]) lowRanges[line_num] = dValues[j-1];
						 if (dValues[j-1] >  highRanges[line_num]) highRanges[line_num] = dValues[j-1];
					 }
					 
					 list.add(new Data(dValues, values[0]));
					 line_num ++;
					
	   			}
				for (int i = 0; i < line_num; i++) {
					ranges.add(new Range(lowRanges[i],highRanges[i]));
				} 
				//System.out.println(list);
				System.out.println(ranges);
				//System.out.println(labels);
			} catch (IOException e) {
				e.printStackTrace();
			} // end while 

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
}