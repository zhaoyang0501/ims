package com.hsae.ims.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hsae.ims.entity.Deptment;
public interface DeptRepository extends PagingAndSortingRepository<Deptment, Long>,JpaSpecificationExecutor<Deptment>{
	@Query("select s from Deptment s where s.id not in(select distinct b.pId from Deptment b)")
	public List<Deptment> getAllChildDept();
	public Iterable<Deptment> findByPId(long pid);
}
