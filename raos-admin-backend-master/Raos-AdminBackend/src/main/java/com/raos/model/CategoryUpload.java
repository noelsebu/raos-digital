package com.raos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpload {

	private int category_id;
	private String category_name;
	private String category_alias_name;
	private String category_type_code;
	private Date created;
	private Date updated;
	
}
