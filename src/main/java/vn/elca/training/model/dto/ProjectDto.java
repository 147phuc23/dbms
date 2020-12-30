package vn.elca.training.model.dto;

/**
 * @author gtn
 *
 */
public class ProjectDto {
    private Long id;
    private String projectNumber;
    private String name;
    private String customer;
    private String status;
    private String startDate;
    private String endDate;
    private String members;
    private String group;
    private String version;

    public ProjectDto() {

    }

    public ProjectDto(Long id, String projectNumber, String name, String customer, String status,
                      String startDate, String endDate, String members, String group, String version)
    {
        this.id = id;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.members = members;
        this.group = group;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public String getName() {
        return name;
    }

    public String getCustomer() {
        return customer;
    }

    public String getStatus() {
        return status;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getMembers() {
        return members;
    }

    public String getGroup() {
        return group;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", projectNumber='" + projectNumber + '\'' +
                ", name='" + name + '\'' +
                ", customer='" + customer + '\'' +
                ", status='" + status + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", members='" + members + '\'' +
                ", group='" + group + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

}
