package vn.elca.training.model.entity.new_entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Visa", nullable = false, unique = true, length = 3)
    private String visa;

    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "BirthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "Version", nullable = false)
    @Version
    private Integer version;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "employee")
    private Group group;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "employees")
    private Set<Project> projects;

    public Employee() {

    }

    public Employee(Long id, String visa, String firstName, String lastName, LocalDate birthDate, Integer version,
                    Group group, Set<Project> projects)
    {
        this.id = id;
        this.visa = visa;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.version = version;
        this.group = group;
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public String getVisa() {
        return visa;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getVersion() {
        return version;
    }

    public Group getGroup() {
        return group;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVisa(String visa) {
        this.visa = visa;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

}