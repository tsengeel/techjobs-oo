package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, RedirectAttributes attributes) {

        // TODO #6 - Validate the JobForm model, and if valid, create a


        if (errors.hasErrors()) {
            return "new-job";
        }


        String jobName = jobForm.getName();
        Employer jobEmployee = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location jobLocation = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType jobPosition = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency jobComp = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());


        Job newJob = new Job(jobName, jobEmployee, jobLocation, jobPosition, jobComp);


        jobData.add(newJob);


        attributes.addAttribute("id", newJob.getId());


        return "redirect:/job";

    }
}
