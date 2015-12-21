package com.hsae.ims.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.message.Message;
public interface MessageRepository extends PagingAndSortingRepository<Message, Long>,JpaSpecificationExecutor<Message>{
	@Query("select message from Message message where message.user=?1 and message.state=?2  order by createDate")
	public List<Message> findByUserAndState(User user,String state);
}



