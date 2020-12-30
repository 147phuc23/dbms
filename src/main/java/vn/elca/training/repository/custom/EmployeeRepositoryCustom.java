package vn.elca.training.repository.custom;

import vn.elca.training.model.entity.new_entity.Employee;

import java.util.List;
import java.util.Set;

public interface EmployeeRepositoryCustom {
    Set<Employee> findEmployeesByEmployeeVisas(List<String> visaStrings);
    Employee findEmployeeByEmployeeVisa(String visa);
    List<String> findEmployeeVisas();
}
