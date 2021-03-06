package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.elca.training.model.entity.Project;
//import vn.elca.training.model.entity.QProject;
//import vn.elca.training.model.entity.QTask;
import vn.elca.training.model.entity.Task;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author gtn
 *
 */
public class TaskRepositoryImpl { // implements TaskRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

//    @Override
//    public List<Project> findProjectsByTaskName(String taskName) {
//        return new JPAQuery<Project>(em)
//                .from(QProject.project)
//                .innerJoin(QProject.project.tasks, QTask.task).fetchJoin()
//                .where(QTask.task.name.eq(taskName))
//                .fetch();
//    }
//
//    @Override
//    public List<Task> showProjectNameOfTopTenNewTasks() {
//        return new JPAQuery<Task>(em)
//                .from(QTask.task)
//                .orderBy(QTask.task.id.desc())
//                .limit(10)
//                .fetch();
//    }
}
