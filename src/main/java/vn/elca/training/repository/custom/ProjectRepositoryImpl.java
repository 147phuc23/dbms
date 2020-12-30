package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import vn.elca.training.model.entity.new_entity.Project;
import vn.elca.training.model.entity.new_entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Project findProjectByProjectNumber(Short projectNumber) {
        JPAQuery<Project> query = new JPAQuery<>(entityManager);

        QProject project = QProject.project;

        return query.from(project)
                .where(project.projectNumber.eq(projectNumber))
                .fetchOne();
    }

    @Override
    public List<Project> findProjectsByProjectStatus(String status, int page, int size) {
//        JPAQuery<Project> query = new JPAQuery<>(entityManager);
//
//        QProject project = QProject.project;
//
//        return query.from(project)
//                .where(project.status.eq(status))
//                .orderBy(project.projectNumber.asc())
//                .fetch();
        Query query = entityManager.createQuery("SELECT p FROM project AS p WHERE p.status=:status" +
                " ORDER BY p.projectNumber");
        query.setParameter("status", status);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<Project> findProjectsByNameCustomer(String textFilter, int page, int size) {
//        JPAQuery<Project> query = new JPAQuery<>(entityManager);
//
//        QProject project = QProject.project;
//
//        return query.from(project)
//                .where(project.name.containsIgnoreCase(textFilter)
//                        .or(project.customer.containsIgnoreCase(textFilter)))
//                .orderBy(project.projectNumber.asc())
//                .fetch();
        Query query = entityManager.createQuery("SELECT p FROM project AS p" +
                " WHERE p.name LIKE '%" + textFilter + "%' OR p.customer LIKE '%" + textFilter + "%' ORDER BY p.projectNumber");
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<Project> findProjectsByNameCustomerNumber(String textFilter, Short projectNumber, int page, int size) {
//        JPAQuery<Project> query = new JPAQuery<>(entityManager);
//
//        QProject project = QProject.project;
//
//        return query.from(project)
//                .where(project.name.containsIgnoreCase(textFilter)
//                        .or(project.customer.containsIgnoreCase(textFilter))
//                        .or(project.projectNumber.eq(projectNumber)))
//                .orderBy(project.projectNumber.asc())
//                .fetch();
        Query query = entityManager.createQuery("SELECT p FROM project AS p" +
                " WHERE p.name LIKE '%" + textFilter + "%' OR p.customer LIKE '%" + textFilter + "%' OR p.projectNumber=:projectNumber" +
                " ORDER BY p.projectNumber");
        query.setParameter("projectNumber", projectNumber);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<Project> findProjectsByNameCustomerStatus(String textFilter, String selectionFilter, int page, int size) {
//        JPAQuery<Project> query = new JPAQuery<>(entityManager);
//
//        QProject project = QProject.project;
//
//        return query.from(project)
//                .where(project.name.containsIgnoreCase(textFilter)
//                        .or(project.customer.containsIgnoreCase(textFilter))
//                        .or(project.status.eq(selectionFilter)))
//                .orderBy(project.projectNumber.asc())
//                .fetch();
        Query query = entityManager.createQuery("SELECT p FROM project AS p" +
                " WHERE p.name LIKE '%" + textFilter + "%' OR p.customer LIKE '%" + textFilter + "%' OR p.status=:selectionFilter" +
                " ORDER BY p.projectNumber");
        query.setParameter("selectionFilter", selectionFilter);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<Project> findProjectsByNameCustomerStatusNumber(String textFilter, String selectionFilter, Short projectNumber, int page, int size) {
//        JPAQuery<Project> query = new JPAQuery<>(entityManager);
//
//        QProject project = QProject.project;
//
//        return query.from(project)
//                .where(project.name.containsIgnoreCase(textFilter)
//                        .or(project.customer.containsIgnoreCase(textFilter))
//                        .or(project.status.eq(selectionFilter))
//                        .or(project.projectNumber.eq(projectNumber)))
//                .orderBy(project.projectNumber.asc())
//                .fetch();
        Query query = entityManager.createQuery("SELECT p FROM project AS p" +
                " WHERE p.name LIKE '%" + textFilter + "%' OR p.customer LIKE '%" + textFilter + "%'" +
                " OR p.status=:selectionFilter OR p.projectNumber=:projectNumber ORDER BY p.projectNumber");
        query.setParameter("selectionFilter", selectionFilter);
        query.setParameter("projectNumber", projectNumber);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public void deleteProjectByProjectId(Long projectId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QProject project = QProject.project;

        queryFactory.delete(project).where(project.id.eq(projectId)).execute();

    }

    @Override
    public void multiDeleteProjectsByIds(List<Long> projectIds) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QProject project = QProject.project;

        for (Long projectId : projectIds){
            queryFactory.delete(project).where(project.id.eq(projectId)).execute();
        }

    }

}
