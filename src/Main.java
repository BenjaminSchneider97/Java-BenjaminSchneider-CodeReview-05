import javafx.application.Application;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.*;
import java.io.*;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage){

        //basic layout for components
        BorderPane root = new BorderPane();
        BorderPane products = new BorderPane();
        GridPane productDetails = new GridPane();
        VBox bottomLayer = new VBox();
        VBox description = new VBox();
        HBox buttons = new HBox();

        //list on the side showing the products
        ObservableList<Product> allProducts = FXCollections.observableArrayList (
                new Product(
                        "Pfeffer",
                        "1 Stück",
                        3.49,
                        2.79,
                        "/images/pfeffer__600x600.jpg",
                        "Schwarzer Pfeffer verleiht Ihren Speisen eine pikante Schärfe, besonders wenn er länger mitgekocht wird."
                ),
                new Product(
                        "Schafmilchkäse",
                        "200 Gramm Packung",
                        2.59,
                        1.99,
                        "/images/cheese_salakis__600x600.jpg",
                        "Hier gibt es keine Beschreibung, weil unsere Handelskette kennst sich nur bedingt damit aus, wie man eine Werbebeschreibung schreibt."
                ),
                new Product(
                        "Vöslauer",
                        "1.5 Liter Flasche",
                        0.75,
                        0.49,
                        "/images/voslauer__600x600.jpg",
                        "Spritziges Vöslauer Mineralwasser."
                ),
                new Product(
                        "Zucker",
                        "500 Gramm Paket",
                        1.39,
                        0.89,
                        "/images/zucker__600x600.jpg",
                        "Natürliches Gelieren wird durch Apfelpektin unterstützt, welches im richtigen Verhältnis mit Zitronensäure und Kristallzucker abgemischt wurde."
                )
        );

        ListView<Product> productList = new ListView<>();
        productList.setItems(allProducts);

        productList.setMinWidth(275);

        //layout for the product fields
        Label productName = new Label("Prod. Name");
        TextField productNameField = new TextField();
        productNameField.setCursor(Cursor.DEFAULT);
        productNameField.setEditable(false);

        Label quantity = new Label("Quantity");
        TextField quantityField = new TextField();
        quantityField.setCursor(Cursor.DEFAULT);
        quantityField.setEditable(false);

        Label oldPrice = new Label("Old Price");
        TextField oldPriceField = new TextField();

        Label newPrice = new Label("New Price");
        TextField newPriceField = new TextField();

        Label eur = new Label("EUR");
        eur.setFont(Font.font("", FontWeight.BOLD, 12));
        eur.setTextFill(Color.PURPLE);

        Label eur2 = new Label("EUR");
        eur2.setFont(Font.font("", FontWeight.BOLD, 12));
        eur2.setTextFill(Color.PURPLE);

        //append content to the product details layout
        productDetails.add(productName, 0, 0, 1, 1);
        productDetails.add(productNameField, 1, 0, 1, 1);
        productDetails.add(quantity, 0, 1, 1, 1);
        productDetails.add(quantityField, 1, 1, 1, 1);
        productDetails.add(oldPrice, 0, 2, 1, 1);
        productDetails.add(oldPriceField, 1, 2, 1, 1);
        productDetails.add(eur, 2, 2, 1, 1);
        productDetails.add(newPrice, 0, 3, 1, 1);
        productDetails.add(newPriceField, 1, 3, 1, 1);
        productDetails.add(eur2, 2, 3, 1, 1);

        productDetails.setHgap(10);
        productDetails.setVgap(25);

        //layout for description
        Label descriptionTitle = new Label("Description: ");
        descriptionTitle.setFont(Font.font("Montserrat", FontWeight.BOLD, 25));
        Label descriptionDesc = new Label("-");
        descriptionDesc.setWrapText(true);

        description.getChildren().addAll(descriptionTitle, descriptionDesc);

        //layout for the buttons
        Button btnUpdate = new Button("Update");
        btnUpdate.setCursor(Cursor.HAND);

        Button btnReport = new Button("Report");
        btnReport.setCursor(Cursor.HAND);

        buttons.getChildren().addAll(btnUpdate, btnReport);
        buttons.setSpacing(10);

        //show product data
        productList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product product, Product t1) {
                productNameField.setText(t1.getName());
                quantityField.setText(t1.getQuantity());
                oldPriceField.setText(Double.toString(t1.getOldPrice()));
                newPriceField.setText(Double.toString(t1.getNewPrice()));
                descriptionDesc.setText(t1.getDescription());

                String imagePath = t1.getImagePath();
                InputStream input = this.getClass().getResourceAsStream(imagePath);
                Image image = new Image(input);
                ImageView showImage = new ImageView(image);
                showImage.setFitHeight(245);
                showImage.setFitWidth(245);

                productDetails.add(showImage, 0, 4, 2, 2);
            }
        });

        //append content to bottom layer
        bottomLayer.getChildren().addAll(description, buttons);
        bottomLayer.setSpacing(10);

        //append product details to product pane
        products.setCenter(productDetails);
        products.setBottom(bottomLayer);
        products.setPadding(new Insets(15));

        //append content to root layout
        root.setRight(productList);
        root.setCenter(products);

        //btnUpdate
        btnUpdate.setOnAction(update -> {
            int selIdx = productList.getSelectionModel().getSelectedIndex();
            if (selIdx != -1) {
                double newOldPrice = Double.valueOf(oldPriceField.getText());
                double newNewPrice = Double.valueOf(newPriceField.getText());
                productList.getItems().get(selIdx).setOldPrice(newOldPrice);
                productList.getItems().get(selIdx).setNewPrice(newNewPrice);
                productList.refresh();
                updateSuccess();
            } else{
                noSelection();
            }
        });

        //btnReport
        btnReport.setOnAction(report -> {
            try {
                writeReport(allProducts);
                successReport();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //settings for primary stage
        primaryStage.setTitle("Set action prices");
        primaryStage.setScene(new Scene(root, 950, 580));
        primaryStage.getIcons().add(new Image("/images/Java-icon.png"));
        primaryStage.show();
    }

    //error msg for not selecting an item to update
    private void noSelection(){
        Alert noSelection = new Alert(Alert.AlertType.ERROR);
        noSelection.setTitle("Error");
        noSelection.setHeaderText(null);
        noSelection.setContentText("Please select the item from the list you want to update.");
        noSelection.showAndWait();
    }

    //success msg after updating
    private void updateSuccess(){
        Alert updateSuccess = new Alert(Alert.AlertType.INFORMATION);
        updateSuccess.setTitle("Success");
        updateSuccess.setHeaderText(null);
        updateSuccess.setContentText("Product successfully updated.");
        updateSuccess.showAndWait();
    }

    //success msg you see for creating a report
    private void successReport(){
        Alert successReport = new Alert(Alert.AlertType.INFORMATION);
        successReport.setTitle("Success");
        successReport.setHeaderText(null);
        successReport.setContentText("\"report.txt\" successfully created.");
        successReport.showAndWait();
    }

    //creates a new file and writes the report in it
    private void writeReport(ObservableList<Product> allProducts) throws IOException {
        FileWriter fileWriter = new FileWriter("./src/report.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        allProducts.forEach(data ->{
            printWriter.println(data.getName());
            printWriter.println(data.getQuantity());
            printWriter.println(data.getDescription());
            printWriter.println("Instead " + data.getOldPrice() + "€");
            printWriter.println("Action price " + data.getNewPrice() + "€");
            printWriter.println("");
        });
        printWriter.close();
    }

    //launches the application
    public static void main(String[] args){
        launch(args);
    }
}