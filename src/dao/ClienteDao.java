package dao;

import controladores.ClienteJpaController;
import controladores.exceptions.NonexistentEntityException;
import entidades.Cliente;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import wrapper.ClienteWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class ClienteDao extends ClienteJpaController implements InterfaceDao<ClienteWrapper> {

	public ClienteDao() {
		super(Dao.POtiRoPU);
	}

	@Override
	public void clearCache() {
		Dao.ClearCache();
	}

	public ClienteWrapper getByNome(String nome) {
		Cliente e = null;
		EntityManager em = getEntityManager();
		try {
			e = (Cliente) em.createNamedQuery("Clientes.findByNome").setParameter("nome", nome).getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			e = null;
		} finally {
			em.close();
		}
		return new ClienteWrapper(e);
	}

	@Override
	public boolean criar(ClienteWrapper e) {
		if (getInstance(e) != null) {
			return false;
		}
		try {
			create(e.get());
		} catch (Exception ex) {
			Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return true;
	}

	@Override
	public boolean atualizar(ClienteWrapper e) {
		ClienteWrapper velho = getByCodigo(e.getCodigo());
		if (velho != null) {
			try {
				edit(e.get());
				return true;
			} catch (Exception ex) {
				Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean destruir(ClienteWrapper e) {
		e = getInstance(e);
		if (e != null) {
			try {
				destroy(e.getCodigo());
				return true;
			} catch (NonexistentEntityException ex) {
				Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return false;
	}

	public ObservableList<Cliente> listar() {
		return FXCollections.observableList(findClienteEntities());
	}

	@Override
	public ObservableList<ClienteWrapper> listarWrapper() {
		ObservableList<ClienteWrapper> prop = FXCollections.observableArrayList();
		for (Cliente e : FXCollections.observableList(findClienteEntities())) {
			prop.add(new ClienteWrapper(e));
		}
		return prop;
	}

	public ClienteWrapper getInstance(ClienteWrapper e) {
		return getByNome(e.getNome());
	}

	@Override
	public int count() {
		return getClienteCount();
	}

	public ClienteWrapper getByCodigo(int codigo) {
		Cliente e = null;
		EntityManager em = getEntityManager();
		try {
			e = (Cliente) em.createNamedQuery("Cliente.findByCodigo").setParameter("codigo", codigo).getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			e = null;
		} finally {
			em.close();
		}
		return new ClienteWrapper(e);
	}
}
