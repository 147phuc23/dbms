package vn.elca.training.service;

import java.util.List;

import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.new_entity.Project;

public interface ProjectService {
    List<Project> findProjectsByPaging(int page, int size);
    List<Project> findProjectsWithFilters(String textFilter, String selectionFilter, int page, int size);
    Project findProjectByProjectNumber(Short projectNumber);
    void saveProject(ProjectDto projectDto);
    void updateProject(ProjectDto projectDto);
    void deleteProjectByProjectId(Long projectId);
    void multiDeleteProjectByIds(String[] projectIds);
}
