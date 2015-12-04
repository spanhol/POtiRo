package wrapper;

import entidades.Regiao;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Fernando Spanhol
 */
public class RegiaoWrapper extends Wrapper<Regiao> {

	private final IntegerProperty id;
	private final StringProperty abreviacao;
	private final StringProperty nome;
	private final SimpleBooleanProperty selected;

	public RegiaoWrapper(Regiao elemento) {
		this.elemento = elemento;
		selected = new SimpleBooleanProperty(false);
		id = new SimpleIntegerProperty();
		abreviacao = new SimpleStringProperty();
		nome = new SimpleStringProperty();
		id.set(elemento.getId());
		abreviacao.set(elemento.getAbreviacao());
		nome.set(elemento.getNome());
	}

	public SimpleBooleanProperty selectedProperty() {
		return selected;
	}

	public boolean getSelected() {
		return selected.get();
	}

	@Override
	public String toString() {
		return elemento.getNome();
	}

	@Override
	public Regiao get() {
		return elemento;
	}

	public void set(Regiao e) {
		this.elemento = e;
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public String getAbreviacao() {
		return abreviacao.get();
	}

	public void setAbreviacao(String abreviacao) {
		this.abreviacao.set(abreviacao);
	}

	public String getNome() {
		return nome.get();
	}

	public void setNome(String nome) {
		this.nome.set(nome);
	}

	public void setElemento(Regiao elemento) {
		this.elemento = elemento;
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty abreviacaoProperty() {
		return abreviacao;
	}

	public StringProperty nomeProperty() {
		return nome;
	}
}
