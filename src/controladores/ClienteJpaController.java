/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidades.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Geo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Fernando Spanhol
 */
public class ClienteJpaController implements Serializable {

	public ClienteJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Cliente cliente) throws PreexistingEntityException, Exception {
		if (cliente.getGeoCollection() == null) {
			cliente.setGeoCollection(new ArrayList<Geo>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Collection<Geo> attachedGeoCollection = new ArrayList<Geo>();
			for (Geo geoCollectionGeoToAttach : cliente.getGeoCollection()) {
				geoCollectionGeoToAttach = em.getReference(geoCollectionGeoToAttach.getClass(), geoCollectionGeoToAttach.getPlaceId());
				attachedGeoCollection.add(geoCollectionGeoToAttach);
			}
			cliente.setGeoCollection(attachedGeoCollection);
			em.persist(cliente);
			for (Geo geoCollectionGeo : cliente.getGeoCollection()) {
				Cliente oldCodigoCliOfGeoCollectionGeo = geoCollectionGeo.getCodigoCli();
				geoCollectionGeo.setCodigoCli(cliente);
				geoCollectionGeo = em.merge(geoCollectionGeo);
				if (oldCodigoCliOfGeoCollectionGeo != null) {
					oldCodigoCliOfGeoCollectionGeo.getGeoCollection().remove(geoCollectionGeo);
					oldCodigoCliOfGeoCollectionGeo = em.merge(oldCodigoCliOfGeoCollectionGeo);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findCliente(cliente.getCodigo()) != null) {
				throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Cliente persistentCliente = em.find(Cliente.class, cliente.getCodigo());
			Collection<Geo> geoCollectionOld = persistentCliente.getGeoCollection();
			Collection<Geo> geoCollectionNew = cliente.getGeoCollection();
			Collection<Geo> attachedGeoCollectionNew = new ArrayList<Geo>();
			for (Geo geoCollectionNewGeoToAttach : geoCollectionNew) {
				geoCollectionNewGeoToAttach = em.getReference(geoCollectionNewGeoToAttach.getClass(), geoCollectionNewGeoToAttach.getPlaceId());
				attachedGeoCollectionNew.add(geoCollectionNewGeoToAttach);
			}
			geoCollectionNew = attachedGeoCollectionNew;
			cliente.setGeoCollection(geoCollectionNew);
			cliente = em.merge(cliente);
			for (Geo geoCollectionOldGeo : geoCollectionOld) {
				if (!geoCollectionNew.contains(geoCollectionOldGeo)) {
					geoCollectionOldGeo.setCodigoCli(null);
					geoCollectionOldGeo = em.merge(geoCollectionOldGeo);
				}
			}
			for (Geo geoCollectionNewGeo : geoCollectionNew) {
				if (!geoCollectionOld.contains(geoCollectionNewGeo)) {
					Cliente oldCodigoCliOfGeoCollectionNewGeo = geoCollectionNewGeo.getCodigoCli();
					geoCollectionNewGeo.setCodigoCli(cliente);
					geoCollectionNewGeo = em.merge(geoCollectionNewGeo);
					if (oldCodigoCliOfGeoCollectionNewGeo != null && !oldCodigoCliOfGeoCollectionNewGeo.equals(cliente)) {
						oldCodigoCliOfGeoCollectionNewGeo.getGeoCollection().remove(geoCollectionNewGeo);
						oldCodigoCliOfGeoCollectionNewGeo = em.merge(oldCodigoCliOfGeoCollectionNewGeo);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = cliente.getCodigo();
				if (findCliente(id) == null) {
					throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Cliente cliente;
			try {
				cliente = em.getReference(Cliente.class, id);
				cliente.getCodigo();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
			}
			Collection<Geo> geoCollection = cliente.getGeoCollection();
			for (Geo geoCollectionGeo : geoCollection) {
				geoCollectionGeo.setCodigoCli(null);
				geoCollectionGeo = em.merge(geoCollectionGeo);
			}
			em.remove(cliente);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Cliente> findClienteEntities() {
		return findClienteEntities(true, -1, -1);
	}

	public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
		return findClienteEntities(false, maxResults, firstResult);
	}

	private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Cliente.class));
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

	public Cliente findCliente(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Cliente.class, id);
		} finally {
			em.close();
		}
	}

	public int getClienteCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Cliente> rt = cq.from(Cliente.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
