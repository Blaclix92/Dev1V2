/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Model.Degree;
import Model.DegreePK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.School;
import Model.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Benny
 */
public class DegreeJpaController implements Serializable {

    public DegreeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Degree degree) throws PreexistingEntityException, Exception {
        if (degree.getDegreePK() == null) {
            degree.setDegreePK(new DegreePK());
        }
        degree.getDegreePK().setBsn(degree.getEmployee().getBsn());
        degree.getDegreePK().setSchoolid(degree.getSchool().getSchoolid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            School school = degree.getSchool();
            if (school != null) {
                school = em.getReference(school.getClass(), school.getSchoolid());
                degree.setSchool(school);
            }
            Employee employee = degree.getEmployee();
            if (employee != null) {
                employee = em.getReference(employee.getClass(), employee.getBsn());
                degree.setEmployee(employee);
            }
            em.persist(degree);
            if (school != null) {
                school.getDegreeCollection().add(degree);
                school = em.merge(school);
            }
            if (employee != null) {
                employee.getDegreeCollection().add(degree);
                employee = em.merge(employee);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDegree(degree.getDegreePK()) != null) {
                throw new PreexistingEntityException("Degree " + degree + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Degree degree) throws NonexistentEntityException, Exception {
        degree.getDegreePK().setBsn(degree.getEmployee().getBsn());
        degree.getDegreePK().setSchoolid(degree.getSchool().getSchoolid());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Degree persistentDegree = em.find(Degree.class, degree.getDegreePK());
            School schoolOld = persistentDegree.getSchool();
            School schoolNew = degree.getSchool();
            Employee employeeOld = persistentDegree.getEmployee();
            Employee employeeNew = degree.getEmployee();
            if (schoolNew != null) {
                schoolNew = em.getReference(schoolNew.getClass(), schoolNew.getSchoolid());
                degree.setSchool(schoolNew);
            }
            if (employeeNew != null) {
                employeeNew = em.getReference(employeeNew.getClass(), employeeNew.getBsn());
                degree.setEmployee(employeeNew);
            }
            degree = em.merge(degree);
            if (schoolOld != null && !schoolOld.equals(schoolNew)) {
                schoolOld.getDegreeCollection().remove(degree);
                schoolOld = em.merge(schoolOld);
            }
            if (schoolNew != null && !schoolNew.equals(schoolOld)) {
                schoolNew.getDegreeCollection().add(degree);
                schoolNew = em.merge(schoolNew);
            }
            if (employeeOld != null && !employeeOld.equals(employeeNew)) {
                employeeOld.getDegreeCollection().remove(degree);
                employeeOld = em.merge(employeeOld);
            }
            if (employeeNew != null && !employeeNew.equals(employeeOld)) {
                employeeNew.getDegreeCollection().add(degree);
                employeeNew = em.merge(employeeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DegreePK id = degree.getDegreePK();
                if (findDegree(id) == null) {
                    throw new NonexistentEntityException("The degree with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DegreePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Degree degree;
            try {
                degree = em.getReference(Degree.class, id);
                degree.getDegreePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The degree with id " + id + " no longer exists.", enfe);
            }
            School school = degree.getSchool();
            if (school != null) {
                school.getDegreeCollection().remove(degree);
                school = em.merge(school);
            }
            Employee employee = degree.getEmployee();
            if (employee != null) {
                employee.getDegreeCollection().remove(degree);
                employee = em.merge(employee);
            }
            em.remove(degree);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Degree> findDegreeEntities() {
        return findDegreeEntities(true, -1, -1);
    }

    public List<Degree> findDegreeEntities(int maxResults, int firstResult) {
        return findDegreeEntities(false, maxResults, firstResult);
    }

    private List<Degree> findDegreeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Degree.class));
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

    public Degree findDegree(DegreePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Degree.class, id);
        } finally {
            em.close();
        }
    }

    public int getDegreeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Degree> rt = cq.from(Degree.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
