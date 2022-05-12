package com.yanky.service.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "yanki")
public class Yanki {

	@Id
	private String id;
	private int nroDocument;
	private int phoneNumber;
	private Double amount;
	private String email;
	private String imeiNumber;
	
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDateTime createDate;
}
