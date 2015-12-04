package wrapper;

import entidades.Caminhao;
import entidades.Motorista;
import java.util.Calendar;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelo.CustomDate;

/**
 *
 * @author Fernando Spanhol
 */
public class MotoristaWrapper extends Wrapper<Motorista> {


	private final IntegerProperty id;
	private final StringProperty nome;
	private final StringProperty ctps;
	private final StringProperty cnh;
	private final StringProperty cpf;
	private final StringProperty rg;
	private final ObjectProperty<CustomDate> admissao;
	private final StringProperty caminhaopreferencial;
	private final BooleanProperty ativo;

	public MotoristaWrapper(Motorista e) {
		this.elemento = e;
		id = new SimpleIntegerProperty();
		nome = new SimpleStringProperty();
		ctps = new SimpleStringProperty();
		cnh = new SimpleStringProperty();
		cpf = new SimpleStringProperty();
		rg = new SimpleStringProperty();
		admissao = new SimpleObjectProperty<>();
		caminhaopreferencial = new SimpleStringProperty();
		ativo = new SimpleBooleanProperty();
		id.set(e.getId());
		nome.set(e.getNome());
		ctps.set(e.getCtps());
		cnh.set(e.getCnh());
		cpf.set(e.getCpf());
		rg.set(e.getRg());
		if (e.getAdmissao() == null) {
			e.setAdmissao(Calendar.getInstance().getTime());
		}
		admissao.set(new CustomDate(e.getAdmissao().getTime()));
		if (e.getAtivo() == null) {
			e.setAtivo(false);
		}
		ativo.set(e.getAtivo());
		if (e.getFavcaminhaoid() != null) {
			caminhaopreferencial.set(e.getFavcaminhaoid().getPlaca());
		}
	}

	public Motorista get() {
		return elemento;
	}

	public void set(Motorista e) {
		this.elemento = e;
	}

	@Override
	public String toString() {
		return elemento.getNome();
	}

	public void setElemento(Motorista elemento) {
		this.elemento = elemento;
	}

	public Integer getId() {
		return id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
		elemento.setId(id);
	}

	public String getNome() {
		return nome.get();
	}

	public void setNome(String nome) {
		this.nome.set(nome);
		elemento.setNome(nome);
	}

	public String getCtps() {
		return ctps.get();
	}

	public void setCtps(String ctps) {
		this.ctps.set(ctps);
		elemento.setCtps(ctps);
	}

	public String getCnh() {
		return cnh.get();
	}

	public void setCnh(String cnh) {
		this.cnh.set(cnh);
		elemento.setCnh(cnh);
	}

	public String getCpf() {
		return cpf.get();
	}

	public void setCpf(String cpf) {
		this.cpf.set(cpf);
		elemento.setCpf(cpf);
	}

	public String getRg() {
		return rg.get();
	}

	public void setRg(String rg) {
		this.rg.set(rg);
		elemento.setRg(rg);
	}

	public CustomDate getAdmissao() {
		return admissao.get();
	}

	public void setAdmissao(CustomDate admissao) {
		this.admissao.set(admissao);
		elemento.setAdmissao(admissao);
	}
	
	public Boolean getAtivo() {
		return ativo.get();
	}

	public void setAtivo(Boolean ativo) {
		this.ativo.set(ativo);
		elemento.setAtivo(ativo);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty ctpsProperty() {
		return ctps;
	}

	public StringProperty cnhProperty() {
		return cnh;
	}

	public StringProperty cpfProperty() {
		return cpf;
	}

	public StringProperty rgProperty() {
		return rg;
	}

	public ObjectProperty<CustomDate> admissaoProperty() {
		return admissao;
	}

	public BooleanProperty ativoProperty() {
		return ativo;
	}

	public Caminhao getFavcaminhaoid() {
		return elemento.getFavcaminhaoid();
	}

	public StringProperty caminhaopreferencialProperty() {
		return caminhaopreferencial;
	}

	public void setFavcaminhaoid(Caminhao caminhaopref) {
		this.caminhaopreferencial.set(caminhaopref.getPlaca());
		elemento.setFavcaminhaoid(caminhaopref);
	}
}
