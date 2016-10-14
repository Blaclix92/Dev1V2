/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.HeadquarterInfo;
import Model.Project;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Donovan
 */
public class ProjectJpaController implements Serializable {

    public ProjectJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Project project) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HeadquarterInfo headquarterid = project.getHeadquarterid();
            if (headquarterid != null) {
                headquarterid = em.getReference(headquarterid.getClass(), headquarterid.getHeadquarterid());
                project.setHeadquarterid(headquarterid);
            }
            em.persist(project);
            if (headquarterid != null) {
                headquarterid.getProjectCollection().add(project);
                headquarterid = em.merge(headquarterid);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProject(project.getProjectid()) != null) {
                throw new PreexistingEntityException("Project " + project + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Project project) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project persistentProject = em.find(Project.class, project.getProjectid());
            HeadquarterInfo headquarteridOld = persistentProject.getHeadquarterid();
            HeadquarterInfo headquarteridNew = project.getHeadquarterid();
            if (headquarteridNew != null) {
                headquarteridNew = em.getReference(headquarteridNew.getClass(), headquarteridNew.getHeadquarterid());
                project.setHeadquarterid(headquarteridNew);
            }
            project = em.merge(project);
            if (headquarteridOld != null && !headquarteridOld.equals(headquarteridNew)) {
                headquarteridOld.getProjectCollection().remove(project);
                headquarteridOld = em.merge(headquarteridOld);
            }
            if (headquarteridNew != null && !headquarteridNew.equals(headquarteridOld)) {
                headquarteridNew.getProjectCollection().add(project);
                headquarteridNew = em.merge(headquarteridNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = project.getProjectid();
                if (findProject(id) == null) {
                    throw new NonexistentEntityException("The project with id " + id + " no longer exists.");
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
            Project project;
            try {
                project = em.getReference(Project.class, id);
                project.getProjectid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The project with id " + id + " no longer exists.", enfe);
            }
            HeadquarterInfo headquarterid = project.getHeadquarterid();
            if (headquarterid != null) {
                headquarterid.getProjectCollection().remove(project);
                headquarterid = em.merge(headquarterid);
            }
            em.remove(project);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Project> findProjectEntities() {
        return findProjectEntities(true, -1, -1);
    }

    public List<Project> findProjectEntities(int maxResults, int firstResult) {
        return findProjectEntities(false, maxResults, firstResult);
    }

    private List<Project> findProjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Project.class));
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

    public Project findProject(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Project> rt = cq.from(Project.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
