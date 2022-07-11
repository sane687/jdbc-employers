package ru.alex.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.model.Employee;
import ru.alex.service.DepartmentService;
import ru.alex.service.EmployeeService;
import ru.alex.service.JobService;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер с эндпоинтами для сотрудника
 */
@Controller
@RequestMapping(value = "/people", method = {RequestMethod.GET, RequestMethod.POST})
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final JobService jobService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService, JobService jobService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.jobService = jobService;
    }

    @GetMapping
    public String index(Model model){
        List<Employee> employeeList = employeeService.getAllEmployee();
        model.addAttribute("employers", employeeList);
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model){
        Employee employee = employeeService.getEmployeeById(id);
        if(employee==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PAGE NOT FOUND");
        }
        model.addAttribute("employee", employee);
        return "employee/employee-show";
    }

    @GetMapping("/new")
    public String newEmployee(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("jobs", jobService.getAllJobs());
        return "employee/create-employee-form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid Employee employee, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("jobs", jobService.getAllJobs());
            return "employee/create-employee-form";
        }
        String error = employeeService.saveEmployee(employee);
        if(error!=null){
            model.addAttribute("createEmployeeError", error);
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("jobs", jobService.getAllJobs());
            return "employee/create-employee-form";
        }
        return "redirect:/people";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("jobs", jobService.getAllJobs());
        return "employee/employee-edit-form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @Valid Employee employee, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("jobs", jobService.getAllJobs());
            return "employee/employee-edit-form";
        }
        String error = employeeService.updateEmployee(id, employee);
        if(error!=null){
            model.addAttribute("updateEmployeeError",  error);
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("jobs", jobService.getAllJobs());
            return "employee/employee-edit-form";
        }
        employeeService.updateEmployee(id, employee);
        return "redirect:/people";
    }

    @PostMapping ("/delete/{id}")
    public String delete(@PathVariable int id){
        employeeService.deleteEmployeeById(id);
        return "redirect:/people";
    }
}
