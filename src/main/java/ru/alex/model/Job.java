package ru.alex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Сущность сотрудника компании
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    int id;
    @Pattern(regexp = "([а-яА-Я]+ ?)+", message = "только русские буквы")
    String jobName;
    @Range(min = 1, message = "минимальное значение - 1")
    int salary;
    @NotNull(message = "поле не должно быть пустым")
    @Range(min = 1, message = "минимальное значение - 1")
    int capacity;
    Integer departmentId;

}
