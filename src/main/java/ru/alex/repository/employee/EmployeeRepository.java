package ru.alex.repository.employee;

import ru.alex.model.Employee;
import java.util.List;

/**
 * Интерфейс описывающий методы доступа к сотрудникам
 */
public interface EmployeeRepository {

    void saveEmployee(Employee employee);

    void updateEmployee(int id, Employee employee);

    void deleteEmployeeById(int id);

    Employee getEmployeeById(int id);

    List<Employee> getAllEmployee();
}
