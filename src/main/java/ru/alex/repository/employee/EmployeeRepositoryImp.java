package ru.alex.repository.employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alex.model.Employee;
import ru.alex.repository.mapper.EmployeeMapper;
import java.util.List;

/**
 * Класс реализующий доступ к сотрудникам
 * Реализует все CRUD методы
 */
@Component
public class EmployeeRepositoryImp implements EmployeeRepository{

    JdbcTemplate jdbcTemplate;

    public EmployeeRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> getAllEmployee() {
        String sql = "SELECT EMPLOYEE.*,JOB.NAME AS JOB_TITLE, " +
                "DEPARTMENT.NAME AS DEPARTMENT_TITLE, " +
                "JOB.SALARY " +
                "FROM EMPLOYEE LEFT JOIN JOB ON EMPLOYEE.JOB_ID=JOB.ID " +
                "LEFT JOIN DEPARTMENT ON EMPLOYEE.DEPARTMENT_ID=DEPARTMENT.ID";

        return jdbcTemplate.query(sql, new EmployeeMapper());
    }

    @Override
    public Employee getEmployeeById(int id) {

        String sql = "SELECT EMPLOYEE.*,JOB.NAME AS JOB_TITLE, " +
                "DEPARTMENT.NAME AS DEPARTMENT_TITLE, " +
                "JOB.SALARY " +
                "FROM EMPLOYEE LEFT JOIN JOB ON EMPLOYEE.JOB_ID=JOB.ID " +
                "LEFT JOIN DEPARTMENT ON EMPLOYEE.DEPARTMENT_ID=DEPARTMENT.ID " +
                "WHERE EMPLOYEE.ID=?";

        return jdbcTemplate.query(sql ,new Object[]{id}, new EmployeeMapper()).
                stream().findAny().orElse(null);
    }

    @Override
    public void saveEmployee(Employee employee) {
        int highestId = 0;
        try{
            highestId = jdbcTemplate.queryForObject("SELECT MAX(ID) FROM EMPLOYEE",int.class);
        } catch (NullPointerException ignored) {
        }

        jdbcTemplate.update("INSERT INTO EMPLOYEE (ID, NAME, JOB_ID, DEPARTMENT_ID, HEAD_FLAG) VALUES (?,?,?,?,?)",
                highestId + 1, employee.getEmployeeName(), employee.getJobId(), employee.getDepartmentId(), employee.isHeadFlag());
    }

    @Override
    public void updateEmployee(int id, Employee employee) {
        jdbcTemplate.update("UPDATE EMPLOYEE SET NAME =?, JOB_ID =?, DEPARTMENT_ID=?, HEAD_FLAG=? WHERE ID=?",
                employee.getEmployeeName(), employee.getJobId(), employee.getDepartmentId(), employee.isHeadFlag(), id);
    }

    @Override
    public void deleteEmployeeById(int id) {
        jdbcTemplate.update("DELETE FROM EMPLOYEE WHERE ID=?", id);
    }

}
