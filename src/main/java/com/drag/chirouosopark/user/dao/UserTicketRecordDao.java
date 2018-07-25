package com.drag.chirouosopark.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.drag.chirouosopark.user.entity.UserTicketRecord;


public interface UserTicketRecordDao extends JpaRepository<UserTicketRecord, String>, JpaSpecificationExecutor<UserTicketRecord> {
	
	
	
}
