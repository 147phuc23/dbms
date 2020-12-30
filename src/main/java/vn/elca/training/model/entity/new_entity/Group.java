package vn.elca.training.model.entity.new_entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "GroupTable")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Version", nullable = false)
    @Version
    private Integer version;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GroupLeaderId", nullable = false, foreignKey = @ForeignKey(name = "fk_group_leader"))
    private Employee employee;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private Set<Project> projects;

    public Group() {

    }

    public Group(Long id, Integer version, Employee employee, Set<Project> projects) {
        this.id = id;
        this.version = version;
        this.employee = employee;
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

}