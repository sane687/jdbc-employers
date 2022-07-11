package ru.alex.service;

import org.springframework.stereotype.Service;
import ru.alex.model.Employee;
import ru.alex.model.Job;
import ru.alex.repository.department.DepartmentRepositoryImp;
import ru.alex.repository.employee.EmployeeRepositoryImp;
import ru.alex.repository.job.JobRepositoryImp;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс сервис реализующий логику работы с сотрудниками
 */
@Service
public class EmployeeService {

    private final EmployeeRepositoryImp employeeRepositoryImp;
    private final JobRepositoryImp jobRepositoryImp;
    private final DepartmentRepositoryImp departmentRepositoryImp;

    public EmployeeService(EmployeeRepositoryImp employeeRepositoryImp, JobRepositoryImp jobRepositoryImp, DepartmentRepositoryImp departmentRepositoryImp) {
        this.employeeRepositoryImp = employeeRepositoryImp;
        this.jobRepositoryImp = jobRepositoryImp;
        this.departmentRepositoryImp = departmentRepositoryImp;
    }

    public List<Employee> getAllEmployee(){
        return employeeRepositoryImp.getAllEmployee();
    }

    public String saveEmployee(Employee employee){

        if(!checkForCorrectDepartment(employee)){
            System.out.println("НЕ ПРОШЛО");
            return "некорректное сочетание \"отдел-должность\"";
        }
        System.out.println("ПРОШЛО");

        if(checkForJobCapacity(employee.getJobId())){
            if(jobRepositoryImp.getJobById(employee.getJobId()).getCapacity()==1){
                departmentRepositoryImp.saveHead(employee);
            }
            employeeRepositoryImp.saveEmployee(employee);
            return null;
        }
        return "вы не можете создавать больше сотрудников на этой должности";
    }

    public Employee getEmployeeById(int id){
        return employeeRepositoryImp.getEmployeeById(id);
    }

    /**
     * Обновляет данные сотрудника
     * @param id идентификатор сотрудника
     * @param employee сотруник
     * @return строку с ошибкой или 'null' если ошибки нет
     */
    public String updateEmployee(int id, Employee employee){

        if(!checkForCorrectDepartment(employee)){
            return "некорректное сочетание \"отдел-должность\"";
        }

        boolean isSameJob = employeeRepositoryImp.getEmployeeById(id).getJobId()==employee.getJobId();

        if(isSameJob){
            employeeRepositoryImp.updateEmployee(id, employee);
            return null;
        }

        if(checkForJobCapacity(employee.getJobId())){
            if(jobRepositoryImp.getJobById(employee.getJobId()).getCapacity()==1){
                departmentRepositoryImp.saveHead(employee);
            }
            employeeRepositoryImp.updateEmployee(id, employee);
            return null;
        }

        return "вы не можете создавать больше сотрудников на этой должности";
    }

    public void deleteEmployeeById(int id){
        Employee employee = employeeRepositoryImp.getEmployeeById(id);

        if(employee.isHeadFlag()){
            departmentRepositoryImp.deleteHeadById(employee.getDepartmentId());
        }
        employeeRepositoryImp.deleteEmployeeById(id);
    }

    /**
     * Метод проверяющий есть ли еще место для нового сотрудника в должности
     * @param jobId идентификатор должности
     * @return значение true если есть место для нового сотрудника в должности
     */
    public boolean checkForJobCapacity(int jobId){

        List<Employee> employeeList = employeeRepositoryImp.getAllEmployee();
        List<Job> jobList = jobRepositoryImp.getAllJobs();

        long count = employeeList.stream().filter(employee -> employee.getJobId()==jobId).count();

        for(Job job : jobList) {
            if(job.getId()==jobId && job.getCapacity()<=count){
                return false;
            }
        }
        return true;
    }

    /**
     * Метод проверяет если должность работника принадлежит соответствующему департаменту
     * @param employee сотрудник
     * @return true если соответствует
     */
    public boolean checkForCorrectDepartment(Employee employee){

        List<Job> jobs = jobRepositoryImp.getAllJobs();

        Job temp = null;
        for(Job job : jobs){
            if(job.getId()==employee.getJobId()){
                temp=job;
            }
        }

        if(temp==null){ return false;}

        return temp.getDepartmentId()==(employee.getDepartmentId()) || temp.getDepartmentId()==0;

    }

}
