module TextAnalyzeUI {
	requires javafx.controls;
	requires javafx.base;
	requires javafx.graphics;
	requires org.junit.jupiter.api;
	requires junit;
	
	opens application to javafx.graphics, javafx.fxml;
}
