package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.new_entity.Employee;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.service.EmployeeService;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(@Autowired EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<String> findEmployeeVisas() {
        return employeeRepository.findEmployeeVisas();
    }

}
