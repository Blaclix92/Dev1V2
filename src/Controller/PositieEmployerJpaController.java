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
import Model.Employee;
import Model.PositieDescription;
import Model.Project;
import Model.HeadquarterInfo;
import Model.PositieEmployer;
import Model.PositieEmployerPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Donovan
 */
public class PositieEmployerJpaController implements Serializable {

    public PositieEmployerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PositieEmployer positieEmployer) throws PreexistingEntityException, Exception {
        if (positieEmployer.getPositieEmployerPK() == null) {
            positieEmployer.setPositieEmployerPK(new PositieEmployerPK());
        }
        positieEmployer.getPositieEmployerPK().setBsn(positieEmployer.getEmployee().getBsn());
        positieEmployer.getPositieEmployerPK().setPositieid(positieEmployer.getPositieDescription().getPositieid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee = positieEmployer.getEmployee();
            if (employee != null) {
                employee = em.getReference(employee.getClass(), employee.getBsn());
                positieEmployer.setEmployee(employee);
            }
            PositieDescription positieDescription = positieEmployer.getPositieDescription();
            if (positieDescription != null) {
                positieDescription = em.getReference(positieDescription.getClass(), positieDescription.getPositieid());
                positieEmployer.setPositieDescription(positieDescription);
            }
            Project projectid = positieEmployer.getProjectid();
            if (projectid != null) {
                projectid = em.getReference(projectid.getClass(), projectid.getProjectid());
                positieEmployer.setProjectid(projectid);
            }
            HeadquarterInfo headquarterid = positieEmployer.getHeadquarterid();
            if (headquarterid != null) {
                headquarterid = em.getReference(headquarterid.getClass(), headquarterid.getHeadquarterid());
                positieEmployer.setHeadquarterid(headquarterid);
            }
            em.persist(positieEmployer);
            if (employee != null) {
                employee.getPositieEmployerCollection().add(positieEmployer);
                employee = em.merge(employee);
            }
            if (positieDescription != null) {
                positieDescription.getPositieEmployerCollection().add(positieEmployer);
                positieDescription = em.merge(positieDescription);
            }
            if (projectid != null) {
                projectid.getPositieEmployerCollection().add(positieEmployer);
                projectid = em.merge(projectid);
            }
            if (headquarterid != null) {
                headquarterid.getPositieEmployerCollection().add(positieEmployer);
                headquarterid = em.merge(headquarterid);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPositieEmployer(positieEmployer.getPositieEmployerPK()) != null) {
                throw new PreexistingEntityException("PositieEmployer " + positieEmployer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PositieEmployer positieEmployer) throws NonexistentEntityException, Exception {
        positieEmployer.getPositieEmployerPK().setBsn(positieEmployer.getEmployee().getBsn());
        positieEmployer.getPositieEmployerPK().setPositieid(positieEmployer.getPositieDescription().getPositieid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PositieEmployer persistentPositieEmployer = em.find(PositieEmployer.class, positieEmployer.getPositieEmployerPK());
            Employee employeeOld = persistentPositieEmployer.getEmployee();
            Employee employeeNew = positieEmployer.getEmployee();
            PositieDescription positieDescriptionOld = persistentPositieEmployer.getPositieDescription();
            PositieDescription positieDescriptionNew = positieEmployer.getPositieDescription();
            Project projectidOld = persistentPositieEmployer.getProjectid();
            Project projectidNew = positieEmployer.getProjectid();
            HeadquarterInfo headquarteridOld = persistentPositieEmployer.getHeadquarterid();
            HeadquarterInfo headquarteridNew = positieEmployer.getHeadquarterid();
            if (employeeNew != null) {
                employeeNew = em.getReference(employeeNew.getClass(), employeeNew.getBsn());
                positieEmployer.setEmployee(employeeNew);
            }
            if (positieDescriptionNew != null) {
                positieDescriptionNew = em.getReference(positieDescriptionNew.getClass(), positieDescriptionNew.getPositieid());
                positieEmployer.setPositieDescription(positieDescriptionNew);
            }
            if (projectidNew != null) {
                projectidNew = em.getReference(projectidNew.getClass(), projectidNew.getProjectid());
                positieEmployer.setProjectid(projectidNew);
            }
            if (headquarteridNew != null) {
                headquarteridNew = em.getReference(headquarteridNew.getClass(), headquarteridNew.getHeadquarterid());
                positieEmployer.setHeadquarterid(headquarteridNew);
            }
            positieEmployer = em.merge(positieEmployer);
            if (employeeOld != null && !employeeOld.equals(employeeNew)) {
                employeeOld.getPositieEmployerCollection().remove(positieEmployer);
                employeeOld = em.merge(employeeOld);
            }
            if (employeeNew != null && !employeeNew.equals(employeeOld)) {
                employeeNew.getPositieEmployerCollection().add(positieEmployer);
                employeeNew = em.merge(employeeNew);
            }
            if (positieDescriptionOld != null && !positieDescriptionOld.equals(positieDescriptionNew)) {
                positieDescriptionOld.getPositieEmployerCollection().remove(positieEmployer);
                positieDescriptionOld = em.merge(positieDescriptionOld);
            }
            if (positieDescriptionNew != null && !positieDescriptionNew.equals(positieDescriptionOld)) {
                positieDescriptionNew.getPositieEmployerCollection().add(positieEmployer);
                positieDescriptionNew = em.merge(positieDescriptionNew);
            }
            if (projectidOld != null && !projectidOld.equals(projectidNew)) {
                projectidOld.getPositieEmployerCollection().remove(positieEmployer);
                projectidOld = em.merge(projectidOld);
            }
            if (projectidNew != null && !projectidNew.equals(projectidOld)) {
                projectidNew.getPositieEmployerCollection().add(positieEmployer);
                projectidNew = em.merge(projectidNew);
            }
            if (headquarteridOld != null && !headquarteridOld.equals(headquarteridNew)) {
                headquarteridOld.getPositieEmployerCollection().remove(positieEmployer);
                headquarteridOld = em.merge(headquarteridOld);
            }
            if (headquarteridNew != null && !headquarteridNew.equals(headquarteridOld)) {
                headquarteridNew.getPositieEmployerCollection().add(positieEmployer);
                headquarteridNew = em.merge(headquarteridNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PositieEmployerPK id = positieEmployer.getPositieEmployerPK();
                if (findPositieEmployer(id) == null) {
                    throw new NonexistentEntityException("The positieEmployer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PositieEmployerPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PositieEmployer positieEmployer;
            try {
                positieEmployer = em.getReference(PositieEmployer.class, id);
                positieEmployer.getPositieEmployerPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The positieEmployer with id " + id + " no longer exists.", enfe);
            }
            Employee employee = positieEmployer.getEmployee();
            if (employee != null) {
                employee.getPositieEmployerCollection().remove(positieEmployer);
                employee = em.merge(employee);
            }
            PositieDescription positieDescription = positieEmployer.getPositieDescription();
            if (positieDescription != null) {
                positieDescription.getPositieEmployerCollection().remove(positieEmployer);
                positieDescription = em.merge(positieDescription);
            }
            Project projectid = positieEmployer.getProjectid();
            if (projectid != null) {
                projectid.getPositieEmployerCollection().remove(positieEmployer);
                projectid = em.merge(projectid);
            }
            HeadquarterInfo headquarterid = positieEmployer.getHeadquarterid();
            if (headquarterid != null) {
                headquarterid.getPositieEmployerCollection().remove(positieEmployer);
                headquarterid = em.merge(headquarterid);
            }
            em.remove(positieEmployer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PositieEmployer> findPositieEmployerEntities() {
        return findPositieEmployerEntities(true, -1, -1);
    }

    public List<PositieEmployer> findPositieEmployerEntities(int maxResults, int firstResult) {
        return findPositieEmployerEntities(false, maxResults, firstResult);
    }

    private List<PositieEmployer> findPositieEmployerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PositieEmployer.class));
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

    public PositieEmployer findPositieEmployer(PositieEmployerPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PositieEmployer.class, id);
        } finally {
            em.close();
        }
    }

    public int getPositieEmployerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PositieEmployer> rt = cq.from(PositieEmployer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
