package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import vn.elca.training.model.entity.new_entity.Project;
import vn.elca.training.repository.custom.ProjectRepositoryCustom;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, QueryDslPredicateExecutor<Project>, ProjectRepositoryCustom {

}
