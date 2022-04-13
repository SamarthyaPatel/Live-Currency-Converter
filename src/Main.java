import java.io.IOException;
import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/***
 * <p> File name: Main.java</p>
 * <p> Creation date: 01.04.2022</p>
 * <p> Last modification date: 13.04.2022</p>
 * <p> Purpose of the program: Handling the GUI of the application by using JavaFx and doing the needful requested by the user.</p>
 * <p> Version history: 1.0 - pure code; 2.0 - comments added</p>
 * 
 * @author Samarthya Patel
 * @version 2.0
 */

public class Main extends Application {

	private static final int ONE = 1;
	private static final String CURRENCY_RATE_ERROR_MESSAGE = "There was an error in fetching the currency. Please try again.";
	private static final DecimalFormat DF = new DecimalFormat("0.00");
	
	private static final int SCENE_CANVAS_WIDTH = 600;
	private static final int SCENE_CANVAS_HEIGHT = 350;
	
	private String left = "GBP";
	private String right = "INR";
	private double currencyRate;
	private float amount = ONE;
	private double value;
	

	@Override
	public void start(Stage stage) throws IOException {
		
		Pane pane = new Pane();
		
		Button convert = new Button();
		
		Label result = new Label();
		
		ObservableList<String> currencies = FXCollections.observableArrayList(
				"Australia Dollar (AUD)",
				"Canada Dollar (CAD)",
				"China Yuan Renminbi (CNY)",
				"Egypt Pound (EGP)",
				"Euro Member Countries (EUR)",
				"Great Britain Pound (GBP)",
				"Indian Rupee (INR)",
				"Japan Yen (JPY)",
				"Russia Ruble (RUB)",
				"Saudi Arabia Riyal (SAR)",
				"Singapore Dollar (SGD)",
				"United Arab Emirates Dirham (AED)",
				"United States Dollar (USD)");
		
		Label amountLabel = new Label();
		amountLabel.setTranslateX(50);
		amountLabel.setTranslateY(50);
		amountLabel.setText("Amount: ");
		amountLabel.setFont(new Font("Arial", 14));
		
		Label from = new Label();
		from.setTranslateX(180);
		from.setTranslateY(50);
		from.setText("From this currency: ");
		from.setFont(new Font("Arial", 14));

		Label to = new Label();
		to.setTranslateX(380);
		to.setTranslateY(50);
		to.setText("To this currency: ");
		to.setFont(new Font("Arial", 14));
		
		TextField volume = new TextField();
		volume.setPromptText("1");
		volume.setFont(new Font("Arial", 14));
		volume.setTranslateX(40);
		volume.setTranslateY(80);
		volume.setPrefWidth(100);
		
		ComboBox<String> leftBox = new ComboBox<String>(currencies);
		leftBox.setPromptText("Select Currency.");
		leftBox.setStyle("-fx-font: 14px Arial;");
		leftBox.setTranslateX(170);
		leftBox.setTranslateY(80);
		leftBox.setPrefWidth(180);
		leftBox.setOnAction(e -> {
			setLeft(leftBox.getValue());
		});

		ComboBox<String> rightBox = new ComboBox<String>(currencies);
		rightBox.setPromptText("Select Currency.");
		rightBox.setStyle("-fx-font: 14px Arial;");
		rightBox.setTranslateX(370);
		rightBox.setTranslateY(80);
		rightBox.setPrefWidth(180);
		rightBox.setOnAction(e -> {
			setRight(rightBox.getValue());
		});
		
		convert.setText("Convert");
		convert.setTranslateX(50);
		convert.setTranslateY(150);
		convert.setMinSize(500, 15);
		convert.setFont(new Font("Arial", 16));
		convert.setOnAction(e -> {
			
			//Sets default value of 1 on the volume field.
			if(!volume.getText().equals("")) {
				amount = Integer.parseInt(volume.getText());
			} else {
				volume.setText("1");
			}
			
			//Sets default currency on the Left Side Currency field.
			if(leftBox.getValue() == null) {
				leftBox.setPromptText(left);
			}
			
			//Sets default currency on the Right Side Currency field.
			if(rightBox.getValue() == null) {
				rightBox.setPromptText(right);
			}
			
			CurrencyRate rate = new CurrencyRate(left, right);
			
			try {
				currencyRate = rate.getCurrencyRate();
			} catch (IOException e1) {
				System.out.println(CURRENCY_RATE_ERROR_MESSAGE);
				e1.printStackTrace();
			}
			
			value = (amount * currencyRate);
			
			result.setText(String.valueOf(DF.format(value)) + " " + rate.getRHS());
			result.setTranslateX(50);
			result.setTranslateY(250);
			result.setMinSize(500, 15);
			result.setAlignment(Pos.CENTER);
			result.setStyle("-fx-font: 30px Arial;");
		});
		
		pane.getChildren().addAll(amountLabel, from, to, volume, leftBox, rightBox, convert, result);

		Scene scene = new Scene(pane, SCENE_CANVAS_WIDTH, SCENE_CANVAS_HEIGHT);
		stage.setScene(scene);
		stage.setTitle("Live Currency Converter");
		stage.show();
		
	}

	/***
	 * Set method to set left currency
	 * 
	 * @param v	Value from the left combo box
	 */
	public void setLeft(String v) {
		switch (v) {
		case "Australia Dollar (AUD)":
			left = "AUD";
			break;
		case "Canada Dollar (CAD)":
			left = "CAD";
			break;
		case "China Yuan Renminbi (CNY)":
			left = "CNY";
			break;
		case "Egypt Pound (EGP)":
			left = "EGP";
			break;
		case "Euro Member Countries (EUR)":
			left = "EUR";
			break;
		case "Great Britain Pound (GBP)":
			left = "GBP";
			break;
		case "Indian Rupee (INR)":
			left = "INR";
			break;
		case "Japan Yen (JPY)":
			left = "JPY";
			break;
		case "Russia Ruble (RUB)":
			left = "RUB";
			break;
		case "Saudi Arabia Riyal (SAR)":
			left = "SAR";
			break;
		case "Singapore Dollar (SGD)":
			left = "SGD";
			break;
		case "United Arab Emirates Dirham (AED)":
			left = "AED";
			break;
		case "United States Dollar (USD)":
			left = "USD";
			break;
		}
	}

	/***
	 * Set method to set right currency
	 * 
	 * @param v	Value from the right combo box
	 */
	public void setRight(String v) {
		switch (v) {
		case "Australia Dollar (AUD)":
			right = "AUD";
			break;
		case "Canada Dollar (CAD)":
			right = "CAD";
			break;
		case "China Yuan Renminbi (CNY)":
			right = "CNY";
			break;
		case "Egypt Pound (EGP)":
			right = "EGP";
			break;
		case "Euro Member Countries (EUR)":
			right = "EUR";
			break;
		case "Great Britain Pound (GBP)":
			right = "GBP";
			break; 
		case "Indian Rupee (INR)":
			right = "INR";
			break;
		case "Japan Yen (JPY)":
			right = "JPY";
			break;
		case "Russia Ruble (RUB)":
			right = "RUB";
			break;
		case "Saudi Arabia Riyal (SAR)":
			right = "SAR";
			break;
		case "Singapore Dollar (SGD)":
			right = "SGD";
			break;
		case "United Arab Emirates Dirham (AED)":
			right = "AED";
			break;
		case "United States Dollar (USD)":
			right = "USD";
			break;
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
