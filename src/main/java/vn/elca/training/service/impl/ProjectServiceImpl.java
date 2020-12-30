package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.new_entity.Employee;
import vn.elca.training.model.entity.new_entity.Group;
import vn.elca.training.model.entity.new_entity.Project;
import vn.elca.training.model.exception.ConcurrentUpdateException;
import vn.elca.training.model.exception.IllegalUserFormException;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.exception.VisasDoesNotExistsException;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.FormUtils;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final GroupRepository groupRepository;
    private final EmployeeRepository employeeRepository;

    private static final int SAVE_OP = 0;
    private static final int UPDATE_OP = 1;

    public ProjectServiceImpl(
            @Autowired ProjectRepository projectRepository,
            @Autowired GroupRepository groupRepository,
            @Autowired EmployeeRepository employeeRepository
    ){
        this.projectRepository = projectRepository;
        this.groupRepository = groupRepository;
        this.employeeRepository = employeeRepository;
    }

    public void operate(ProjectDto projectDto, int op) {

        short projectNumber;
        Group group;

        if(projectDto.getProjectNumber().isEmpty() || projectDto.getName().isEmpty()
                || projectDto.getCustomer().isEmpty() || projectDto.getGroup().isEmpty()
                || projectDto.getStatus().isEmpty() || projectDto.getStartDate().isEmpty())
        {
            throw new IllegalUserFormException("Some Required Fields Are Empty");
        }

        if(!FormUtils.isShort(projectDto.getProjectNumber())){
            throw new IllegalUserFormException("Project Number Is Not Parsable");
        } else {
            projectNumber = Short.parseShort(projectDto.getProjectNumber());
            Project project = projectRepository.findProjectByProjectNumber(projectNumber);
            if(op == SAVE_OP){
                if(project != null){
                    throw new ProjectNumberAlreadyExistsException("Project Number Already Exists");
                }
            }
            if(op == UPDATE_OP){
                if(project == null){
                    throw new IllegalUserFormException("Project Not Found");
                }
                if(project.getVersion() != Integer.parseInt(projectDto.getVersion())){
                    throw new ConcurrentUpdateException("This Project Is Concurrently Updated");
                }
            }
        }

        if(!(projectDto.getStatus().equals("New") || projectDto.getStatus().equals("Pla")
                || projectDto.getStatus().equals("Inp") || projectDto.getStatus().equals("Fin")))
        {
            throw new IllegalUserFormException("Illegal Project Status");
        }

        if(!projectDto.getGroup().equals("New")){
            long groupId;
            try {
                groupId = Long.parseLong(projectDto.getGroup());
            } catch (NumberFormatException e){
                throw new IllegalUserFormException("Group Id Is Not Parsable");
            }

            group = groupRepository.findOne(groupId);
            if(group == null){
                throw new IllegalUserFormException("Group Id Not Found");
            }
        } else {
            // unknown current user -> lack of information
            throw new IllegalUserFormException("New Group Is Not Supported");
        }

        LocalDate startDate = FormUtils.formatLocalDate(projectDto.getStartDate());
        LocalDate endDate = null;
        if(!projectDto.getEndDate().isEmpty()){
            endDate = FormUtils.formatLocalDate(projectDto.getEndDate());
            if(!endDate.isAfter(startDate)){
                throw new IllegalUserFormException("Start Date Is Not Before End Date");
            }
        }

        Set<Employee> employees = new HashSet<>();
        List<String> remainingVisas = new ArrayList<>();

        if(!projectDto.getMembers().isEmpty()){
            List<String> employeeVisas = new LinkedList<>(Arrays.asList(projectDto.getMembers().split(",")));
            for (String visa : employeeVisas){
                Employee employee = employeeRepository.findEmployeeByEmployeeVisa(visa);
                if(employee != null){
                    employees.add(employee);
                } else {
                    remainingVisas.add(visa);
                }
            }
        }

        if(remainingVisas.size() != 0){
            throw new VisasDoesNotExistsException("Some Visas Does Not Exists", remainingVisas);
        }

        if(op == SAVE_OP){
            Project project = new Project(projectDto.getId(), projectNumber, projectDto.getName(),
                    projectDto.getCustomer(), projectDto.getStatus(), startDate,
                    endDate, null, employees, group);
            projectRepository.save(project);
        } else if(op == UPDATE_OP){
            Project project = projectRepository.findProjectByProjectNumber(projectNumber);
            project.setName(projectDto.getName());
            project.setCustomer(projectDto.getCustomer());
            project.setGroup(group);
            project.setEmployees(employees);
            project.setStatus(projectDto.getStatus());
            project.setStartDate(startDate);
            project.setEndDate(endDate);
            projectRepository.save(project);
        } else {
            throw new RuntimeException("Not Supported Operation");
        }

    }

    @Override
    public List<Project> findProjectsByPaging(int page, int size) {
        return projectRepository.findAll(new PageRequest(page, size, new Sort(Sort.Direction.ASC, "projectNumber"))).getContent();
    }

    @Override
    public List<Project> findProjectsWithFilters(String textFilter, String selectionFilter, int page, int size) {
        boolean textIsShort = FormUtils.isShort(textFilter);

        if (textFilter.isEmpty() && selectionFilter.isEmpty()){
            return projectRepository.findAll(new PageRequest(page, size, new Sort(Sort.Direction.ASC, "projectNumber"))).getContent();
        } else if (textFilter.isEmpty()){
            return projectRepository.findProjectsByProjectStatus(selectionFilter, page, size);
        } else if (selectionFilter.isEmpty()){
            if (textIsShort){
                return projectRepository.findProjectsByNameCustomerNumber(textFilter, Short.parseShort(textFilter), page, size);
            } else {
                return projectRepository.findProjectsByNameCustomer(textFilter, page, size);
            }
        } else {
            if (textIsShort){
                return projectRepository.findProjectsByNameCustomerStatusNumber(textFilter, selectionFilter, Short.parseShort(textFilter), page, size);
            } else {
                return projectRepository.findProjectsByNameCustomerStatus(textFilter, selectionFilter, page, size);
            }
        }
    }

    @Override
    public Project findProjectByProjectNumber(Short projectNumber) {
        return projectRepository.findProjectByProjectNumber(projectNumber);
    }

    @Override
    public void saveProject(ProjectDto projectDto) {
        operate(projectDto, SAVE_OP);
    }

    @Override
    public void updateProject(ProjectDto projectDto) {
        operate(projectDto, UPDATE_OP);
    }

    @Override
    public void deleteProjectByProjectId(Long projectId) {
        projectRepository.deleteProjectByProjectId(projectId);
    }

    @Override
    public void multiDeleteProjectByIds(String[] projectIds) {
        List<Long> ids = new ArrayList<>();
        for(String projectId : projectIds){
            ids.add(Long.parseLong(projectId));
        }
        projectRepository.multiDeleteProjectsByIds(ids);
    }

}
