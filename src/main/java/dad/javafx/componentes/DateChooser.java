package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class DateChooser extends HBox implements Initializable {
	//VIEW
	@FXML
	private ComboBox<String> diaCombo;

	@FXML
	private ComboBox<String> mesCombo;

	@FXML
	private ComboBox<String> yearCombo;

	// MODEL
	private ListProperty<String> dias = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty diaSeleccionado = new SimpleIntegerProperty();
	private ListProperty<String> meses = new SimpleListProperty<>(FXCollections.observableArrayList());
	private IntegerProperty mesSeleccionado = new SimpleIntegerProperty();
	private ListProperty<String> years = new SimpleListProperty<>(FXCollections.observableArrayList());
	private StringProperty yearSeleccionado = new SimpleStringProperty();

	private String[] nombresMeses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto",
			"Septiembre", "Octubre", "Noviembre", "Diciembre" };
	private ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<>();
	private Locale localSpain = new Locale("es", "ES");

	public DateChooser() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DateChooserView.fxml"));
			loader.setController(this);
			loader.setRoot(this); 
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ComboBox
		dias.bindBidirectional(diaCombo.itemsProperty());
		diaSeleccionado.bind(diaCombo.getSelectionModel().selectedIndexProperty());

		meses.bindBidirectional(mesCombo.itemsProperty());
		mesSeleccionado.bind(mesCombo.getSelectionModel().selectedIndexProperty());
		mesSeleccionado.addListener((o, ov, nv) -> {
			if (ov != null)
				getDays();
		});
		meses.addAll(nombresMeses);

		years.bindBidirectional(yearCombo.itemsProperty());
		yearSeleccionado.bind(yearCombo.getSelectionModel().selectedItemProperty());
		yearSeleccionado.addListener((o, ov, nv) -> {
			if (ov != null)
				getDays();
		});
		getYears();

		mesCombo.getSelectionModel().select(0);
		yearCombo.getSelectionModel().select(0);
		actualizarFecha();
	}

	private void getYears() {
		for (int i = 2020; i >= 1900; i--) {
			years.add(String.valueOf(i));
		}
	}

	private void getDays() {
		int dia = diaSeleccionado.getValue() + 1;
		if (dia <= 0) {
			dia = 1;
		}

		Calendar cal = new GregorianCalendar(localSpain);
		int year = Calendar.YEAR;
		if (yearSeleccionado.get() != null) {
			year = Integer.parseInt(yearSeleccionado.get());
		}
		cal.set(year, mesSeleccionado.get(), 1);
		int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<String> numeros = new ArrayList<>();
		for (int i = 1; i <= max; i++) {
			numeros.add(String.valueOf(i));
		}

		if (dia > max) {
			dia = max;
		}

		dias.setAll(numeros);
		diaCombo.getSelectionModel().select(String.valueOf(dia));
		actualizarFecha();
	}

	private void actualizarFecha() {
		int month = mesSeleccionado.get() + 1;
		int day = diaSeleccionado.get() + 1;
		int year = yearSeleccionado.get() != null ? Integer.parseInt(yearSeleccionado.get()) : Calendar.YEAR;

		fecha.set(LocalDate.of(year, month, day));
	}

	public final ObjectProperty<LocalDate> fechaProperty() {
		return this.fecha;
	}

	public final LocalDate getFecha() {
		return this.fechaProperty().get();
	}

	public final void setFecha(final LocalDate fecha) {
		this.fechaProperty().set(fecha);
	}
}
