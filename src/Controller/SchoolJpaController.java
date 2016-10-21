/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Degree;
import Model.School;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Benny
 */
public class SchoolJpaController implements Serializable {

    public SchoolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(School school) throws PreexistingEntityException, Exception {
        if (school.getDegreeCollection() == null) {
            school.setDegreeCollection(new ArrayList<Degree>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Degree> attachedDegreeCollection = new ArrayList<Degree>();
            for (Degree degreeCollectionDegreeToAttach : school.getDegreeCollection()) {
                degreeCollectionDegreeToAttach = em.getReference(degreeCollectionDegreeToAttach.getClass(), degreeCollectionDegreeToAttach.getDegreePK());
                attachedDegreeCollection.add(degreeCollectionDegreeToAttach);
            }
            school.setDegreeCollection(attachedDegreeCollection);
            em.persist(school);
            for (Degree degreeCollectionDegree : school.getDegreeCollection()) {
                School oldSchoolOfDegreeCollectionDegree = degreeCollectionDegree.getSchool();
                degreeCollectionDegree.setSchool(school);
                degreeCollectionDegree = em.merge(degreeCollectionDegree);
                if (oldSchoolOfDegreeCollectionDegree != null) {
                    oldSchoolOfDegreeCollectionDegree.getDegreeCollection().remove(degreeCollectionDegree);
                    oldSchoolOfDegreeCollectionDegree = em.merge(oldSchoolOfDegreeCollectionDegree);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSchool(school.getSchoolid()) != null) {
                throw new PreexistingEntityException("School " + school + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(School school) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            School persistentSchool = em.find(School.class, school.getSchoolid());
            Collection<Degree> degreeCollectionOld = persistentSchool.getDegreeCollection();
            Collection<Degree> degreeCollectionNew = school.getDegreeCollection();
            List<String> illegalOrphanMessages = null;
            for (Degree degreeCollectionOldDegree : degreeCollectionOld) {
                if (!degreeCollectionNew.contains(degreeCollectionOldDegree)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Degree " + degreeCollectionOldDegree + " since its school field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Degree> attachedDegreeCollectionNew = new ArrayList<Degree>();
            for (Degree degreeCollectionNewDegreeToAttach : degreeCollectionNew) {
                degreeCollectionNewDegreeToAttach = em.getReference(degreeCollectionNewDegreeToAttach.getClass(), degreeCollectionNewDegreeToAttach.getDegreePK());
                attachedDegreeCollectionNew.add(degreeCollectionNewDegreeToAttach);
            }
            degreeCollectionNew = attachedDegreeCollectionNew;
            school.setDegreeCollection(degreeCollectionNew);
            school = em.merge(school);
            for (Degree degreeCollectionNewDegree : degreeCollectionNew) {
                if (!degreeCollectionOld.contains(degreeCollectionNewDegree)) {
                    School oldSchoolOfDegreeCollectionNewDegree = degreeCollectionNewDegree.getSchool();
                    degreeCollectionNewDegree.setSchool(school);
                    degreeCollectionNewDegree = em.merge(degreeCollectionNewDegree);
                    if (oldSchoolOfDegreeCollectionNewDegree != null && !oldSchoolOfDegreeCollectionNewDegree.equals(school)) {
                        oldSchoolOfDegreeCollectionNewDegree.getDegreeCollection().remove(degreeCollectionNewDegree);
                        oldSchoolOfDegreeCollectionNewDegree = em.merge(oldSchoolOfDegreeCollectionNewDegree);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = school.getSchoolid();
                if (findSchool(id) == null) {
                    throw new NonexistentEntityException("The school with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            School school;
            try {
                school = em.getReference(School.class, id);
                school.getSchoolid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The school with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Degree> degreeCollectionOrphanCheck = school.getDegreeCollection();
            for (Degree degreeCollectionOrphanCheckDegree : degreeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This School (" + school + ") cannot be destroyed since the Degree " + degreeCollectionOrphanCheckDegree + " in its degreeCollection field has a non-nullable school field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(school);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<School> findSchoolEntities() {
        return findSchoolEntities(true, -1, -1);
    }

    public List<School> findSchoolEntities(int maxResults, int firstResult) {
        return findSchoolEntities(false, maxResults, firstResult);
    }

    private List<School> findSchoolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(School.class));
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

    public School findSchool(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(School.class, id);
        } finally {
            em.close();
        }
    }

    public int getSchoolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<School> rt = cq.from(School.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
