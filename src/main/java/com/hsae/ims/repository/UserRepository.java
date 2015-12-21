package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>,JpaSpecificationExecutor<User>{
	
	@Query("select u from User u where u.username=?1 and u.freeze='0'")
	public User findByUsername(String username);

	@Modifying
	@Query("update User a set a.freeze='1',a.weekreport='0',a.train='0',a.attendance='0' where a.id=?1")
	public int freezeUser(long id);
	
	@Modifying
	@Query("update User a set a.freeze='0',a.weekreport='1',a.train='1',a.attendance='1' where a.id=?1")
	public int unFreezeUser(long id);

	@Modifying
	@Query("update User a set a.password=?2 where a.id=?1")
	public int resetpwd(long id, String defaultpwd);
	
	@Query("select s from User s where s.weekreport='1'")
	public List<User> getUserByWeekReport();
	
	@Query("select s from User s where s.freeze='0'")
	public List<User> findValidUser();
	
	@Query("select u from User u where u.empnumber= ?1")
	public User getUserByNo(String empnumber);
	
	@Query("select u from User u where u.id= ?1 ")
	public User getUserById(long id);

	@Query("select u from User u where u.dept.id= ?1")
	public List<User> findUserBydept(long dept);
	
	@Query("select u from User u where u.authorityCode like ?1% ")
	public List<User> findUserByAuthority(String authorityScope);
	
	public List<User> findUserByAttendance(int attendance);

	@Query("select s from User s where s.train='1' and s.freeze='0' order by s.dept")
	public List<User> findAllTraining();

	public List<User> findByDept(Deptment dept);
}
