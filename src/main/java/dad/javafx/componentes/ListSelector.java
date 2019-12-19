package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

public class ListSelector<T> extends GridPane implements Initializable {
	 //Model

	private ListProperty<T> left = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ListProperty<T> rigth = new SimpleListProperty<>(FXCollections.observableArrayList());
	private StringProperty leftTitle = new SimpleStringProperty();
	private StringProperty rigthTitle = new SimpleStringProperty();
	
	
	//View
	
		@FXML
	    private Label leftLabel, rigthLabel;

	    @FXML
	    private ListView<T> leftList , rigthList;

	    @FXML
	    private Button moveToRigthButton, moveAllToRigthButton, moveAllToLeftButton, moveToLeftButton;

	    public ListSelector() {
			super();
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListSelectorView.fxml"));
			loader.setController(this);
			loader.setRoot(this); //Establecemos la raiz de la vista
			loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		leftList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		rigthList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		leftList.itemsProperty().bind(left);
		rigthList.itemsProperty().bind(rigth);
		
		leftLabel.textProperty().bind(leftTitle);
		rigthLabel.textProperty().bind(rigthTitle);
	
		
		//TODO DESABILITAR BOTONES
	}
	
	@FXML
    void onMoveAllToLeftAction(ActionEvent event) {
		left.addAll(rigth);
		rigth.clear();
    }

    @FXML
    void onMoveAllToRigthAction(ActionEvent event) {
    	rigth.addAll(left);
		left.clear();
    }

    @FXML
    void onMoveToLeftAction(ActionEvent event) {
    	left.addAll(rigthList.getSelectionModel().getSelectedItems());
    	rigth.removeAll(rigthList.getSelectionModel().getSelectedItems());
    }

    @FXML
    void onMoveToRigthAction(ActionEvent event) {
    	rigth.addAll(leftList.getSelectionModel().getSelectedItems());
    	left.removeAll(leftList.getSelectionModel().getSelectedItems());
    }
	public final ListProperty<T> leftProperty() {
		return this.left;
	}
	
	public final ObservableList<T> getLeft() {
		return this.leftProperty().get();
	}
	
	public final void setLeft(final ObservableList<T> left) {
		this.leftProperty().set(left);
	}
	
	public final ListProperty<T> rigthProperty() {
		return this.rigth;
	}
	
	public final ObservableList<T> getRigth() {
		return this.rigthProperty().get();
	}
	
	public final void setRigth(final ObservableList<T> rigth) {
		this.rigthProperty().set(rigth);
	}
	
	public final StringProperty leftTitleProperty() {
		return this.leftTitle;
	}
	
	public final String getLeftTitle() {
		return this.leftTitleProperty().get();
	}
	
	public final void setLeftTitle(final String leftTitle) {
		this.leftTitleProperty().set(leftTitle);
	}
	
	public final StringProperty rigthTitleProperty() {
		return this.rigthTitle;
	}
	
	public final String getRigthTitle() {
		return this.rigthTitleProperty().get();
	}
	
	public final void setRigthTitle(final String rigthTitle) {
		this.rigthTitleProperty().set(rigthTitle);
	}

}
