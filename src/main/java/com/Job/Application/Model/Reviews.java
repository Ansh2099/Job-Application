package com.Job.Application.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100)
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 100)
    private String desc;

    @NotBlank(message = "Rating cannot be blank")
    private double rating;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Companies company;
}
