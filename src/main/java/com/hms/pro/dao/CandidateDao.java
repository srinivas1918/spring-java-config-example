package com.hms.pro.dao;

import java.util.Date;
import java.util.List;

import com.hms.pro.constants.QueryResultBySateEnum;
import com.hms.pro.domain.Candidate;
import com.hms.pro.ui.CandidateUI;

public interface CandidateDao extends AbstractDao<Candidate, Integer> {

	List<Candidate> getCandidates(QueryResultBySateEnum bySateEnum);
	List<CandidateUI> getCandidates(QueryResultBySateEnum bySateEnum, int candidateId);
	Candidate getCandidateDetails (QueryResultBySateEnum bySateEnum, int candidateId);
	int updateVacateData (QueryResultBySateEnum bySateEnum, int candidateId, Date vacateDate);
}