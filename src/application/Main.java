package application;

import java.io.*;
import java.util.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
//import application.Model;
import application.Range;
import application.Data;

public class Main extends Application {
	public ArrayList<Data> GDP_list  = new ArrayList<Data>();
	public ArrayList<Data> pop_list  = new ArrayList<Data>();
	public ArrayList<Data> QuarterGDP_list  = new ArrayList<Data>();
	public ArrayList<Range> GDP_ranges = new ArrayList<Range>();
	public ArrayList<Range> pop_ranges = new ArrayList<Range>();
	public ArrayList<Range> QuarterGDP_ranges = new ArrayList<Range>();
	public ArrayList<XYChart.Series<String, Number>> GDP_series_list = new ArrayList<XYChart.Series<String, Number>>();
	public ArrayList<XYChart.Series<String, Number>> pop_series_list = new ArrayList<XYChart.Series<String, Number>>();
	public ArrayList<XYChart.Series<String, Number>> QuarterGDP_series_list = new ArrayList<XYChart.Series<String, Number>>();
	public List<Integer> selected_list = new ArrayList<Integer>();
	
	public GridPane LineChart = new GridPane();
	public int province_num = 27;
	public String[] names = new String[province_num];
	public int year_ini = 2010;
	public int year_num = 5;
	public int c = 0;
	public Text linechart_title = new Text();
	public Object previous_target = new Object();
	public boolean same_as_previous = false;

