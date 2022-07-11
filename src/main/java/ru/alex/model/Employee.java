package ru.alex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * Сущность сотрудника компании
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    int id;
    @NotEmpty(message = "поле не должно быть пустым")
    @Pattern(regexp = "^[а-яёА-ЯЁ]+", message = "только русские буквы")
    String employeeName;
    @Range(min = 1, message = "поле не должно быть пустым")
    int jobId;
    @Range(min = 1, message = "поле не должно быть пустым")
    int departmentId;
    int salary;
    String jobTitle;
    String departmentTitle;
    boolean headFlag;
}
