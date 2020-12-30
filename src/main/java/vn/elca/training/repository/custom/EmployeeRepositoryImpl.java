package vn.elca.training.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import vn.elca.training.model.entity.new_entity.Employee;
import vn.elca.training.model.entity.new_entity.QEmployee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Employee> findEmployeesByEmployeeVisas(List<String> visaStrings) {
        JPAQuery<Employee> query = new JPAQuery<>(entityManager);

        QEmployee employee = QEmployee.employee;

        List<Employee> employees = query.from(employee)
                .where(employee.visa.in(visaStrings))
                .fetch();

        return new HashSet<>(employees);
    }

    @Override
    public Employee findEmployeeByEmployeeVisa(String visa) {
        JPAQuery<Employee> query = new JPAQuery<>(entityManager);

        QEmployee employee = QEmployee.employee;

        return query.from(employee)
                .where(employee.visa.eq(visa))
                .fetchOne();
    }

    @Override
    public List<String> findEmployeeVisas() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QEmployee employee = QEmployee.employee;

        return queryFactory.select(employee.visa).from(employee)
                .fetch();
    }

}
