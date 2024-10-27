package br.com.golden.raspberry.awards.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity(name = "filme")
public class FilmeEntity {

	/**
	 * Identificador
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * year
	 */
	private Integer ano;
	/**
	 * Title
	 */
	private String titulo;
	/**
	 * Studios
	 */
	private String studios;
	/**
	 * Producer
	 */
	private String produtor;
	/**
	 * Winner
	 */
	private Boolean vencedor;
	
	/**
	 * Linha referente a importação
	 */
	@Transient
	private long linha;

}
