package ru.alex.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.model.Department;
import ru.alex.model.Job;
import ru.alex.service.DepartmentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Контроллер с эндпоинтами для департаментов
 */
@Controller
@RequestMapping(value = "/department", method = {RequestMethod.GET, RequestMethod.POST})
public class DepartmentController {

    final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping
    public String departments(Model model){
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "department/department-management";
    }


    @GetMapping("/new")
    public String newDepartment(Model model){
        model.addAttribute("department", new Department());
        return "department/create-department-form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid Department department, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "department/create-department-form";
        }
        Set<String> errors = departmentService.saveDepartment(department);
        if(!errors.isEmpty()){
            model.addAttribute("duplicateErrors", errors);
            return "department/create-department-form";
        }

        return "redirect:/department";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "department/department-edit-form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @Valid Department department, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "department/department-edit-form";
        }
        Set<String> errors = departmentService.updateDepartment(id, department);
        if(!errors.isEmpty()){
            model.addAttribute("duplicateErrors", errors);
            return "department/department-edit-form";
        }

        return "redirect:/department";
    }

    @PostMapping ("/delete/{id}")
    public String delete(@PathVariable int id){
        departmentService.deleteDepartmentById(id);
        return "redirect:/department";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model){
        Department department = departmentService.getDepartmentById(id);
        if(department==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PAGE NOT FOUND");
        }
        model.addAttribute("department", department);
        return "department/department-show";
    }

}
