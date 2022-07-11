package ru.alex.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alex.model.Job;
import ru.alex.service.DepartmentService;
import ru.alex.service.JobService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/job", method = {RequestMethod.GET, RequestMethod.POST})
public class JobController {

    DepartmentService departmentService;
    JobService jobService;

    @Autowired
    public JobController(DepartmentService departmentService, JobService jobService) {
        this.departmentService = departmentService;
        this.jobService = jobService;
    }

    @GetMapping
    public String jobs(Model model){
        List<Job> jobList = jobService.getAllJobs();
        model.addAttribute("jobs", jobList);
        return "job/job-management";
    }

    @GetMapping("/new")
    public String newJob(Model model){
        model.addAttribute("job", new Job());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "job/create-job-form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid Job job, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "job/create-job-form";
        }
        if(!jobService.saveJob(job)){
            model.addAttribute("jobAlreadyExistsError", "должность с таким названием уже существует");
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "job/create-job-form";
        }

        return "redirect:/job";
    }

    @PostMapping ("/delete/{id}")
    public String delete(@PathVariable int id){
        jobService.deleteJobById(id);
        return "redirect:/job";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("job", jobService.getJobById(id));
        return "job/job-edit-form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @Valid Job job, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "job/job-edit-form";
        }
        String error = (jobService.updateJob(id, job));
        if(error!=null){
            model.addAttribute("jobUpdateError", error);
            return "job/job-edit-form";
        }

        return "redirect:/job";
    }

}
