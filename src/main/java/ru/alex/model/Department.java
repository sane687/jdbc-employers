package ru.alex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Сущность отдела компании
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    int id;
    @Pattern(regexp = "([а-яА-Я]+ ?)+", message = "только русские буквы")
    String departmentName;
    @Pattern(regexp = "[1-9][0-9]\\-[0-9][0-9]\\-[0-9][0-9]", message = "введи номер в формате \"xx-xx-xx\"")
    String phoneNumber;
    @Pattern(regexp = "^\\w+@[a-zA-Z]+\\.[a-zA-Z]+$", message = "введите корректный email в формате \"xxx@yyy.zzz\"")
    String email;
    String head;
}
