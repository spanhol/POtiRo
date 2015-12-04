/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Caminhao;
import entidades.Motorista;
import entidades.Regiao;
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
public class ViagemJpaController implements Serializable {

	public ViagemJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Viagem viagem) {
		if (viagem.getRegiaoCollection() == null) {
			viagem.setRegiaoCollection(new ArrayList<Regiao>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Caminhao idcaminhao = viagem.getIdcaminhao();
			if (idcaminhao != null) {
				idcaminhao = em.getReference(idcaminhao.getClass(), idcaminhao.getId());
				viagem.setIdcaminhao(idcaminhao);
			}
			Motorista idmotorista = viagem.getIdmotorista();
			if (idmotorista != null) {
				idmotorista = em.getReference(idmotorista.getClass(), idmotorista.getId());
				viagem.setIdmotorista(idmotorista);
			}
			Collection<Regiao> attachedRegiaoCollection = new ArrayList<Regiao>();
			for (Regiao regiaoCollectionRegiaoToAttach : viagem.getRegiaoCollection()) {
				regiaoCollectionRegiaoToAttach = em.getReference(regiaoCollectionRegiaoToAttach.getClass(), regiaoCollectionRegiaoToAttach.getId());
				attachedRegiaoCollection.add(regiaoCollectionRegiaoToAttach);
			}
			viagem.setRegiaoCollection(attachedRegiaoCollection);
			em.persist(viagem);
			if (idcaminhao != null) {
				idcaminhao.getViagemCollection().add(viagem);
				idcaminhao = em.merge(idcaminhao);
			}
			if (idmotorista != null) {
				idmotorista.getViagemCollection().add(viagem);
				idmotorista = em.merge(idmotorista);
			}
			for (Regiao regiaoCollectionRegiao : viagem.getRegiaoCollection()) {
				regiaoCollectionRegiao.getViagemCollection().add(viagem);
				regiaoCollectionRegiao = em.merge(regiaoCollectionRegiao);
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Viagem viagem) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Viagem persistentViagem = em.find(Viagem.class, viagem.getId());
			Caminhao idcaminhaoOld = persistentViagem.getIdcaminhao();
			Caminhao idcaminhaoNew = viagem.getIdcaminhao();
			Motorista idmotoristaOld = persistentViagem.getIdmotorista();
			Motorista idmotoristaNew = viagem.getIdmotorista();
			Collection<Regiao> regiaoCollectionOld = persistentViagem.getRegiaoCollection();
			Collection<Regiao> regiaoCollectionNew = viagem.getRegiaoCollection();
			if (idcaminhaoNew != null) {
				idcaminhaoNew = em.getReference(idcaminhaoNew.getClass(), idcaminhaoNew.getId());
				viagem.setIdcaminhao(idcaminhaoNew);
			}
			if (idmotoristaNew != null) {
				idmotoristaNew = em.getReference(idmotoristaNew.getClass(), idmotoristaNew.getId());
				viagem.setIdmotorista(idmotoristaNew);
			}
			Collection<Regiao> attachedRegiaoCollectionNew = new ArrayList<Regiao>();
			for (Regiao regiaoCollectionNewRegiaoToAttach : regiaoCollectionNew) {
				regiaoCollectionNewRegiaoToAttach = em.getReference(regiaoCollectionNewRegiaoToAttach.getClass(), regiaoCollectionNewRegiaoToAttach.getId());
				attachedRegiaoCollectionNew.add(regiaoCollectionNewRegiaoToAttach);
			}
			regiaoCollectionNew = attachedRegiaoCollectionNew;
			viagem.setRegiaoCollection(regiaoCollectionNew);
			viagem = em.merge(viagem);
			if (idcaminhaoOld != null && !idcaminhaoOld.equals(idcaminhaoNew)) {
				idcaminhaoOld.getViagemCollection().remove(viagem);
				idcaminhaoOld = em.merge(idcaminhaoOld);
			}
			if (idcaminhaoNew != null && !idcaminhaoNew.equals(idcaminhaoOld)) {
				idcaminhaoNew.getViagemCollection().add(viagem);
				idcaminhaoNew = em.merge(idcaminhaoNew);
			}
			if (idmotoristaOld != null && !idmotoristaOld.equals(idmotoristaNew)) {
				idmotoristaOld.getViagemCollection().remove(viagem);
				idmotoristaOld = em.merge(idmotoristaOld);
			}
			if (idmotoristaNew != null && !idmotoristaNew.equals(idmotoristaOld)) {
				idmotoristaNew.getViagemCollection().add(viagem);
				idmotoristaNew = em.merge(idmotoristaNew);
			}
			for (Regiao regiaoCollectionOldRegiao : regiaoCollectionOld) {
				if (!regiaoCollectionNew.contains(regiaoCollectionOldRegiao)) {
					regiaoCollectionOldRegiao.getViagemCollection().remove(viagem);
					regiaoCollectionOldRegiao = em.merge(regiaoCollectionOldRegiao);
				}
			}
			for (Regiao regiaoCollectionNewRegiao : regiaoCollectionNew) {
				if (!regiaoCollectionOld.contains(regiaoCollectionNewRegiao)) {
					regiaoCollectionNewRegiao.getViagemCollection().add(viagem);
					regiaoCollectionNewRegiao = em.merge(regiaoCollectionNewRegiao);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = viagem.getId();
				if (findViagem(id) == null) {
					throw new NonexistentEntityException("The viagem with id " + id + " no longer exists.");
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
			Viagem viagem;
			try {
				viagem = em.getReference(Viagem.class, id);
				viagem.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The viagem with id " + id + " no longer exists.", enfe);
			}
			Caminhao idcaminhao = viagem.getIdcaminhao();
			if (idcaminhao != null) {
				idcaminhao.getViagemCollection().remove(viagem);
				idcaminhao = em.merge(idcaminhao);
			}
			Motorista idmotorista = viagem.getIdmotorista();
			if (idmotorista != null) {
				idmotorista.getViagemCollection().remove(viagem);
				idmotorista = em.merge(idmotorista);
			}
			Collection<Regiao> regiaoCollection = viagem.getRegiaoCollection();
			for (Regiao regiaoCollectionRegiao : regiaoCollection) {
				regiaoCollectionRegiao.getViagemCollection().remove(viagem);
				regiaoCollectionRegiao = em.merge(regiaoCollectionRegiao);
			}
			em.remove(viagem);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Viagem> findViagemEntities() {
		return findViagemEntities(true, -1, -1);
	}

	public List<Viagem> findViagemEntities(int maxResults, int firstResult) {
		return findViagemEntities(false, maxResults, firstResult);
	}

	private List<Viagem> findViagemEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Viagem.class));
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

	public Viagem findViagem(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Viagem.class, id);
		} finally {
			em.close();
		}
	}

	public int getViagemCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Viagem> rt = cq.from(Viagem.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
