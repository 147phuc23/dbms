package vn.elca.training.model.entity.new_entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "ProjectNumber", nullable = false, unique = true)
    private Short projectNumber;

    @Column(name = "ProjectName", nullable = false, length = 50)
    private String name;

    @Column(name = "Customer", nullable = false, length = 50)
    private String customer;

    @Column(name = "Status", nullable = false, length = 3)
    private String status;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Column(name = "Version", nullable = false)
    @Version
    private Integer version;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ProjectEmployee",
            joinColumns = { @JoinColumn(name = "ProjectId", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "EmployeeId", nullable = false, updatable = false) } )
    private Set<Employee> employees;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GroupId", nullable = false, foreignKey = @ForeignKey(name = "fk_project_group"))
    private Group group;

    public Project() {

    }

    public Project(Long id, Short projectNumber, String name, String customer, String status, LocalDate startDate,
                   LocalDate endDate, Integer version, Set<Employee> employees, Group group)
    {
        this.id = id;
        this.projectNumber = projectNumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.version = version;
        this.employees = employees;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public Short getProjectNumber() {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getVersion() {
        return version;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Group getGroup() {
        return group;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProjectNumber(Short projectNumber) {
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

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectNumber=" + projectNumber +
                ", name='" + name + '\'' +
                ", customer='" + customer + '\'' +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", version=" + version +
                ", employees=" + employees +
                ", group=" + group +
                '}';
    }

}