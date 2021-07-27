package com.asm_java6_pc00725.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Serializable {
	private static final long serialVersionUID = -5885342208000278840L;
	@Id
	Serializable group;
	Double sum;
	Long count;
}
