package vn.elca.training.repository.custom;

import vn.elca.training.model.entity.new_entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {

    List<Project> findProjectsByProjectStatus(String status, int page, int size);
    Project findProjectByProjectNumber(Short projectNumber);

    List<Project> findProjectsByNameCustomer(String textFilter, int page, int size);
    List<Project> findProjectsByNameCustomerNumber(String textFilter, Short projectNumber, int page, int size);
    List<Project> findProjectsByNameCustomerStatus(String textFilter, String selectionFilter, int page, int size);
    List<Project> findProjectsByNameCustomerStatusNumber(String textFilter, String selectionFilter, Short projectNumber, int page, int size);

    void deleteProjectByProjectId(Long projectId);
    void multiDeleteProjectsByIds(List<Long> projectIds);

}
