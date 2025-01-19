package com.Job.Application.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Companies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000)
    private String desc;

    //@JsonIgnore
    //@OneToMany
    //private List<Jobs> jobs;
}
