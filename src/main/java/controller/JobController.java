package controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import core.ShellExecutor;
import db.Job;
import db.JobDetail;
import db.JobDetailEntry;
import repository.JobDetailRepository;
import repository.JobRepository;

@RestController
public class JobController {

	@Autowired JobRepository jobRepo;
	@Autowired JobDetailRepository jobDetailRepository;

	@Autowired
	private TaskScheduler scheduler;

//	@PostConstruct
	public void init() {
		try {
			schedule();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/schedule")
	public void schedule() throws ParseException {
		Iterable<Job> iterable = jobRepo.findAll();
		for(Job s: iterable){
			ShellExecutor executor = new ShellExecutor(s, jobDetailRepository);
			scheduler.schedule(executor, new CronTrigger(s.getSchedule()));
		}
	}
	
	private void schedule(Job j){
		ShellExecutor executor = new ShellExecutor(j, jobDetailRepository);
		scheduler.schedule(executor, new CronTrigger(j.getSchedule()));
	}

	@RequestMapping("/getJobs")
	public Iterable<Job> getAllJobs(){
		return jobRepo.findAll();
	}

	@RequestMapping("/getJobDetail")
	public List<JobDetailEntry> getAllJobDetails() {
		List<JobDetailEntry> entries = new ArrayList<>();
		Iterable<Job> iterable = jobRepo.findAll();
		for(Job j : iterable){
			JobDetailEntry entry = new JobDetailEntry();
			JobDetail jd = jobDetailRepository.findTop1ByJobIdOrderByIdDesc(j.getId());
			entry.setName(j.getName());
			entry.setLastRunTime(jd.getLastRunTime().toString());
			entry.setLastResponse(jd.getResult());
			entry.setStatus(jd.getStatus());
			entries.add(entry);
		}
		return entries;
	}
	
	@RequestMapping(value="/create/{name}", method=RequestMethod.POST)
	public Job createJob(@PathVariable String name) {
		Job job = new Job();
		job.setFileName("/myntra/scripts/1.py");
		job.setMachineIpAddress("e5");
		job.setName(name);
		job.setSchedule("0/10 * * * * *");
		Job j = jobRepo.save(job);
		System.out.println(j);
		schedule(j);
		return j;
	}
}
