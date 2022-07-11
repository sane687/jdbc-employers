package ru.alex.repository.department;

import ru.alex.model.Department;
import ru.alex.model.Employee;

import java.util.List;

/**
 * Интерфейс описывающий методы доступа к отделу
 */
public interface DepartmentRepository {


    void saveDepartment(Department department);
    void deleteDepartmentById(int id);
    void updateDepartment(int id, Department department);
    Department getDepartmentById(int id);
    Department getDepartmentByName(String name);
    List<Department> getAllDepartments();


    void saveHead(Employee employee);
    void deleteHeadById(int id);



}
