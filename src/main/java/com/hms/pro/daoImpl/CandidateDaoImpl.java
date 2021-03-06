package com.hms.pro.daoImpl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.hms.pro.constants.QueryResultBySateEnum;
import com.hms.pro.dao.CandidateDao;
import com.hms.pro.domain.Candidate;
import com.hms.pro.domain.Payment;
import com.hms.pro.ui.CandidateUI;

@Repository
public class CandidateDaoImpl extends AbstractDaoImpl<Candidate, Integer> implements CandidateDao{

	public CandidateDaoImpl() {
		super(Candidate.class);
	}

	public List<CandidateUI> getCandidates(QueryResultBySateEnum bySateEnum, int buildingId,boolean onlyVacates) {
		String sql="SELECT c.candidate_id as candidateId, c.name as fullName, "
				+ "c.mobile_no as mobileNo, c.emergency_contact_no as emergencyContactNo, r.room_name as roomName, rt.room_category as "
				+ "roomCategory, c.candidate_fee as candidateFee, c.join_date as joinDate, c.vacation_flag as vacationFlag, "
				+ "c.vacation_date as vacationDate from candidate c join room r on "
				+ "r.room_id = c. room and r.building=:buildingId join room_type rt on rt.room_type_id = r.room_type where c.isActive=:active";
		if(onlyVacates){
			sql="SELECT c.candidate_id as candidateId, c.name as fullName, "
				+ "c.mobile_no as mobileNo, c.emergency_contact_no as emergencyContactNo, r.room_name as roomName, rt.room_category as "
				+ "roomCategory, c.candidate_fee as candidateFee, c.join_date as joinDate, c.vacation_flag as vacationFlag, "
				+ "c.vacation_date as vacationDate from candidate c join room r on "
				+ "r.room_id = c. room and r.building=:buildingId join room_type rt on rt.room_type_id = r.room_type where c.isActive=:active and c.vacation_flag =0";
		}
		Query query=getCurrentSession().createSQLQuery(sql);
		query.setParameter("active", bySateEnum.ordinal());
		query.setParameter("buildingId", buildingId);
		return (List<CandidateUI>) excuteQuery(query, CandidateUI.class);
	}
	
	public List<Candidate> getCandidates(QueryResultBySateEnum bySateEnum) {
		Query query=getCurrentSession().createQuery("from Candidate c where c.isActive=:active");
		query.setParameter("active",bySateEnum.ordinal());
		return query.list();
	}
	
	public int updateVacateData (QueryResultBySateEnum bySateEnum, int candidateId, Date vacateDate) {
		Query query = getCurrentSession().createQuery("update Candidate c set c.vacationDate=:vacateDate, c.vacationFlag=:vacateFlag where c.candidateId=:candidateId");
		query.setParameter("vacateDate", vacateDate);
		query.setParameter("candidateId", candidateId);
		query.setParameter("vacateFlag", bySateEnum.ordinal());
		return  query.executeUpdate();
	}
	
	public Candidate getCandidateDetails (QueryResultBySateEnum bySateEnum, int candidateId) {
		Query query = getCurrentSession().createQuery("from Candidate c where c.candidateId=:candidateId and c.isActive=:active");
		query.setParameter("candidateId", candidateId);
		query.setParameter("active", bySateEnum.ordinal());
		if (CollectionUtils.isEmpty(query.list())) {
			return null;
		}
		return (Candidate) query.list().get(0);
	}

	public List<Candidate> getCandidatesOfRoom(QueryResultBySateEnum state,
			Integer roomNo) {
		Query query=getCurrentSession().createQuery("from Candidate c where c.room.roomId=:roomNo and c.isActive=:active");
		query.setParameter("roomNo", roomNo);
		query.setParameter("active", state.ordinal());
		return query.list();
	}

	public List<Candidate> getPaymentsOfCandidates(QueryResultBySateEnum state,
			Date date,boolean isDelayed) {
		Query query=null;
		if(isDelayed){
			// Pendings
			query=getCurrentSession().createQuery("from Candidate c where c.dueDate <=:date and c.isActive=:state order by c.dueDate ASC");
		}else{
			// today and follwed by dates
			query=getCurrentSession().createQuery("from Candidate c where c.dueDate >=:date and c.isActive=:state ORDER BY c.dueDate ASC");
		}
		
		query.setParameter("date", date);
		query.setParameter("state", state.ordinal());
		return query.list();
	}

	public CandidateUI getPaymentDetails(int candidateId) {
		
		String qry="SELECT c.name as fullName, r.room_name as roomName, c.candidate_fee as candidateFee, c.due_amount as dueAmount" +
				" FROM candidate c join room r on r.room_id = c.room where c.candidate_id=:id";
		Query query=getCurrentSession().createSQLQuery(qry);
		query.setParameter("id", candidateId);
		List<CandidateUI> candidates=(List<CandidateUI>) excuteQuery(query, CandidateUI.class);
		if(candidates.size()>0){
			return candidates.get(0);
		}
		return null;
	}

	public List<Candidate> getPaymentsOfCandidates(QueryResultBySateEnum state,
			Date date, boolean isPending, int buildingId) {
		Query query=null;
		if(isPending){
			// Pendings
			query=getCurrentSession().createQuery("from Candidate c where c.dueDate <=:date and c.isActive=:state and c.room.building.buildingId=:id order by c.dueDate ASC");
		}else{
			// today and follwed by dates
			query=getCurrentSession().createQuery("from Candidate c where c.dueDate >=:date and c.isActive=:state and  c.room.building.buildingId=:id ORDER BY c.dueDate ASC");
		}
		query.setParameter("id", buildingId);
		query.setParameter("date", date);
		query.setParameter("state", state.ordinal());
		return query.list();
	}

	public List<Candidate> getPaymentsOfCandidates(
			QueryResultBySateEnum state, int buildingId) {
		Query query=null;
		if(buildingId!=0){
			query=getCurrentSession().createQuery("from Candidate c where c.isActive=:state and c.paymentStatus='Pending' and c.room.building.buildingId=:id ORDER BY c.pendingDueDate ASC");
			query.setParameter("id", buildingId);
		}else{
			query=getCurrentSession().createQuery("from Candidate c where c.isActive=:state and c.paymentStatus='Pending' ORDER BY c.pendingDueDate ASC");
		}
			// today and follwed by dates
		query.setParameter("state", state.ordinal());
		return query.list();

	}

	public List<Payment> getPaymentHistory(int candidateId) {
	
		String hql="from Payment p where p.candidate.candidateId=:cid";
		Query query=getCurrentSession().createQuery(hql);
		query.setParameter("cid", candidateId);
		return query.list();
	}

}
