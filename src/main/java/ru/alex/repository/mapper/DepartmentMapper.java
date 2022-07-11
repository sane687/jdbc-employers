package ru.alex.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.alex.model.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс маппер для связи сущности отдела с соответствующей таблицой в БД
 */
public class DepartmentMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
        Department department = new Department();

        department.setId(resultSet.getInt("ID"));
        department.setDepartmentName(resultSet.getString("NAME"));
        department.setPhoneNumber(resultSet.getString("TELEPHONE_NUMBER"));
        department.setEmail(resultSet.getString("EMAIL"));
        department.setHead(resultSet.getString("HEAD"));

        return department;
    }
}
