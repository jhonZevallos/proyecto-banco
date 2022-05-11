package com.customer.service.client;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class Account {

	private int accountNumber;

	private int nroDocument;
	private String typeAccount;
	// ****formate de fechas********************
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@CreatedDate
	private LocalDateTime membershipDate;

	public Account(int accountNumber, int nroDocument, String typeAccount, LocalDateTime membershipDate) {
		super();
		this.accountNumber = accountNumber;
		this.nroDocument = nroDocument;
		this.typeAccount = typeAccount;
		this.membershipDate = membershipDate;
	}

	public Account() {
		super();
	}

}