	public void start(Stage stage) {
	  stage.setTitle("Application for Population&GDP Statistics of Chinese Provinces");
	  
	  // Alert Information Window (Exceed 5 selection limit)
	  Alert maximum_alert = new Alert(AlertType.ERROR);
	  maximum_alert.setTitle("Error");
	  maximum_alert.setHeaderText("Hit Maximum Limit!");
	  maximum_alert.setContentText("Please select no more than 5 provinces at one time.");
	  
	  // Alert Information Window (Exceed 5 selection limit)
	  Alert empty_alert = new Alert(AlertType.ERROR);
	  empty_alert.setTitle("Error");
	  empty_alert.setHeaderText("Empty List!");
	  empty_alert.setContentText("Please select at least 1 province.");

	  
	  // Root - StackPane
	  StackPane root = new StackPane();
	  // scene_selection - Scene
	  Scene scene_selection = new Scene(root, 1050, 700);
	  // chart - StackPane
	  StackPane linechart = new StackPane();
	  linechart.setPadding(new Insets(15));
	  // GDP_scene_chart - Scene
	  Scene GDP_scene_chart = new Scene(linechart,1050, 700);
	  
	  // chart - StackPane
	  StackPane barchart = new StackPane();
	  barchart.setPadding(new Insets(20,20,20,20));
	  // QuarterGDP_scene_chart - Scene
	  Scene QuarterGDP_scene_chart = new Scene(barchart,1050,700);
	  
	  // img_selection - GridPane
	  GridPane img_selection = new GridPane();
	  img_selection.setPadding(new Insets(10,20,20,20));
	  img_selection.setVgap(5);
	  img_selection.setHgap(5);

	  // Province Buttons Set Up
	  ToggleButton btn_shandong 	= new ToggleButton("Shandong", new ImageView(new Image("shandong.png")));
	  btn_shandong.setTooltip(new Tooltip("Capital City:Jinan\n" + "Area:157,100 km^2\n" + "Area Rank:20th"));
	  ToggleButton btn_guangdong 	= new ToggleButton("Guangdong", new ImageView(new Image("guangdong.png")));
	  btn_guangdong.setTooltip(new Tooltip("Capital City:Guangzhou\n" + "Area:179,800 km^2\n" + "Area Rank:15th"));
	  ToggleButton btn_hebei 		= new ToggleButton("Hebei", new ImageView(new Image("hebei.png")));
	  btn_hebei.setTooltip(new Tooltip("Capital City:Baoding\n" + "Area:187,700 km^2\n" + "Area Rank:12th"));
	  ToggleButton btn_henan 		= new ToggleButton("Henan", new ImageView(new Image("henan.png")));
	  btn_henan.setTooltip(new Tooltip("Capital City:Kaifeng\n" + "Area:167,000 km^2\n" + "Area Rank:17th"));
	  ToggleButton btn_hubei 		= new ToggleButton("Hubei", new ImageView(new Image("hubei.png")));
	  btn_hubei.setTooltip(new Tooltip("Capital City:Wuhan\n" + "Area:185,900 km^2\n" + "Area Rank:13th"));
	  ToggleButton btn_hunan 		= new ToggleButton("Hunan", new ImageView(new Image("hunan.png")));
	  btn_hunan.setTooltip(new Tooltip("Capital City:Changsha\n" + "Area:210,000 km^2\n" + "Area Rank:10th"));
	  ToggleButton btn_jiangsu 		= new ToggleButton("Jiangsu", new ImageView(new Image("jiangsu.png")));
	  btn_jiangsu.setTooltip(new Tooltip("Capital City:Nanning\n" + "Area:102,600 km^2\n" + "Area Rank:24th"));
	  ToggleButton btn_liaoning 	= new ToggleButton("Liaoning", new ImageView(new Image("liaoning.png")));
	  btn_liaoning.setTooltip(new Tooltip("Capital City:Shenyang\n" + "Area:145,900 km^2\n" + "Area Rank:21st"));
	  ToggleButton btn_anhui 		= new ToggleButton("Anhui", new ImageView(new Image("anhui.png")));
	  btn_anhui.setTooltip(new Tooltip("Capital City:Hefei\n" + "Area:139,600 km^2\n" + "Area Rank:22nd"));
	  ToggleButton btn_fujian 		= new ToggleButton("Fujian", new ImageView(new Image("fujian.png")));
	  btn_fujian.setTooltip(new Tooltip("Capital City:Fuzhou\n" + "Area:121,400 km^2\n" + "Area Rank:23rd"));
	  ToggleButton btn_zhejiang 	= new ToggleButton("Zhejiang", new ImageView(new Image("zhejiang.png")));
	  btn_zhejiang.setTooltip(new Tooltip("Capital City:Hangzhou\n" + "Area:101,800 km^2\n" + "Area Rank:25th"));
	  ToggleButton btn_gansu 		= new ToggleButton("Gansu", new ImageView(new Image("gansu.png")));
	  btn_gansu.setTooltip(new Tooltip("Capital City:Lanzhou\n" + "Area:425,800 km^2\n" + "Area Rank:7th"));
	  ToggleButton btn_guangxi 		= new ToggleButton("Guangxi", new ImageView(new Image("guangxi.png")));
	  btn_guangxi.setTooltip(new Tooltip("Capital City:Nanning\n" + "Area:236,700 km^2\n" + "Area Rank:9th"));
	  ToggleButton btn_hainan 		= new ToggleButton("Hainan", new ImageView(new Image("hainan.png")));
	  btn_hainan.setTooltip(new Tooltip("Capital City:Haikou\n" + "Area:35,400 km^2\n" + "Area Rank:27th"));
	  ToggleButton btn_heilongjiang = new ToggleButton("Heilongjiang", new ImageView(new Image("heilongjiang.png")));	  
	  btn_heilongjiang.setTooltip(new Tooltip("Capital City:Harbin\n" + "Area:454,800 km^2\n" + "Area Rank:6th"));
	  ToggleButton btn_jiangxi 		= new ToggleButton("Jiangxi", new ImageView(new Image("jiangxi.png")));
	  btn_jiangxi.setTooltip(new Tooltip("Capital City:Nanchang\n" + "Area:166,900 km^2\n" + "Area Rank:18th"));
	  ToggleButton btn_jilin 		= new ToggleButton("Jilin", new ImageView(new Image("jilin.png")));
	  btn_jilin.setTooltip(new Tooltip("Capital City:Changchun\n" + "Area:187,400 km^2\n" + "Area Rank:14th"));
	  ToggleButton btn_neimenggu 	= new ToggleButton("Neimenggu", new ImageView(new Image("neimenggu.png")));
	  btn_neimenggu.setTooltip(new Tooltip("Capital City:Hohhot\n" + "Area:1,183,000 km^2\n" + "Area Rank:3rd"));
	  ToggleButton btn_ningxia 		= new ToggleButton("Ningxia", new ImageView(new Image("ningxia.png")));
	  btn_ningxia.setTooltip(new Tooltip("Capital City:Yinchuan\n" + "Area:66,000 km^2\n" + "Area Rank:26th"));
	  ToggleButton btn_qinghai 		= new ToggleButton("Qinghai", new ImageView(new Image("qinghai.png")));
	  btn_qinghai.setTooltip(new Tooltip("Capital City:Baoding\n" + "Area:720,000 km^2\n" + "Area Rank:4th"));
	  ToggleButton btn_Shanxi 		= new ToggleButton("Shanxi", new ImageView(new Image("shanxi.png")));
	  btn_Shanxi.setTooltip(new Tooltip("Capital City:Taiyuan\n" + "Area:156,000 km^2\n" + "Area Rank:19th"));
	  ToggleButton btn_shanxi 		= new ToggleButton("shanxi", new ImageView(new Image("shanxi1.png")));
	  btn_shanxi.setTooltip(new Tooltip("Capital City:Xi'an\n" + "Area:205,800 km^2\n" + "Area Rank:11th"));
	  ToggleButton btn_xinjiang 	= new ToggleButton("Xinjiang", new ImageView(new Image("xinjiang.png")));
	  btn_xinjiang.setTooltip(new Tooltip("Capital City:Ur¨¹mqi\n" + "Area:1,664,900 km^2\n" + "Area Rank:1st"));
	  ToggleButton btn_xizang 		= new ToggleButton("Xizang", new ImageView(new Image("xizang.png")));
	  btn_xizang.setTooltip(new Tooltip("Capital City:Lhasa\n" + "Area:1,228,400 km^2\n" + "Area Rank:2nd"));
	  ToggleButton btn_yunnan 		= new ToggleButton("Yunnan", new ImageView(new Image("yunnan.png")));
	  btn_yunnan.setTooltip(new Tooltip("Capital City:Kunming\n" + "Area:394,000 km^2\n" + "Area Rank:8th"));
	  ToggleButton btn_sichuan 		= new ToggleButton("Sichuan", new ImageView(new Image("sichuan.png")));
	  btn_sichuan.setTooltip(new Tooltip("Capital City:Chengdu\n" + "Area:485,000 km^2\n" + "Area Rank:5th"));
	  ToggleButton btn_guizhou 		= new ToggleButton("Guizhou", new ImageView(new Image("guizhou.png")));
	  btn_guizhou.setTooltip(new Tooltip("Capital City:Guiyang\n" + "Area:176,167 km^2\n" + "Area Rank:16th"));

	  int col = 0;
	  int row = 2;
	  for (ToggleButton b : Arrays.asList(btn_anhui, btn_fujian, btn_gansu, btn_guangdong, btn_guangxi, btn_guizhou, 
		   btn_hainan, btn_hebei, btn_heilongjiang, btn_henan,btn_hubei,btn_hunan, btn_jiangsu,btn_jiangxi,btn_jilin, 
		   btn_liaoning, btn_neimenggu, btn_ningxia, btn_qinghai , btn_shandong, btn_Shanxi, btn_shanxi,
		   btn_sichuan, btn_xinjiang, btn_xizang, btn_yunnan, btn_zhejiang)) {
		// Grid Distribution
		col++;
		img_selection.add(b, col, row, 1, 1);
		if ((col%5)==0){
			col = 0;
			row++;
		}		
		b.setAlignment(Pos.BOTTOM_CENTER);
		b.setMaxWidth(Double.MAX_VALUE);
		b.setMaxHeight(Double.MAX_VALUE);
		b.setContentDisplay(ContentDisplay.TOP);
		b.setStyle("-fx-font: 13 arial");
			b.setOnAction(new EventHandler<ActionEvent>() {
				  public void handle(ActionEvent event) {
						  	  { 
								  for(c = 0; c != names.length; c++){
									  if ((b.getText().toString()).equals(names[c])){
										  if(selected_list.contains(c)){		
										  	  selected_list.remove(new Integer(c));
									  		break;
										  }
										  else if(selected_list.size()<5)
										  {
											 selected_list.add(c);
										  	 break;
										  }
										  else
										  {
											  b.setSelected(false);
											  maximum_alert.showAndWait();
											  break;
										  }
									  }
									 
								  }
						  	  }
						  	System.out.println(selected_list);
				  }  
			});	
		}
	    
	  		// data import 
			pop_importValues();
		  	GDP_importValues();
		  	QuarterGDP_importValues();
		  	
		  	// Map Image
			ImageView mapView = new ImageView(new Image("map.png"));
			img_selection.add(mapView,0,2,1,5);
			GridPane.setMargin(mapView, new Insets(5, 30, 5, 10));
			
			// line chart setup
			final CategoryAxis GDP_xAxis = new CategoryAxis();
			GDP_xAxis.setLabel("Year");
			final NumberAxis GDP_yAxis = new NumberAxis();
			GDP_yAxis.setLabel("GDP (hundred million yuan)");
			
			final LineChart<String, Number> GDP_Chart = new LineChart<String, Number>(GDP_xAxis, GDP_yAxis);
			GDP_Chart.setPadding(new Insets(10,0,0,0));
			GDP_Chart.setPrefWidth(1000);
			GDP_Chart.setPrefHeight(630);

			// bar chart setup
			final CategoryAxis QuarterGDP_XAxis = new CategoryAxis();
			QuarterGDP_XAxis.setLabel("Year");
			final NumberAxis QuarterGDP_YAxis = new NumberAxis();
			QuarterGDP_YAxis.setLabel("GDP (hundred million yuan)");
			
			BarChart<String, Number> QuarterGDP_Chart = new BarChart<String, Number>(QuarterGDP_XAxis,QuarterGDP_YAxis);
			QuarterGDP_Chart.setPadding(new Insets(30,0,0,0));

	        //QuarterGDP_Chart.setTitle("Gross Domestic Product(GDP) Season Statistics");
	        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
	        series1.setName("First Quarter");
	        XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
	        series2.setName("Second Quarter");
	        XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
	        series3.setName("Third Quarter");
	        XYChart.Series<String, Number> series4 = new XYChart.Series<String, Number>();
	        series4.setName("Fourth Quarter");
	        // add data to chart
	        QuarterGDP_Chart.getData().addAll(series1,series2,series3,series4);
	        QuarterGDP_scene_chart.setCursor(Cursor.DEFAULT);

	        // Main Title
			Text title = new Text("Population & GDP Statistics of Chinese Provinces");
			img_selection.add(title,0,0,6,1);
			GridPane.setHalignment(title, HPos.CENTER);
			GridPane.setMargin(title, new Insets(10, 0, 0, 0));
			title.setFont(Font.font(java.awt.Font.SERIF, FontWeight.BOLD, 25));
			title.setFill(Color.rgb(101,169,222));
			
			Rectangle blueBorder = new Rectangle();
	        blueBorder.setStroke(Color.rgb(101,169,222));
	        blueBorder.setManaged(false);
	        blueBorder.setFill(Color.TRANSPARENT);
	        title.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
	            public void changed(ObservableValue<? extends Bounds> observable,Bounds oldValue, Bounds newValue) {
	            	blueBorder.setLayoutX(title.getBoundsInParent().getMinX()-4);
	            	blueBorder.setLayoutY(title.getBoundsInParent().getMinY()+30);
	            	blueBorder.setWidth(title.getBoundsInParent().getWidth()+10);
	            }
	        });
	        img_selection.add(blueBorder,0,0);

			// Sub Titles
			Label map_text = new Label("Map Overview");
			img_selection.add(map_text,0,1);
			map_text.setGraphic(new ImageView(new Image("eye.png")));
			Label selection_text = new Label("Selection Board");
			img_selection.add(selection_text,1,1,5,1);
			selection_text.setGraphic(new ImageView(new Image("hand.png")));
	        
			Rectangle redBorder = new Rectangle();
	        redBorder.setStroke(Color.rgb(254,145,145));
	        redBorder.setManaged(false);
	        redBorder.setFill(Color.TRANSPARENT);
	        Rectangle redBorder1 = new Rectangle();
	        redBorder1.setStroke(Color.rgb(254,145,145));
	        redBorder1.setManaged(false);
	        redBorder1.setFill(Color.TRANSPARENT);
			for (Label t :  Arrays.asList(selection_text, map_text)){
				GridPane.setHalignment(t, HPos.CENTER);
				GridPane.setMargin(t, new Insets(5, 0, 5, 0));
				t.setFont(Font.font(java.awt.Font.SERIF,FontWeight.BOLD, 21));
				t.setTextFill(Color.rgb(254,145,145)); 	
			}
			selection_text.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
	            public void changed(ObservableValue<? extends Bounds> observable,Bounds oldValue, Bounds newValue) {
	            	redBorder.setLayoutX(selection_text.getBoundsInParent().getMinX()-4);
	            	redBorder.setLayoutY(selection_text.getBoundsInParent().getMinY()+25);
	            	redBorder.setWidth(selection_text.getBoundsInParent().getWidth()+10);
	            }
	        });
			map_text.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
	            public void changed(ObservableValue<? extends Bounds> observable,Bounds oldValue, Bounds newValue) {
	            	redBorder1.setLayoutX(map_text.getBoundsInParent().getMinX()-4);
	            	redBorder1.setLayoutY(map_text.getBoundsInParent().getMinY()+25);
	            	redBorder1.setWidth(map_text.getBoundsInParent().getWidth()+10);
	            }
	        });
			img_selection.add(redBorder,1,1);
	        img_selection.add(redBorder1,0,1);
		
	  	  root.getChildren().add(img_selection);
 
		// Selection Scene Set-up 
	  	HBox mode_switch = new HBox(3);  
		Button button_pop = new Button("Population",new ImageView(new Image("person.png")));
		button_pop.setFont(Font.font(java.awt.Font.SERIF,FontWeight.BOLD, 15));
		button_pop.setTextFill(Color.rgb(101,169,222));//blue
		Button button_gdp = new Button("GDP", new ImageView(new Image("money.png")));
		button_gdp.setFont(Font.font(java.awt.Font.SERIF,FontWeight.BOLD, 15));
		button_gdp.setTextFill(Color.rgb(230,133,25));//red
		mode_switch.getChildren().addAll(button_pop,button_gdp);
		
		Button button_check = new Button("", new ImageView("check.png"));
		Button button_reset = new Button("", new ImageView("reset.png"));
		img_selection.add(button_check,5,7);
		GridPane.setHalignment(button_check, HPos.RIGHT);
		img_selection.add(button_reset,4,7);
		GridPane.setHalignment(button_reset, HPos.RIGHT);
		
		// Line Chart Set-up
		Button button_return = new Button("", new ImageView("back.png"));
		setTitle_GDP();
		LineChart.setMargin(button_return, new Insets(0, 10, 5, 940));
		LineChart.add(linechart_title,0,0);
		LineChart.add(mode_switch,1,0);
		LineChart.add(button_return,0,2,2,1);
		LineChart.add(GDP_Chart, 0,1,2,1);
		linechart.getChildren().add(LineChart);

		// Bar Chart Set-up
		BorderPane BarChart = new BorderPane();
		
		Text QuarterGDP_title = new Text();
		QuarterGDP_title.setFont(Font.font(java.awt.Font.SERIF,FontWeight.BOLD, 21));
		QuarterGDP_title.setFill(Color.rgb(230,133,25));//red
		barchart.setAlignment(QuarterGDP_title, Pos.TOP_CENTER);
		
		Button button_return1 = new Button("", new ImageView("back.png"));
		BarChart.setBottom(button_return1);
		BorderPane.setAlignment(button_return1, Pos.BOTTOM_RIGHT);

		barchart.getChildren().add(QuarterGDP_Chart);
		barchart.getChildren().add(BarChart);
		barchart.getChildren().add(QuarterGDP_title);

		/** Button Event Listener **/
		// mode-switch population
		button_pop.setOnAction(new EventHandler<ActionEvent>() {
		  public void handle(ActionEvent event) {
			  if ((linechart_title.getText()).equals("Gross Domestic Product(GDP) Statistics")){
			  //GDP_Chart.getData().remove(GDP_series_list.get(c));
			  GDP_Chart.getData().clear();
		      GDP_yAxis.setLabel("Population (ten thousand)");
			  GDP_xAxis.setLabel("Year");
			  setTitle_pop();
			  //GDP_Chart.getData().retainAll();
			  for(int i = 0; i != selected_list.size(); i++){
				  GDP_Chart.getData().add(pop_series_list.get(selected_list.get(i)));
			  }
			  }
		   }
		});
		
		// mode-switch GDP
		button_gdp.setOnAction(new EventHandler<ActionEvent>(){
		  public void handle(ActionEvent event) {
			  if ((linechart_title.getText()).equals("Population Statistics")){
				  GDP_Chart.getData().clear();
				  GDP_yAxis.setLabel("GDP (hundred million yuan)");
				  GDP_xAxis.setLabel("Year");
				  setTitle_GDP();
				  //GDP_Chart.getData().retainAll();
				  for(int i = 0; i != selected_list.size(); i++){
					  GDP_Chart.getData().add(GDP_series_list.get(selected_list.get(i)));
					  GDP_series_list.get(selected_list.get(i)).getNode().setOnMouseEntered(onMouseEnteredSeriesListener);
					  GDP_series_list.get(selected_list.get(i)).getNode().setOnMouseExited(onMouseExitedSeriesListener);
					  GDP_series_list.get(selected_list.get(i)).getNode().setOnMouseClicked(new EventHandler<MouseEvent>()
				        {
				            public void handle(MouseEvent t) {
				            	for (int k = 0; k != selected_list.size(); k++){
				            		Object obj = t.getTarget();
				            		if(GDP_series_list.get(selected_list.get(k)).getNode() == obj)
				            		{
				            			for(int j=0 ; j<17; j+=4){
											  series1.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j));
											  series2.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j+1));
											  series3.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j+2));
											  series4.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j+3));
										}
						            	QuarterGDP_title.setText(names[selected_list.get(k)]+" GDP Season Statistics");
										stage.setScene(QuarterGDP_scene_chart);
				            		}
				            	}
				            }
				        });
				  }
			  }
		  }
		});
		
		// return to scene selection
		button_return.setOnAction(new EventHandler<ActionEvent>() {
			  public void handle(ActionEvent event) {
				  selected_list.clear();
				  GDP_Chart.getData().clear();
				  GDP_yAxis.setLabel("GDP (hundred million yuan)");
				  GDP_xAxis.setLabel("Year");
				  setTitle_GDP();
				  stage.setScene(scene_selection);
			  }
		});
		
		// return to GDP chart
		button_return1.setOnAction(new EventHandler<ActionEvent>() {
			  public void handle(ActionEvent event) {
				 stage.setScene(GDP_scene_chart);
			  }
		});
		
		// check button in selection scene
		button_check.setOnAction(new EventHandler<ActionEvent>() {
			  public void handle(ActionEvent event) {
				  if (selected_list.size()!=0){
					// clear button selected state
					  for (ToggleButton b : Arrays.asList(btn_anhui, btn_fujian, btn_gansu, btn_guangdong, btn_guangxi, btn_guizhou, 
					    		btn_hainan, btn_hebei, btn_heilongjiang, btn_henan,btn_hubei,btn_hunan, btn_jiangsu,btn_jiangxi,btn_jilin, 
					    		btn_liaoning, btn_neimenggu, btn_ningxia, btn_qinghai , btn_shandong, btn_Shanxi, btn_shanxi,
					    		btn_sichuan, btn_xinjiang, btn_xizang, btn_yunnan, btn_zhejiang)){
						   if (b.isSelected()){
							   b.setSelected(false);
						   }       
					  }
				  
					  for(int i = 0; i != selected_list.size(); i++){
						  GDP_Chart.getData().add(GDP_series_list.get(selected_list.get(i)));
						  GDP_series_list.get(selected_list.get(i)).getNode().setOnMouseEntered(onMouseEnteredSeriesListener);
						  GDP_series_list.get(selected_list.get(i)).getNode().setOnMouseExited(onMouseExitedSeriesListener);
						  GDP_series_list.get(selected_list.get(i)).getNode().setOnMouseClicked(new EventHandler<MouseEvent>()
					        {
					            public void handle(MouseEvent t) {
					            	for (int k = 0; k != selected_list.size(); k++){
					            		Object obj = t.getTarget();
					            		if(GDP_series_list.get(selected_list.get(k)).getNode() == obj)
					            		{
					            			for(int j=0 ; j<17; j+=4){
												  series1.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j));
												  series2.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j+1));
												  series3.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j+2));
												  series4.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j+3));
											}
							            	QuarterGDP_title.setText(names[selected_list.get(k)]+" GDP Season Statistics");
							            	
											stage.setScene(QuarterGDP_scene_chart);
											
					            		}
					            	}
					            }
					        });
					  }
					  stage.setScene(GDP_scene_chart);
				  }
				  else{
					  empty_alert.showAndWait();
				  }
			  }
		});
		
		// reset button in selection scene
		button_reset.setOnAction(new EventHandler<ActionEvent>() {
			  public void handle(ActionEvent event) {
				  // clear button selected state
				  for (ToggleButton b : Arrays.asList(btn_anhui, btn_fujian, btn_gansu, btn_guangdong, btn_guangxi, btn_guizhou, 
				    		btn_hainan, btn_hebei, btn_heilongjiang, btn_henan,btn_hubei,btn_hunan, btn_jiangsu,btn_jiangxi,btn_jilin, 
				    		btn_liaoning, btn_neimenggu, btn_ningxia, btn_qinghai , btn_shandong, btn_Shanxi, btn_shanxi,
				    		btn_sichuan, btn_xinjiang, btn_xizang, btn_yunnan, btn_zhejiang)) {
					   if (b.isSelected()){
						   b.setSelected(false);
					   }       
				  }
				  selected_list.clear();
			  }
		});

		// Set Scene
		stage.setScene(scene_selection);
		stage.show();
  }
  
	public void setTitle_GDP(){
		linechart_title.setText("Gross Domestic Product(GDP) Statistics");
		linechart_title.setFont(Font.font(java.awt.Font.SERIF,FontWeight.BOLD, 21));
		linechart_title.setFill(Color.rgb(230,133,25));//red
		LineChart.setMargin(linechart_title, new Insets(0, 100, 0, 350));
	}
	
	public void setTitle_pop(){
		linechart_title.setText("Population Statistics");
		linechart_title.setFont(Font.font(java.awt.Font.SERIF,FontWeight.BOLD, 21));
		linechart_title.setFill(Color.rgb(230,133,25));//red
		LineChart.setMargin(linechart_title, new Insets(0, 185, 0, 445));
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
	/** Mouse Event Handler **/
	// Enter
    EventHandler<MouseEvent> onMouseEnteredSeriesListener = 
            (MouseEvent event) -> {
                ((Node)(event.getSource())).setCursor(Cursor.HAND);         
    };

    // Exit
    EventHandler<MouseEvent> onMouseExitedSeriesListener = 
            (MouseEvent event) -> {
                ((Node)(event.getSource())).setCursor(Cursor.DEFAULT);
    };
    
    /** Import Data **/
    // import GDP values
    public void GDP_importValues(){
		File file = new File("GDP.txt");
		    try {
		    	 String thisLine = null;
		    	 BufferedReader br = new BufferedReader(new FileReader(file));
		         try {
		        	 thisLine = br.readLine();

					 double GDP_lowRanges [] = new double[province_num+1];
					 for (int i = 0; i < GDP_lowRanges.length; i++) 
						 GDP_lowRanges[i] = Double.MAX_VALUE;
								 
					 double GDP_highRanges [] = new double[province_num+1];
				     for (int i = 0; i < GDP_highRanges.length; i++) 
				    	 GDP_highRanges[i] = Double.MIN_VALUE;
				     
				     int line_num = 0;
					 while ((thisLine = br.readLine()) != null) { 

						 String values [] = thisLine.split(";");
						 double dValues [] = new double[values.length -1];
						 for (int j =1; j < values.length; j++) {			 
							 dValues[j-1] = Double.parseDouble(values[j]);
							 if (dValues[j-1] <  GDP_lowRanges[line_num]) GDP_lowRanges[line_num] = dValues[j-1];
							 if (dValues[j-1] >  GDP_highRanges[line_num]) GDP_highRanges[line_num] = dValues[j-1];
						 }
						 GDP_list.add(new Data(dValues, values[0]));
						 names[line_num] = values[0];
						 line_num ++;	 
		   			  }
					  for (int i = 0; i < line_num; i++) {
						  GDP_ranges.add(new Range(GDP_lowRanges[i],GDP_highRanges[i]));
					  } 
				} catch (IOException e) {
					e.printStackTrace();
				} 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		    // Line Chart
			for (int n = 0; n < province_num; n++) {
				XYChart.Series<String, Number> GDP_series_name = new XYChart.Series<String, Number>();
				GDP_series_name.setName("GDP_" + names[n]);
				GDP_series_list.add(GDP_series_name);
				for(int i = 0; i != 5; ++i)
			  	{	
					final XYChart.Data<String,Number> data = new XYChart.Data<String, Number>(Integer.toString(year_ini + i), GDP_list.get(n).getValue(i));
					data.setNode(new HoveredThresholdNode(i+year_ini,GDP_list.get(n).getValue(i)));
					GDP_series_list.get(n).getData().add(data);
			  	}
			}
	}
	
    // import population values
	public void pop_importValues(){
		File file = new File("population.txt");
    try {
   	 String thisLine = null;
   	 BufferedReader bt = new BufferedReader(new FileReader(file));
        try {
			thisLine = bt.readLine();
			
			// Prepare Ranges
			double pop_lowRanges [] = new double[province_num+1];
			for (int i = 0; i < pop_lowRanges.length; i++) 
				 pop_lowRanges[i] = Double.MAX_VALUE;
						 
			double pop_highRanges [] = new double[province_num+1];
			for (int i = 0; i < pop_highRanges.length; i++) 
				 pop_highRanges[i] = Double.MIN_VALUE;
			 
			int line_num = 0;
			while ((thisLine = bt.readLine()) != null) { 
				 String values [] = thisLine.split(";");
				 double dValues [] = new double[values.length -1];
			
				 for (int j =1; j < values.length; j++) {
					 dValues[j-1] = Double.parseDouble(values[j]);
					 if (dValues[j-1] <  pop_lowRanges[line_num]) pop_lowRanges[line_num] = dValues[j-1];
					 if (dValues[j-1] >  pop_highRanges[line_num]) pop_highRanges[line_num] = dValues[j-1];
				 }
				 pop_list.add(new Data(dValues, values[0]));
				 names[line_num] = values[0];
				 line_num ++;	 
  			  }
			  for (int i = 0; i < line_num; i++) {
				  pop_ranges.add(new Range(pop_lowRanges[i],pop_highRanges[i]));
			  } 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
    
    // Line Chart
		for (int n = 0; n < province_num; n++) {
			XYChart.Series<String, Number> pop_series_name = new XYChart.Series<String, Number>();
			pop_series_name.setName("population_" + names[n]);
			pop_series_list.add(pop_series_name);
			for(int i = 0; i != 5; ++i)
		  	{	
				final XYChart.Data<String,Number> data = new XYChart.Data<String, Number>(Integer.toString(year_ini + i), pop_list.get(n).getValue(i));
				data.setNode(new HoveredThresholdNode(i+year_ini,pop_list.get(n).getValue(i)));
				pop_series_list.get(n).getData().add(data);
		  	}	
		}
	}
	
	// import QuarterGDP values
	public void QuarterGDP_importValues(){
		File file = new File("Quarter_GDP.txt");
		try {
			String thisLine = null;
			BufferedReader bt = new BufferedReader(new FileReader(file));
			try {
				 thisLine = bt.readLine();

				 double QuarterGDP_lowRanges [] = new double[province_num+1];
				 for (int i = 0; i < QuarterGDP_lowRanges.length; i++) 
					 QuarterGDP_lowRanges[i] = Double.MAX_VALUE;
							 
				 double QuarterGDP_highRanges [] = new double[province_num+1];
			     for (int i = 0; i < QuarterGDP_highRanges.length; i++) 
			    	 QuarterGDP_highRanges[i] = Double.MIN_VALUE;
			     
			     int line_num = 0;
					 while ((thisLine = bt.readLine()) != null) { 
						 String values [] = thisLine.split(";");
						 double dValues [] = new double[values.length -1];
					
						 for (int j =1; j < values.length; j++) {
							 
							 dValues[j-1] = Double.parseDouble(values[j]);
							 
							 if (dValues[j-1] <  QuarterGDP_lowRanges[line_num]) QuarterGDP_lowRanges[line_num] = dValues[j-1];
							 if (dValues[j-1] >  QuarterGDP_highRanges[line_num]) QuarterGDP_highRanges[line_num] = dValues[j-1];
						 }
					 QuarterGDP_list.add(new Data(dValues, values[0]));
					 line_num ++;	 
	  			  }
				  for (int i = 0; i < line_num; i++) {
					  QuarterGDP_ranges.add(new Range(QuarterGDP_lowRanges[i],QuarterGDP_highRanges[i]));
				  } 
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		}

		for (int n = 0; n < province_num; n++) 
		{		
			int year_counter = 0;
			XYChart.Series<String, Number> QuarterGDP_series_name = new XYChart.Series<String, Number>();
			QuarterGDP_series_name.setName("QuaterGDP_" + names[n]);
			QuarterGDP_series_list.add(QuarterGDP_series_name);
			// iterate data under specific year
			for(int i = 0; i != 20; ++i)
		  	{	
				if(i!=0 && i%4==0){
					year_counter++;
				}
				final XYChart.Data<String,Number> data = new XYChart.Data<String, Number>(Integer.toString(year_ini + year_counter), QuarterGDP_list.get(n).getValue(i));
				QuarterGDP_series_list.get(n).getData().add(data);
		  	}						
		}
	}
}
    
	/** Hover Effect for Mouse **/
	  class HoveredThresholdNode extends StackPane {
	    HoveredThresholdNode(int priorValue, double value) {
	      setPrefSize(13,13);
	      final Label label = createDataThresholdLabel(priorValue, value);
	      setOnMouseEntered(new EventHandler<MouseEvent>() {
	    	  public void handle(MouseEvent mouseEvent) {
		          getChildren().setAll(label);
		          setCursor(Cursor.HAND);
		          toFront();
	        }
	      });
	      setOnMouseExited(new EventHandler<MouseEvent>() {
	    	  public void handle(MouseEvent mouseEvent) {
		          getChildren().clear();
	        }
	      });
	    }

	    private Label createDataThresholdLabel(int priorValue, double value) {
	      final Label label = new Label(value + "");
	      label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
	      label.setStyle("-fx-font-size:10; -fx-font-weight: bold;");
	      
	      if (priorValue == 0) {
	          label.setTextFill(Color.DARKGRAY);
	        } else if (value > priorValue) {
	          label.setTextFill(Color.FORESTGREEN);
	        } else {
	          label.setTextFill(Color.FIREBRICK);
	        }

	      label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
	      return label;
	    }
	  }
  /*GDP_series_list.get(selected_list.get(i)).getNode().setOnMouseClicked(new EventHandler<MouseEvent>()
					        {
					            public void handle(MouseEvent t) {
					            	if (t.getTarget() == previous_target){
				            			stage.setScene(QuarterGDP_scene_chart);
				            			same_as_previous = true;
					            	}
					            	if (same_as_previous == false){
						            	for (int k = 0; k != selected_list.size(); k++){
						            		if(GDP_series_list.get(selected_list.get(k)).getNode() == t.getTarget())
						            		{
						            			for (Series<String, Number> s: Arrays.asList(series1,series2,series3,series4)){
													 s.getData().remove(true); 
												 }
						            			for(int j=0 ; j<17; j+=4){
													  series1.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j));
													  series2.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j+1));
													  series3.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j+2));
													  series4.getData().add(QuarterGDP_series_list.get(selected_list.get(k)).getData().get(j+3));
												}
								            	QuarterGDP_title.setText(names[selected_list.get(k)]+" GDP Season Statistics");
												stage.setScene(QuarterGDP_scene_chart);
						            		}
						            		
						            	}
						            	previous_target = t.getTarget();
						            }
					            }         
					        }); */

