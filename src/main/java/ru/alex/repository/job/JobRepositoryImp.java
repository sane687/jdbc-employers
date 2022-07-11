package ru.alex.repository.job;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alex.model.Job;
import ru.alex.repository.mapper.JobMapper;

import java.util.List;

/**
 * Класс реализующий методы доступа к должностям
 */
@Component
public class JobRepositoryImp implements JobRepository{
    JdbcTemplate jdbcTemplate;

    public JobRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveJob(Job job) {
        int highestId = 0;
        try{
            highestId = jdbcTemplate.queryForObject("SELECT MAX(ID) FROM JOB",int.class);
        } catch (NullPointerException ignored) {
        }

        jdbcTemplate.update("INSERT INTO JOB (ID, NAME, SALARY, CAPACITY, DEPARTMENT_ID) VALUES (?,?,?,?,?)",
                highestId + 1, job.getJobName(), job.getSalary(), job.getCapacity(), job.getDepartmentId());
    }

    @Override
    public void updateJob(int id, Job job) {
        jdbcTemplate.update("UPDATE JOB SET NAME =?, SALARY =?, CAPACITY=? WHERE ID=?",
                job.getJobName(), job.getSalary(), job.getCapacity(), id);
    }

    @Override
    public void deleteJobById(int id) {
        jdbcTemplate.update("DELETE FROM JOB WHERE ID=?", id);
    }


    @Override
    public Job getJobById(int id) {
        String sql = "SELECT * FROM JOB WHERE ID=?";

        return jdbcTemplate.query(sql ,new Object[]{id}, new JobMapper()).
                stream().findAny().orElse(null);
    }

    @Override
    public Job getJobByName(String name){
        String sql = "SELECT * FROM JOB WHERE NAME=?";

        return jdbcTemplate.query(sql ,new Object[]{name}, new JobMapper()).
                stream().findAny().orElse(null);
    }

    @Override
    public List<Job> getAllJobs() {
        String sql = "SELECT * FROM JOB";
        return jdbcTemplate.query(sql, new JobMapper());
    }
}
