/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Cliente;
import entidades.Geo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Fernando Spanhol
 */
public class GeoJpaController implements Serializable {

	public GeoJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Geo geo) throws PreexistingEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Cliente codigoCli = geo.getCodigoCli();
			if (codigoCli != null) {
				codigoCli = em.getReference(codigoCli.getClass(), codigoCli.getCodigo());
				geo.setCodigoCli(codigoCli);
			}
			em.persist(geo);
			if (codigoCli != null) {
				codigoCli.getGeoCollection().add(geo);
				codigoCli = em.merge(codigoCli);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findGeo(geo.getPlaceId()) != null) {
				throw new PreexistingEntityException("Geo " + geo + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Geo geo) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Geo persistentGeo = em.find(Geo.class, geo.getPlaceId());
			Cliente codigoCliOld = persistentGeo.getCodigoCli();
			Cliente codigoCliNew = geo.getCodigoCli();
			if (codigoCliNew != null) {
				codigoCliNew = em.getReference(codigoCliNew.getClass(), codigoCliNew.getCodigo());
				geo.setCodigoCli(codigoCliNew);
			}
			geo = em.merge(geo);
			if (codigoCliOld != null && !codigoCliOld.equals(codigoCliNew)) {
				codigoCliOld.getGeoCollection().remove(geo);
				codigoCliOld = em.merge(codigoCliOld);
			}
			if (codigoCliNew != null && !codigoCliNew.equals(codigoCliOld)) {
				codigoCliNew.getGeoCollection().add(geo);
				codigoCliNew = em.merge(codigoCliNew);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				String id = geo.getPlaceId();
				if (findGeo(id) == null) {
					throw new NonexistentEntityException("The geo with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(String id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Geo geo;
			try {
				geo = em.getReference(Geo.class, id);
				geo.getPlaceId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The geo with id " + id + " no longer exists.", enfe);
			}
			Cliente codigoCli = geo.getCodigoCli();
			if (codigoCli != null) {
				codigoCli.getGeoCollection().remove(geo);
				codigoCli = em.merge(codigoCli);
			}
			em.remove(geo);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Geo> findGeoEntities() {
		return findGeoEntities(true, -1, -1);
	}

	public List<Geo> findGeoEntities(int maxResults, int firstResult) {
		return findGeoEntities(false, maxResults, firstResult);
	}

	private List<Geo> findGeoEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Geo.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Geo findGeo(String id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Geo.class, id);
		} finally {
			em.close();
		}
	}

	public int getGeoCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Geo> rt = cq.from(Geo.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
