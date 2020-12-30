package vn.elca.training.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.new_entity.Employee;
import vn.elca.training.model.entity.new_entity.Project;
import vn.elca.training.service.EmployeeService;
import vn.elca.training.service.GroupService;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.Mapper;

/**
 * @author vlp
 *
 */
@Controller
public class ApplicationController extends ResponseEntityExceptionHandler {

    private final ProjectService projectService;
    private final GroupService groupService;
    private final EmployeeService employeeService;

    private static final int PAGE_SIZE = 5;

    public ApplicationController(
            @Autowired ProjectService projectService,
            @Autowired GroupService groupService,
            @Autowired EmployeeService employeeService)
    {
        this.projectService = projectService;
        this.groupService = groupService;
        this.employeeService = employeeService;
    }

    /**
     * Open or Reload application
     */
    @RequestMapping("/")
    public String getListView(){
        return "list";
    }

    /**
     * Get projects
     */
    @RequestMapping("/projects")
    @ResponseBody
    public ResponseEntity<List<ProjectDto>> getAllProjects(@RequestParam("page") int page){
        List<Project> projects = projectService.findProjectsByPaging(page, PAGE_SIZE);
        List<ProjectDto> projectDtoList = Mapper.projectsToProjectDtoList(projects);
        return new ResponseEntity<>(projectDtoList, HttpStatus.OK);
    }

    /**
     * Swap to form fragment
     */
    @RequestMapping("/fragment/form")
    public String getFormFragment(Model model) {
        List<Long> groupIds = groupService.findGroupIds();
        List<String> emVisas = employeeService.findEmployeeVisas();
        model.addAttribute("groupIds", groupIds);
        model.addAttribute("emVisas", emVisas);
        model.addAttribute("project", new Project());
        model.addAttribute("employees", new ArrayList<String>());
        return "form :: form-fragment";
    }

    /**
     * Swap to list fragment
     */
    @RequestMapping("/fragment/list")
    public String getListFragment() {
        return "list :: list-fragment";
    }

    /**
     * Searching
     */
    @RequestMapping("/search")
    @ResponseBody
    public ResponseEntity<List<ProjectDto>> searchProject
    (@RequestParam("textFilter") String textFilter, @RequestParam("selectionFilter") String selectionFilter,
     @RequestParam("page") int page)
    {
        List<Project> projects = projectService.findProjectsWithFilters(textFilter, selectionFilter, page, PAGE_SIZE);
        List<ProjectDto> projectDtoList = Mapper.projectsToProjectDtoList(projects);
        return new ResponseEntity<>(projectDtoList, HttpStatus.OK);
    }

    /**
     * Create new project
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseEntity<ProjectDto> saveProject(@RequestBody String projectBody) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ProjectDto projectDto = objectMapper.readValue(projectBody, ProjectDto.class);
        projectService.saveProject(projectDto);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    /**
     * Render project
     */
    @RequestMapping("/project/{num}")
    public String renderProject(@PathVariable("num") Short projectNumber, Model model){
        Project project = projectService.findProjectByProjectNumber(projectNumber);
        List<Long> groupIds = groupService.findGroupIds();
        List<String> emVisas = employeeService.findEmployeeVisas();
        List<String> employees = project.getEmployees().stream().map(Employee::getVisa).collect(Collectors.toList());
        model.addAttribute("project", project);
        model.addAttribute("groupIds", groupIds);
        model.addAttribute("emVisas", emVisas);
        model.addAttribute("group", project.getGroup().getId());
        model.addAttribute("employees", employees);
        return "form :: form-fragment";
    }

    /**
     * Update project
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseEntity<ProjectDto> updateProject(@RequestBody String projectBody) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ProjectDto projectDto = objectMapper.readValue(projectBody, ProjectDto.class);
        projectService.updateProject(projectDto);
        return new ResponseEntity<>(projectDto, HttpStatus.OK);
    }

    /**
     * Delete project
     */
    @RequestMapping("/project/delete/{num}")
    @ResponseBody
    public ResponseEntity<String> deleteProject(@PathVariable("num") Long projectId){
        projectService.deleteProjectByProjectId(projectId);
        return new ResponseEntity<>( "Deleted project", HttpStatus.OK);
    }

    /**
     * Multi-delete projects
     */
    @RequestMapping("multi/delete")
    @ResponseBody
    public ResponseEntity<String> multiDeleteProjects(@RequestParam("projectIds") String projectIds) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] ids = objectMapper.readValue(projectIds, String[].class);
        projectService.multiDeleteProjectByIds(ids);
        return new ResponseEntity<>( "Multi-deleted projects", HttpStatus.OK);
    }

    /**
     * Error fragment
     */
    @RequestMapping("/error")
    public String error(){
        return "error :: error-body";
    }

//    @Value("${total.number.of.projects}")
//    private String message;
//
//    @RequestMapping(value="/", method=RequestMethod.GET)
//    public ModelAndView main() {
//        Map<String, Object> model = new HashMap<String, Object>() {
//            private static final long serialVersionUID = -6883088231537577238L;
//            {
//                put("title", "Project Management Tool");
//                put("message", String.format(message, projectService.findAll().size()));
//            }
//        };
//        return new ModelAndView("search", model);
//    }

}
