package modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Encapsula Date para fornecer data como propriedade ou bean
 *
 * @author Fernando Spanhol
 */
public class CustomDate extends java.sql.Date {

	private final ObjectProperty<Date> data;

	public CustomDate(long date) {
		super(date);
		data = new SimpleObjectProperty<>();
		data.set(new Date(date));
	}

	public ObjectProperty dataProperty() {
		return data;
	}

	public Date getData() {
		return data.get();
	}

	public void setData(Date date) {
		this.data.set(date);
		setTime(date.getTime());
	}

	@Override
	public String toString() {
		return new SimpleDateFormat("dd/MM/yyyy").format(this);
	}
}
