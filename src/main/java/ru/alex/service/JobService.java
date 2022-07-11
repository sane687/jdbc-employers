package ru.alex.service;

import org.springframework.stereotype.Service;
import ru.alex.model.Employee;
import ru.alex.model.Job;
import ru.alex.repository.employee.EmployeeRepositoryImp;
import ru.alex.repository.job.JobRepositoryImp;

import java.util.List;
/**
 * Класс сервис реализующий логику работы с должностями
 */
@Service
public class JobService {

    JobRepositoryImp jobRepositoryImp;
    EmployeeRepositoryImp employeeRepositoryImp;

    public JobService(JobRepositoryImp jobRepositoryImp, EmployeeRepositoryImp employeeRepositoryImp) {

        this.jobRepositoryImp = jobRepositoryImp;
        this.employeeRepositoryImp = employeeRepositoryImp;
    }

    public List<Job> getAllJobs(){
        return jobRepositoryImp.getAllJobs();
    }

    public boolean saveJob(Job job){

        if(jobRepositoryImp.getJobByName(job.getJobName())!=null){
            return false;
        }

        jobRepositoryImp.saveJob(job);
        return true;
    }

    public void deleteJobById(int id){
        jobRepositoryImp.deleteJobById(id);
    }

    public Job getJobById(int id){
        return jobRepositoryImp.getJobById(id);
    }

    /**
     * Обновляет данные должности
     * @param id идентификатор должности
     * @param job сотруник
     * @return строку с ошибкой или 'null' если ошибки нет
     */
    public String updateJob(int id, Job job){
        if(jobRepositoryImp.getJobByName(job.getJobName())!=null){
            return "Должность с таким именем уже существует";
        }

        //проверка на то, что вместимость >= текущему количеству сотрудников
        List<Employee> employeeList = employeeRepositoryImp.getAllEmployee();
        long employeeCount = employeeList.stream().filter(employee -> employee.getJobId()==id).count();

        if(employeeCount > job.getCapacity()){
            return "Вместимость должна быть не меньше количества текущих сотрудников";
        }

        jobRepositoryImp.updateJob(id, job);
        return null;
    }

}
