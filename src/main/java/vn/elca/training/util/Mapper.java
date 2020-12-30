package vn.elca.training.util;

import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.new_entity.Employee;
import vn.elca.training.model.entity.new_entity.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 *
 */
public class Mapper {

    public Mapper() {
        // Mapper utility class
    }

    public static ProjectDto projectToProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();

        List<String> employees = entity.getEmployees().stream().map(Employee::getVisa).collect(Collectors.toList());
        String visas = String.join(",", employees);

        dto.setMembers(visas);
        dto.setId(entity.getId());
        dto.setProjectNumber(entity.getProjectNumber().toString());
        dto.setName(entity.getName());
        dto.setCustomer(entity.getCustomer());
        dto.setStatus(entity.getStatus());
        dto.setStartDate(entity.getStartDate().toString());
        if(entity.getEndDate() == null){
            dto.setEndDate("");
        } else{
            dto.setEndDate(entity.getEndDate().toString());
        }
        dto.setGroup(entity.getGroup().getId().toString());

        return dto;
    }

    public static List<ProjectDto> projectsToProjectDtoList(List<Project> projects){
        List<ProjectDto> projectDtoList = new ArrayList<>();

        for(Project project : projects){
            projectDtoList.add(Mapper.projectToProjectDto(project));
        }

        return projectDtoList;
    }

}
