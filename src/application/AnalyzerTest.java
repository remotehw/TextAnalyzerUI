package application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.scene.chart.XYChart;

class AnalyzerTest {

	@Test
	void testAnalyzer() throws IOException {
		System.out.println("testing analyzer\n");
		
		Main test = new Main();
		ArrayList<Entry<String, Integer>> output = test.analyzer();
		assertNotNull(output);
		assertFalse(output.isEmpty());
		
	}

}
