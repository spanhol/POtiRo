package dao;

import controladores.DistanciaJpaController;
import controladores.exceptions.NonexistentEntityException;
import entidades.Distancia;
import entidades.DistanciaPK;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import wrapper.DistanciaWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class DistanciaDao extends DistanciaJpaController implements InterfaceDao<DistanciaWrapper> {

	public DistanciaDao() {
		super(Dao.POtiRoPU);
	}

	@Override
	public void clearCache() {
		Dao.ClearCache();
	}

	@Override
	public boolean criar(DistanciaWrapper e) {
		if (getInstance(e) != null) {
			return false;
		}
		try {
			create(e.get());
		} catch (Exception ex) {
			Logger.getLogger(DistanciaDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return true;
	}

	public boolean upsert(DistanciaWrapper e) {
		if (atualizar(e)) {
			return true;
		} else if (criar(e)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean atualizar(DistanciaWrapper e) {
		DistanciaWrapper velho = getById(e.get().getDistanciaPK());
		if (velho != null) {
			try {
				edit(e.get());
				return true;
			} catch (Exception ex) {
				Logger.getLogger(DistanciaDao.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean destruir(DistanciaWrapper e) {
		e = getInstance(e);
		if (e != null) {
			try {
				destroy(e.get().getDistanciaPK());
				return true;
			} catch (NonexistentEntityException ex) {
				Logger.getLogger(DistanciaDao.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return false;
	}

	public ObservableList<Distancia> listar() {
		return FXCollections.observableList(findDistanciaEntities());
	}

	@Override
	public ObservableList<DistanciaWrapper> listarWrapper() {
		ObservableList<DistanciaWrapper> prop = FXCollections.observableArrayList();
		for (Distancia e : FXCollections.observableList(findDistanciaEntities())) {
			prop.add(new DistanciaWrapper(e));
		}
		return prop;
	}

	public DistanciaWrapper getInstance(DistanciaWrapper e) {
		return getById(e.get().getDistanciaPK());
	}

	@Override
	public int count() {
		return getDistanciaCount();
	}

	public DistanciaWrapper getById(DistanciaPK pk) {
		Distancia e = null;
		EntityManager em = getEntityManager();
		try {
			String sql = "SELECT d FROM Distancia d WHERE d.distanciaPK.placea = :placea AND d.distanciaPK.placeb = :placeb";
			e = (Distancia) em.createQuery(sql).setParameter("placea", pk.getPlacea()).setParameter("placeb", pk.getPlaceb()).getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			e = null;
		} finally {
			em.close();
		}
		if (e == null) {
			return null;
		} else {
			return new DistanciaWrapper(e);
		}
	}
}
