package com.Job.Application.Controllers;

import com.Job.Application.Service.JobsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.Job.Application.Model.Jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/jobs")
public class JobControllers {

    @Autowired
    private JobsService service;

    @GetMapping("/")
    public ResponseEntity<List<Jobs>> getAllJobs() {
        return new ResponseEntity<>(service.getAllJobs(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jobs> getJobById(@PathVariable Long id){
        Jobs job = service.getJobById(id);
        if (job != null)
            return new ResponseEntity<>(job, HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> postJob(@Valid @RequestBody Jobs jobs){
        try{
            return new ResponseEntity<>(service.postJob(jobs), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteJob(@PathVariable long id){
        Jobs job = service.getJobById(id);
        if (job != null){
            service.deleteJob(id);
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobs(@Valid @RequestPart Jobs jobs, @RequestPart Long id){
        try {
            Jobs job = service.getJobById(id);

            if (job != null)
                return new ResponseEntity<>(service.updateJobs(jobs, id), HttpStatus.FOUND);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
