package com.Job.Application.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import com.Job.Application.Constants.ReviewsConstants;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "reviews")
@NamedQueries({
    @NamedQuery(name = ReviewsConstants.FindByCompanyId, query = "SELECT r FROM Reviews r WHERE r.company.id = :companyId")
})
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100)
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000)
    private String description;

    @NotNull(message = "Rating cannot be null")
    private long rating;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Companies company;
}