package dao;

import controladores.GeoJpaController;
import controladores.exceptions.NonexistentEntityException;
import entidades.Cliente;
import entidades.Geo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import wrapper.GeoWrapper;

/**
 *
 * @author Fernando Spanhol
 */
public class GeoDao extends GeoJpaController implements InterfaceDao<GeoWrapper> {

	public GeoDao() {
		super(Dao.POtiRoPU);
	}

	@Override
	public void clearCache() {
		Dao.ClearCache();
	}

	@Override
	public boolean criar(GeoWrapper e) {
		if (getInstance(e) != null) {
			return false;
		}
		try {
			create(e.get());
		} catch (Exception ex) {
			Logger.getLogger(GeoDao.class.getName()).log(Level.SEVERE, null, ex);
		}
		return true;
	}

	public boolean upsert(GeoWrapper e) {
		if (atualizar(e)) {
			return true;
		} else if (criar(e)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean atualizar(GeoWrapper e) {
		GeoWrapper velho = getById(e.getPlaceId());
		if (velho != null) {
			try {
				edit(e.get());
				return true;
			} catch (Exception ex) {
				Logger.getLogger(GeoDao.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean destruir(GeoWrapper e) {
		e = getInstance(e);
		if (e != null) {
			try {
				destroy(e.getPlaceId());
				return true;
			} catch (NonexistentEntityException ex) {
				Logger.getLogger(GeoDao.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return false;
	}

	public ObservableList<Geo> listar() {
		return FXCollections.observableList(findGeoEntities());
	}

	@Override
	public ObservableList<GeoWrapper> listarWrapper() {
		ObservableList<GeoWrapper> prop = FXCollections.observableArrayList();
		for (Geo e : FXCollections.observableList(findGeoEntities())) {
			prop.add(new GeoWrapper(e));
		}
		return prop;
	}

	public GeoWrapper getInstance(GeoWrapper e) {
		return getById(e.getPlaceId());
	}

	@Override
	public int count() {
		return getGeoCount();
	}

	public GeoWrapper getById(String placeId) {
		Geo e = null;
		EntityManager em = getEntityManager();
		try {
			e = (Geo) em.createNamedQuery("Geo.findByPlaceId").setParameter("placeId", placeId).getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			e = null;
		} finally {
			em.close();
		}
		if (e == null) {
			return null;
		} else {
			return new GeoWrapper(e);
		}
	}
}
