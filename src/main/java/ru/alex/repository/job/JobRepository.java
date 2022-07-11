package ru.alex.repository.job;

import ru.alex.model.Employee;
import ru.alex.model.Job;
import java.util.List;

/**
 * Интерфейс описывающий методы доступа к должностям
 */
public interface JobRepository {

    void saveJob(Job job);
    void deleteJobById(int id);
    void updateJob(int id, Job employee);
    Job getJobById(int id);
    Job getJobByName(String name);
    List<Job> getAllJobs();
}
