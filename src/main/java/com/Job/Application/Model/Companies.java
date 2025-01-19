package com.Job.Application.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Jobs> jobs;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Reviews> reviews;

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(List<Jobs> jobs) {
        this.jobs = jobs;
    }
}
