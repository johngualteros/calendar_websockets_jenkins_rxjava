package com.app.calendar.tasks;

import com.app.calendar.tasks.utils.Day;
import com.app.calendar.tasks.utils.Month;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column
    @Size(min = 3, message = "the name must be have 3 characters min")
    private String name;

    @Column
    @Pattern(regexp = "([0-9])+:[0-9]{2} (AM|PM)", message = "the hour not has the correct format")
    private String hour;

    @Column
    @Enumerated(EnumType.STRING)
    private Day day;

    @Column
    @Min(value = 1, message = "the day number needs greater or equals to 1")
    @Max(value = 31, message = "the day number needs under or equals to 31")
    private int day_number;

    @Column
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column
    private String color;

}
