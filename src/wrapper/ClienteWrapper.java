package wrapper;

import entidades.Cliente;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Fernando Spanhol
 */
public class ClienteWrapper extends Wrapper<Cliente> {

	private final IntegerProperty codigo = new SimpleIntegerProperty();
	private final StringProperty cnpj = new SimpleStringProperty();
	private final StringProperty rua = new SimpleStringProperty();
	private final StringProperty bairro = new SimpleStringProperty();
	private final StringProperty cep = new SimpleStringProperty();
	private final StringProperty estado = new SimpleStringProperty();
	private final StringProperty nome = new SimpleStringProperty();

	private void init(Cliente elemento) {
		this.elemento = elemento;
		codigo.setValue(elemento.getCodigo());
		cnpj.setValue(elemento.getCnpj());
		bairro.setValue(elemento.getBairro());
		cep.setValue(elemento.getCep());
		estado.setValue(elemento.getEstado());
		nome.setValue(elemento.getNome());
	}

	public ClienteWrapper() {
		Cliente e = new Cliente();
		init(e);
	}

	public ClienteWrapper(Cliente e) {
		init(e);
	}

	public int getCodigo() {
		return codigo.get();
	}

	public void setCodigo(int value) {
		elemento.setCodigo(value);
		codigo.set(value);
	}

	public IntegerProperty codigoProperty() {
		return codigo;
	}

	public String getCnpj() {
		return cnpj.get();
	}

	public void setCnpj(String value) {
		elemento.setCnpj(value);
		cnpj.set(value);
	}

	public StringProperty cnpjProperty() {
		return cnpj;
	}

	public String getRua() {
		return rua.get();
	}

	public void setRua(String value) {
		elemento.setRua(value);
		rua.set(value);
	}

	public StringProperty ruaProperty() {
		return rua;
	}

	public String getBairro() {
		return bairro.get();
	}

	public void setBairro(String value) {
		elemento.setBairro(value);
		bairro.set(value);
	}

	public StringProperty bairroProperty() {
		return bairro;
	}

	public String getCep() {
		return cep.get();
	}

	public void setCep(String value) {
		elemento.setCep(value);
		cep.set(value);
	}

	public StringProperty cepProperty() {
		return cep;
	}

	public String getUf() {
		return estado.get();
	}

	public void setUf(String value) {
		elemento.setEstado(value);
		estado.set(value);
	}

	public StringProperty ufProperty() {
		return estado;
	}

	public String getNome() {
		return nome.get();
	}

	public void setNome(String value) {
		elemento.setNome(value);
		nome.set(value);
	}

	public StringProperty nomeProperty() {
		return nome;
	}

}
