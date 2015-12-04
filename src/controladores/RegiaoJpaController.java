/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.Regiao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Viagem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Fernando Spanhol
 */
public class RegiaoJpaController implements Serializable {

	public RegiaoJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Regiao regiao) {
		if (regiao.getViagemCollection() == null) {
			regiao.setViagemCollection(new ArrayList<Viagem>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Collection<Viagem> attachedViagemCollection = new ArrayList<Viagem>();
			for (Viagem viagemCollectionViagemToAttach : regiao.getViagemCollection()) {
				viagemCollectionViagemToAttach = em.getReference(viagemCollectionViagemToAttach.getClass(), viagemCollectionViagemToAttach.getId());
				attachedViagemCollection.add(viagemCollectionViagemToAttach);
			}
			regiao.setViagemCollection(attachedViagemCollection);
			em.persist(regiao);
			for (Viagem viagemCollectionViagem : regiao.getViagemCollection()) {
				viagemCollectionViagem.getRegiaoCollection().add(regiao);
				viagemCollectionViagem = em.merge(viagemCollectionViagem);
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Regiao regiao) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Regiao persistentRegiao = em.find(Regiao.class, regiao.getId());
			Collection<Viagem> viagemCollectionOld = persistentRegiao.getViagemCollection();
			Collection<Viagem> viagemCollectionNew = regiao.getViagemCollection();
			Collection<Viagem> attachedViagemCollectionNew = new ArrayList<Viagem>();
			for (Viagem viagemCollectionNewViagemToAttach : viagemCollectionNew) {
				viagemCollectionNewViagemToAttach = em.getReference(viagemCollectionNewViagemToAttach.getClass(), viagemCollectionNewViagemToAttach.getId());
				attachedViagemCollectionNew.add(viagemCollectionNewViagemToAttach);
			}
			viagemCollectionNew = attachedViagemCollectionNew;
			regiao.setViagemCollection(viagemCollectionNew);
			regiao = em.merge(regiao);
			for (Viagem viagemCollectionOldViagem : viagemCollectionOld) {
				if (!viagemCollectionNew.contains(viagemCollectionOldViagem)) {
					viagemCollectionOldViagem.getRegiaoCollection().remove(regiao);
					viagemCollectionOldViagem = em.merge(viagemCollectionOldViagem);
				}
			}
			for (Viagem viagemCollectionNewViagem : viagemCollectionNew) {
				if (!viagemCollectionOld.contains(viagemCollectionNewViagem)) {
					viagemCollectionNewViagem.getRegiaoCollection().add(regiao);
					viagemCollectionNewViagem = em.merge(viagemCollectionNewViagem);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = regiao.getId();
				if (findRegiao(id) == null) {
					throw new NonexistentEntityException("The regiao with id " + id + " no longer exists.");
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
			Regiao regiao;
			try {
				regiao = em.getReference(Regiao.class, id);
				regiao.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The regiao with id " + id + " no longer exists.", enfe);
			}
			Collection<Viagem> viagemCollection = regiao.getViagemCollection();
			for (Viagem viagemCollectionViagem : viagemCollection) {
				viagemCollectionViagem.getRegiaoCollection().remove(regiao);
				viagemCollectionViagem = em.merge(viagemCollectionViagem);
			}
			em.remove(regiao);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Regiao> findRegiaoEntities() {
		return findRegiaoEntities(true, -1, -1);
	}

	public List<Regiao> findRegiaoEntities(int maxResults, int firstResult) {
		return findRegiaoEntities(false, maxResults, firstResult);
	}

	private List<Regiao> findRegiaoEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Regiao.class));
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

	public Regiao findRegiao(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Regiao.class, id);
		} finally {
			em.close();
		}
	}

	public int getRegiaoCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Regiao> rt = cq.from(Regiao.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
