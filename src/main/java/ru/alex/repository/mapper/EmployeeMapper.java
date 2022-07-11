package ru.alex.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.alex.model.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Класс маппер для связи сущности сотрудника с соответствующей таблицой в БД
 */
public class EmployeeMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {

        Employee employee = new Employee();

        employee.setId(resultSet.getInt("ID"));
        employee.setEmployeeName(resultSet.getString("NAME"));
        employee.setJobId(resultSet.getInt("JOB_ID"));
        employee.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));
        employee.setJobTitle(resultSet.getString("JOB_TITLE"));
        employee.setDepartmentTitle(resultSet.getString("DEPARTMENT_TITLE"));
        employee.setSalary(resultSet.getInt("SALARY"));
        employee.setHeadFlag(resultSet.getBoolean("HEAD_FLAG"));

        return employee;
    }
}
