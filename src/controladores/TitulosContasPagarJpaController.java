/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import entidades.TitulosContasPagar;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Fernando Spanhol
 */
public class TitulosContasPagarJpaController implements Serializable {

	public TitulosContasPagarJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(TitulosContasPagar titulosContasPagar) throws PreexistingEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(titulosContasPagar);
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findTitulosContasPagar(titulosContasPagar.getId()) != null) {
				throw new PreexistingEntityException("TitulosContasPagar " + titulosContasPagar + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(TitulosContasPagar titulosContasPagar) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			titulosContasPagar = em.merge(titulosContasPagar);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = titulosContasPagar.getId();
				if (findTitulosContasPagar(id) == null) {
					throw new NonexistentEntityException("The titulosContasPagar with id " + id + " no longer exists.");
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
			TitulosContasPagar titulosContasPagar;
			try {
				titulosContasPagar = em.getReference(TitulosContasPagar.class, id);
				titulosContasPagar.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The titulosContasPagar with id " + id + " no longer exists.", enfe);
			}
			em.remove(titulosContasPagar);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<TitulosContasPagar> findTitulosContasPagarEntities() {
		return findTitulosContasPagarEntities(true, -1, -1);
	}

	public List<TitulosContasPagar> findTitulosContasPagarEntities(int maxResults, int firstResult) {
		return findTitulosContasPagarEntities(false, maxResults, firstResult);
	}

	private List<TitulosContasPagar> findTitulosContasPagarEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(TitulosContasPagar.class));
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

	public TitulosContasPagar findTitulosContasPagar(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(TitulosContasPagar.class, id);
		} finally {
			em.close();
		}
	}

	public int getTitulosContasPagarCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<TitulosContasPagar> rt = cq.from(TitulosContasPagar.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
