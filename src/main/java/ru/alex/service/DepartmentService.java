package ru.alex.service;

import org.springframework.stereotype.Service;
import ru.alex.model.Department;
import ru.alex.repository.department.DepartmentRepositoryImp;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Класс сервис реализующий логику работы с отделами
 */
@Service
public class DepartmentService {

    DepartmentRepositoryImp departmentRepositoryImp;

    public DepartmentService(DepartmentRepositoryImp departmentRepositoryImp) {
        this.departmentRepositoryImp = departmentRepositoryImp;
    }

    public List<Department> getAllDepartments(){
        return departmentRepositoryImp.getAllDepartments();
    }

    public Department getDepartmentById(int id) {
        return departmentRepositoryImp.getDepartmentById(id);
    }

    public Set<String> saveDepartment(Department department){

        Set<String> errors = findDuplicateColumns(department);

        if(errors.isEmpty()){
            departmentRepositoryImp.saveDepartment(department);
        }
        return errors;
    }

    public Set<String> updateDepartment(int id, Department department){

        Set<String> errors = findDuplicateColumns(department);

        if(errors.isEmpty()){
            departmentRepositoryImp.updateDepartment(id, department);
        }
        return errors;
    }

    /**
     * Метод проверяет если параметры 'имя' 'телефон' и 'email' уже существуют в таблице
     * @param department
     * @return множество строк с ошибками
     */
    public Set<String> findDuplicateColumns(Department department){
        Set<String> errors = new HashSet<>();
        List<Department> departments = departmentRepositoryImp.getAllDepartments();

        departments.forEach(dep ->{
            if(dep.getId()!=department.getId()){
                if(dep.getDepartmentName().equals(department.getDepartmentName())){
                    errors.add("отдел с таким названием уже существует");
                }
                if(dep.getPhoneNumber().equals(department.getPhoneNumber())){
                    errors.add("отдел с таким телефоном уже существует");
                }
                if(dep.getEmail().equals(department.getEmail())){
                    errors.add("отдел с таким email уже существует");
                }
            }
        });
        return errors;
    }

    public void deleteDepartmentById(int id){
        departmentRepositoryImp.deleteDepartmentById(id);
    }
}
