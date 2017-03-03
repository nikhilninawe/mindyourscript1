package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import db.Job;

@Repository
public interface JobRepository extends CrudRepository<Job, Long> {
	Job findById(Long id);
}

