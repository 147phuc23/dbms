package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import vn.elca.training.model.entity.new_entity.Employee;
import vn.elca.training.repository.custom.EmployeeRepositoryCustom;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, QueryDslPredicateExecutor<Employee>, EmployeeRepositoryCustom {

}
