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
public class Credit {

	private int idCredit;

	private int nroDocument;
	private String typeCredit;
	private double creditLimit;
	// ****formate de fechas********************
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@CreatedDate
	private LocalDateTime membershipDate;

	public Credit(int idCredit, int nroDocument, String typeCredit, double creditLimit, LocalDateTime membershipDate) {
		super();
		this.idCredit = idCredit;
		this.nroDocument = nroDocument;
		this.typeCredit = typeCredit;
		this.creditLimit = creditLimit;
		this.membershipDate = membershipDate;
	}

	public Credit() {
		super();
	}

}
