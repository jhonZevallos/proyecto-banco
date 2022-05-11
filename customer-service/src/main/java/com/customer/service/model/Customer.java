package com.customer.service.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "customer")
public class Customer {

	@Id
	private int id;

	private String documentType;
	private int nroDocument;
	private String names;
	private String surname;
	private String typeCustomer;
	// +++++Formato de fechas++++++++++++++
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@CreatedDate
	private LocalDateTime creationDate;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@CreatedDate
	private LocalDateTime modificationDate;

}
