package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		Label greeting = new Label("Text Analyzer!");
		greeting.setFont(new Font(40));
		greeting.setTextFill(Color.BLUE);
		
		try {
			
			Button startBtn = new Button("Start");
			startBtn.setOnAction((ActionEvent e) ->
			{
				try
				{
					resultsChart();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			});
			
			Button quitBtn = new Button("Quit");
			quitBtn.setOnAction(e -> Platform.exit());
						
			HBox btnBox = new HBox(20, startBtn, quitBtn);
			btnBox.setAlignment(Pos.CENTER);
			btnBox.setPadding(new Insets(0, 0, 20, 0));
						
			BorderPane root = new BorderPane();
			root.setCenter(greeting);
			root.setBottom(btnBox);
			
			Scene scene = new Scene(root,500,250);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Text Analyzer");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method reads a text file one line at a time, splits lines into lower case words, and sorts by most frequently
	 * used word. The top 20 words are printed to the console.
	 * @return  An ArrayList of the sorted words 
	 * @throws IOException
	 */
	
	public ArrayList<Map.Entry<String, Integer>> analyzer() throws IOException
	{
		// import the file
		File input = new File("Raven.txt");
		BufferedReader reader = new BufferedReader(new FileReader(input));
		
		// Map to store each word and its count
		Map<String, Integer> textMap = new HashMap<>();
		String line;
		
		// Parse file one line at a time
		while((line = reader.readLine()) != null)
		{
			// Split each line into an array of words, remove anything that is not a letter, and make all letters lower case
			String[] words = line.toLowerCase().split("[^a-zA-Z]+");
			
			for (String word : words)
			{
				
				if (word.length() > 0) 
				{
					if (textMap.containsKey(word))
					{
						textMap.put(word, textMap.get(word) + 1);
					}
					else
					{
						textMap.put(word, 1);
					}
				}
			}
		}
		
		// Sort textMap by the most frequently used words
		ArrayList<Map.Entry<String, Integer>> sorted = new ArrayList<>(textMap.entrySet());
	    Collections.sort(sorted, new Comparator<Map.Entry<String, Integer>>()
	    {
	        public int compare(Map.Entry<String, Integer> first, Map.Entry<String, Integer> second)
	        {
	            return first.getValue().compareTo(second.getValue());
	        }
	    });  
	    
	    // Print top 20 most frequently used words
	    for(int i = 0; i < 20; i++)
	    {
	        System.out.println(sorted.get(sorted.size() - i - 1).getKey() 
	        		+ ": " + sorted.get(sorted.size() - i - 1).getValue());
	    }
	    
		
		reader.close();
		return sorted;

	}
	
	/**
	 * This method calls the analyzer() method to read and sort, and uses the data in
	 * the returned ArrayList to populate a bar graph to display the results in a new window.
	 * 
	 * @throws IOException
	 */
	
	public void resultsChart() throws IOException
	{
		BarChart<String, Number> chart = new BarChart<>(new CategoryAxis(), new NumberAxis());
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		
		ArrayList<Map.Entry<String, Integer>> sorted = analyzer();
		
		for(int i = 0; i < 20; i++)
		{
			series.getData().add(new XYChart.Data<>(sorted.get(sorted.size() - i -1).getKey(), 
			sorted.get(sorted.size() - i - 1).getValue()));
		}
		
		StackPane root = new StackPane();
		Scene secondScene = new Scene(root, 1000, 600);
		Stage secondStage = new Stage();
		secondStage.setTitle("Results");
		
		chart.setTitle("Top 20 Words");
		chart.getData().add(series);
		root.getChildren().add(chart);
		secondStage.setScene(secondScene);
		secondStage.show();
		
	}
	
	public static void main(String[] args) throws IOException {
		launch(args);
	}
}
