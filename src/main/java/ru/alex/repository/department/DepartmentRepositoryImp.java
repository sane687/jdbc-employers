package ru.alex.repository.department;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alex.model.Department;
import ru.alex.model.Employee;
import ru.alex.model.Job;
import ru.alex.repository.mapper.DepartmentMapper;
import ru.alex.repository.mapper.JobMapper;

import java.sql.SQLException;
import java.util.List;

/**
 * Класс реализующий доступ к отделам
 */
@Component
public class DepartmentRepositoryImp implements DepartmentRepository{

    JdbcTemplate jdbcTemplate;

    public DepartmentRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveDepartment(Department department) {
        int highestId = 0;
        try{
            highestId = jdbcTemplate.queryForObject("SELECT MAX(ID) FROM DEPARTMENT",int.class);
        } catch (NullPointerException ignored) {
        }

        jdbcTemplate.update("INSERT INTO DEPARTMENT (ID, NAME, TELEPHONE_NUMBER, EMAIL) VALUES (?,?,?,?)",
                highestId + 1, department.getDepartmentName(), department.getPhoneNumber(), department.getEmail());
    }

    @Override
    public void updateDepartment(int id, Department department) {

        jdbcTemplate.update("UPDATE DEPARTMENT SET NAME =?, TELEPHONE_NUMBER =?, EMAIL=? WHERE ID=?",
                department.getDepartmentName(), department.getPhoneNumber(), department.getEmail(), id);
    }

    @Override
    public List<Department> getAllDepartments() {
        String sql = "SELECT * FROM DEPARTMENT";
        return jdbcTemplate.query(sql, new DepartmentMapper());
    }

    @Override
    public void deleteDepartmentById(int id) {
        jdbcTemplate.update("DELETE FROM DEPARTMENT WHERE ID=?", id);
    }


    @Override
    public Department getDepartmentById(int id) {
        String sql = "SELECT * FROM DEPARTMENT WHERE DEPARTMENT.ID=?";
        return jdbcTemplate.query(sql ,new Object[]{id}, new DepartmentMapper()).
                stream().findAny().orElse(null);
    }

    @Override
    public Department getDepartmentByName(String name){
        String sql = "SELECT * FROM DEPARTMENT WHERE NAME=?";

        return jdbcTemplate.query(sql ,new Object[]{name}, new DepartmentMapper()).
                stream().findAny().orElse(null);
    }

    /**
     * Метод сохраняющий начальника отдела
     */
    @Override
    public void saveHead(Employee employee){
        jdbcTemplate.update("UPDATE DEPARTMENT SET HEAD =? WHERE ID = ?",
                employee.getEmployeeName(), employee.getDepartmentId());
    }
    /**
     * Метод удаляющий начальника отдела
     */
    @Override
    public void deleteHeadById(int id){
        jdbcTemplate.update("UPDATE DEPARTMENT SET HEAD =? WHERE ID = ?",
                null, id);
    }
}
