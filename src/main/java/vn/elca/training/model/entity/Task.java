package vn.elca.training.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import vn.elca.training.model.exception.DeadlineGreaterThanProjectFinishingDateException;
import vn.elca.training.model.validator.TaskDeadlineValid;

/**
 * @author vlp
 *
 */
//@Entity
// @TaskDeadlineValid
public class Task implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Project project;

    public Task(Project project, String name) {
        this.project = project;
        this.name = name;
        this.deadline = LocalDate.now();
    }

    public Task() {}

    public void validateDeadline() throws DeadlineGreaterThanProjectFinishingDateException {
        if (project.getFinishingDate().compareTo(deadline) < 0) {
            throw new DeadlineGreaterThanProjectFinishingDateException();
        }
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
