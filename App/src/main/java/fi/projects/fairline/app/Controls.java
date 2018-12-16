package fi.projects.fairline.app;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Insets;

class Controls extends HBox {

    private Label itemLabel;
    private TextField itemField;
    private Label amountLabel;
    private TextField amountField;
    private Button add;

    public Controls () {
        createControls();
        setControlMargins();
        getStyleClass().add("controlBox");
    }

    private void createControls() {
        itemLabel = new Label("Item: ");
        itemLabel.getStyleClass().add("headerLabel");
        itemField = new TextField();
        itemField.setPrefWidth(100);
        itemField.getStyleClass().add("controlField");
        amountLabel = new Label("Amount: ");
        amountLabel.getStyleClass().add("headerLabel");
        amountField = new TextField();
        amountField.setPrefWidth(60);
        amountField.getStyleClass().add("controlField");
        add = new Button();
        add.getStyleClass().add("buttonAdd");

        getChildren().addAll(itemLabel, itemField, amountLabel, amountField, add);
    }

    private void setControlMargins() {
        HBox.setMargin(itemLabel, new Insets(10, 0, 10, 5));
        HBox.setMargin(itemField, new Insets(10, 5, 10, 5));
        HBox.setMargin(amountLabel, new Insets(10, 0, 10, 0));
        HBox.setMargin(amountField, new Insets(10, 5, 10, 5));
        HBox.setMargin(add, new Insets(7, 0, 10, 45));
    }

    public Button getAddButton() {
        return add;
    }

    public TextField getItemField() {
        return itemField;
    }

    public TextField getAmountField() {
        return amountField;
    }
}