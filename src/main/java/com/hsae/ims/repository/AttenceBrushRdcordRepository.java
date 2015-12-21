package com.hsae.ims.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.dto.EchartDto;
import com.hsae.ims.entity.AttenceBrushRecord;
public interface AttenceBrushRdcordRepository extends PagingAndSortingRepository<AttenceBrushRecord, Long>,JpaSpecificationExecutor<AttenceBrushRecord>{
	public List<AttenceBrushRecord> findByPersonIdAndBrushDate(Long user,Date date);
	@Query(value = "SELECT new com.hsae.ims.dto.EchartDto( t2.name,count(*)) FROM AttenceBrushRecord t1,Code t2  WHERE   t1.personId= ?1  and t1.brushDate>= ?2  and t1.brushDate<?3 and t2.code!='60' AND   t1.state=t2.code AND t2.identification='ATTENCETYPE'  GROUP By t1.state")
	public List<EchartDto> findUserAttenceCount(Long userid,Date start,Date end);
	
	@Query(value = "SELECT new com.hsae.ims.dto.EchartDto( t2.name,count(*)) FROM AttenceBrushRecord t1,Code t2  WHERE    t2.code!='60' AND   t1.state=t2.code AND t2.identification='ATTENCETYPE'  GROUP By t1.state")
	public List<EchartDto> findUserAttenceCount( PageRequest p);
	/**获取某天的刷卡数据*/
	@Query(value="SELECT CONCAT_WS(',',if(t1.id,'30',''), if(t2.id,'20',''), if(t3.id,'40',''))FROM ims_attence_absentee t1 "+
				 " LEFT JOIN"+
				 "  ims_attence_dayoff t2  "+
				 " ON t1.user=t2.user AND  t1.absentee_date=t2.dayoff_date "+
				 " LEFT JOIN ims_attence_travel t3 ON t1.user=t3.user AND t1.absentee_date=t3.travel_date "+
				 " WHERE t1.absentee_date=?2 AND t1.user=?1",nativeQuery=true)
	public String getStateByUserAndDate(Long userid,Date date);
	
	@Query(value=" SELECT COUNT(1) FROM ims_attence_dayoff t1 WHERE date(t1.start_time)<=?2 AND date(t1.end_time)>=?2 AND t1.user=?1",nativeQuery=true)
	public Integer getDayOff(Long userid,Date date);
	
	@Query(value=" SELECT COUNT(1) FROM ims_attence_travel t1 WHERE date(t1.start_time)<=?2 AND date(t1.end_time)>=?2 AND t1.user=?1",nativeQuery=true)
	public Integer getTravel(Long userid,Date date);
	
	@Query(value="SELECT  COUNT(1) FROM ims_attence_absentee t1 WHERE date(t1.absentee_date)=?2  AND t1.user=?1",nativeQuery=true)
	public Integer getAbsentee(Long userid,Date date);
	
}
