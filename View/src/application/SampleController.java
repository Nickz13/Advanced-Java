package application;

import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;

public class SampleController {
	private ProjectHandler pro;
	private Map<String, String> selection = new HashMap<String, String>();
	String empty = "";
	
	@FXML private HBox hbox;
	@FXML private Button rem;
	@FXML private Button swap;
	@FXML private TextField stid;
	 @FXML
	    private Button undo;
	@FXML
	private BarChart<?, ?> bc3;

	@FXML
	private CategoryAxis xaxis3;

	@FXML
	private NumberAxis yaxis3;

	@FXML
	private BarChart<?, ?> bc1;

	@FXML
	private CategoryAxis xaxis1;

	@FXML
	private NumberAxis yaxis1;

	@FXML
	private BarChart<?, ?> bc2;

	@FXML
	private CategoryAxis xaxis2;

	@FXML
	private NumberAxis yaxis2;
	Node n1, n2, n3;

	@FXML
	public void initialize() throws ClassNotFoundException, IOException {
		pro = new ProjectHandler();
		pro.setController(this);
		
		update();
	}

	public void updatebox(ProjectHandler proj) {
		Color[] colors;

	    //Allocate the size of the array
	    colors = new Color[5];

	    //Initialize the values of the array
	    colors[0] = Color.LIGHTSALMON;
	    colors[1] = Color.LIGHTBLUE;
	    colors[2] = Color.LIGHTSLATEGRAY;
	    colors[3] = Color.LIGHTGREEN;
	    colors[4] = Color.LIGHTPINK;
	    int count = 0;
		hbox.getChildren().clear();
		for (Map.Entry mapEle : proj.getTeams().entrySet()) {
			Team value = (Team) mapEle.getValue();
			String key = (String) mapEle.getKey();
			GridPane teamgrid = new GridPane();
			Label txt = new Label();
			txt.setText("Team"+key.charAt(2));
			teamgrid.add(txt, 0, 0);
			String[] stIDs = ((Team) mapEle.getValue()).getStudentIDs().split(" ");
			for (int i = 1;i<=stIDs.length;i++) {
				CheckBox checkBox = new CheckBox(stIDs[i-1]);
				checkBox.setId(stIDs[i-1]);
				checkBox.setTextFill(Color.BLACK);
				checkBox.setAlignment(Pos.CENTER);
				EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
					  
	                public void handle(ActionEvent e) 
	                {
	                    if (checkBox.isSelected()) 
	                    	selection.put(key, checkBox.getId());
	                    else
	                    	selection.remove(key, checkBox.getId());
	                } 
	  
	            };
	            checkBox.setOnAction(event);
				//add them to the GridPane
				teamgrid.add(checkBox , 0, i);
				GridPane.setMargin(checkBox, new Insets(5));
			}
			if(stIDs.length < 4) {
				System.out.println("enetered");
				CheckBox ch = new CheckBox("-");
				ch.setId("empty");
				ch.setTextFill(Color.BLACK);
				ch.setAlignment(Pos.CENTER);
				EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() { 
					  
	                public void handle(ActionEvent e) 
	                {
	                    if (ch.isSelected()) {
	                    	empty = key;
	                    	
	                    }
	                    else
	                    	empty = "";
	                } 
	  
	            };
	            ch.setOnAction(event1);
	            teamgrid.add(ch, 0, 4);
	            GridPane.setMargin(ch, new Insets(5));
			}

			teamgrid.setBackground(
			 new Background(new BackgroundFill(colors[count], CornerRadii.EMPTY, Insets.EMPTY)));
			hbox.getChildren().add(teamgrid);
			teamgrid.setStyle("-fx-padding: 10;" + 
					"-fx-border-style: solid inside;" + 
					"-fx-border-width: 2;" +
					"-fx-border-insets: 5;" + 
					"-fx-border-radius: 5;" + 
					"-fx-border-color: black;");
			teamgrid.setId(key);
			count++;
		}
	}

	public void update() {
		
		updatebox(pro);
		pro.teamMetrics();
		Map<String,Double> series1 = new TreeMap<String,Double>();
		for (Map.Entry mapEle : pro.getAv_comp_level().entrySet()) {
			series1.put((String)mapEle.getKey(), (Double)mapEle.getValue());
		}

		Map<String,Double> series2 = new TreeMap<String,Double>();
		for (Map.Entry mapEle : pro.getSkill_Short().entrySet()) {
			series2.put((String)mapEle.getKey(), (Double)mapEle.getValue());
		}

		Map<String,Double> series3 = new TreeMap<String,Double>();
		for (Map.Entry mapEle : pro.percentage().entrySet()) {
			series3.put((String)mapEle.getKey(), (Double)mapEle.getValue());
		}

		XYChart.Series dataSeries1  = new XYChart.Series();
		dataSeries1.setName("Average Competency Level");
		for (String c : series1.keySet() )
			dataSeries1.getData().add(new XYChart.Data("Team"+c.charAt(2), series1.get(c))); 
		bc1.getData().clear();
		bc1.getData().add(dataSeries1);
		bc1.setTitle("Average Competency Level\n"+"Std. Dev. = "+pro.competencyDeviation());
		bc1.setLegendVisible(false);
//		bc1.lab

		XYChart.Series dataSeries2  = new XYChart.Series();
		dataSeries2.setName("Skill Gap");
		for (String c : series2.keySet() )
			dataSeries2.getData().add(new XYChart.Data("Team"+c.charAt(2), series2.get(c))); 
		bc2.getData().clear();
		bc2.getData().add(dataSeries2);
		bc2.setTitle("Skill Gap \n"+"Std. Dev. = "+pro.shortfallDeviation());
		bc2.setLegendVisible(false);

		XYChart.Series dataSeries3  = new XYChart.Series();
		dataSeries3.setName("Percentage");
		for (String c : series3.keySet() )
			dataSeries3.getData().add(new XYChart.Data("Team"+c.charAt(2), series3.get(c)));
		bc3.getData().clear();
		bc3.getData().add(dataSeries3);
		bc3.setTitle("%Getting 1st and 2nd \n Preferences \n"+"Std. Dev. = "+pro.percentageDeviation());
		bc3.setLegendVisible(false);
		setColors();
		
	}
	
	public void setColors() {
		bc1.lookupAll(".data0.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTPINK");});
		bc1.lookupAll(".data1.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTBLUE");});
		bc1.lookupAll(".data2.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTSALMON");});
		bc1.lookupAll(".data3.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTGREEN");});
		bc1.lookupAll(".data4.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTSLATEGRAY");});
		
		bc2.lookupAll(".data0.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTPINK");});
		bc2.lookupAll(".data1.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTBLUE");});
		bc2.lookupAll(".data2.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTSALMON");});
		bc2.lookupAll(".data3.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTGREEN");});
		bc2.lookupAll(".data4.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTSLATEGRAY");});
		
		bc3.lookupAll(".data0.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTPINK");});
		bc3.lookupAll(".data1.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTBLUE");});
		bc3.lookupAll(".data2.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTSALMON");});
		bc3.lookupAll(".data3.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTGREEN");});
		bc3.lookupAll(".data4.chart-bar").forEach(n -> {n.setStyle("-fx-bar-fill: LIGHTSLATEGRAY");});
		
	}

	public void swap(ActionEvent evt) throws IOException, NotValidIDException, InvalidMemberException, StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		System.out.println(selection.values());
		System.out.println(((Button)evt.getSource()).getId().equals("swap"));
		if(selection.size() > 2 || selection.size() < 2) {
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("Exception Dialog");
		    alert.setHeaderText("Incorrect Selection");
		    alert.setContentText("Exactly two items must be selected");
		    alert.showAndWait();
		    selection.clear();
		    update();
		}
		else {
			try {
				pro.swapTeam(selection);
				selection.clear();
				//update();
			} catch (Exception e) {
				// TODO: handle exception
				for (Map.Entry mapEle : selection.entrySet()) {
					pro.getTeams().remove(mapEle.getKey());	
				}
				
				pro.formTeam(pro.getTeams1());
				pro.formTeam(pro.getTeams2());
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Exception Dialog");
			    alert.setHeaderText(e.getClass().toString());
			    //alert.setContentText("Coursework and Exam marks must be integers");
			    alert.showAndWait();
			    update();
			}
		}
	}
	
	public void removeID() throws IOException, NotValidIDException, InvalidMemberException, StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		System.out.println("selection"+selection.size());
		if(selection.size() != 1) {
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("Exception Dialog");
		    alert.setHeaderText("Incorrect Selection");
		    alert.setContentText("Exactly one item must be selected");
		    alert.showAndWait();
		    selection.clear();
		    update();
		}
		else {
			try {
				pro.removeID(selection);
				selection.clear();
			} catch (Exception e)  {
				// TODO: handle exception
				for (Map.Entry mapEle : selection.entrySet()) {
					pro.getTeams().remove(mapEle.getKey());	
				}
				pro.formTeam(pro.getTeam_edit());
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Exception Dialog");
			    alert.setHeaderText(e.getClass().toString());
			    //alert.setContentText("Coursework and Exam marks must be integers");
			    alert.showAndWait();
			    selection.clear();
			    update();
			}
		}
	}
	
	public void addID(ActionEvent evt) throws IOException, NotValidIDException, InvalidMemberException, StudentConflictException, RepeatedMemberException, NoLeaderException, PersonalityImbalanceException {
		System.out.println(stid.getCharacters());
		System.out.println("selection"+selection.size());
		if(empty.equals("") || stid.getCharacters().toString().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("Exception Dialog");
		    alert.setHeaderText("No Empty location selected or TextField is Empty");
		    alert.setContentText("Select an empty location or Fill the TextField");
		    alert.showAndWait();
		    stid.setText("");
		    empty = "";
		    update();
		}
		else {
			try {
				pro.addID(empty, stid.getCharacters().toString());
				empty = "";
				stid.setText("");
			} catch (Exception e)  {
				// TODO: handle exception
//				for (Map.Entry mapEle : selection.entrySet()) {
//					pro.getTeams().remove(mapEle.getKey());	
//				}
//				pro.formTeam(pro.getTeam_delete());
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Exception Dialog");
			    alert.setHeaderText(e.getClass().toString());
			    //alert.setContentText("Coursework and Exam marks must be integers");
			    alert.showAndWait();
			    selection.clear();
			    update();
			}
		}
	}
	
	@FXML
    void undo(ActionEvent event) {
		pro.undo();
    }
}
