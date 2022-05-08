package com.transaction.service.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Document(collection = "transaction")
public class Transaction {

	@Id
	private int idTransaction;

	private int accountNumber;
	private int nroDocument;
	private String typeTransaction;
	// ****formate de fechas********************
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@CreatedDate
	private LocalDateTime dateTransaction;

}
