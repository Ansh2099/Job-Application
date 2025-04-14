package com.Job.Application.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100)
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 100)
    private String desc;

    @NotNull(message = "Rating cannot be null")
    private double rating;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Companies company;
}