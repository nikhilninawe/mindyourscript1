package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import db.JobDetail;

@Repository
public interface JobDetailRepository extends CrudRepository<JobDetail, Long> {
	JobDetail findTop1ByJobIdOrderByIdDesc(Long id);
}
