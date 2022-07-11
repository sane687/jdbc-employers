package ru.alex.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.alex.model.Job;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс маппер для связи сущности должности с соответствующей таблицой в БД
 */
public class JobMapper implements RowMapper<Job> {

    @Override
    public Job mapRow(ResultSet resultSet, int i) throws SQLException {

        Job job = new Job();

        job.setId(resultSet.getInt("ID"));
        job.setJobName(resultSet.getString("NAME"));
        job.setSalary(resultSet.getInt("SALARY"));
        job.setCapacity(resultSet.getInt("CAPACITY"));
        job.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));

        return job;
    }
}
