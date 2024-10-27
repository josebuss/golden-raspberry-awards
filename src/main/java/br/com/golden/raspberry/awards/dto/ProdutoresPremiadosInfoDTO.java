package br.com.golden.raspberry.awards.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoresPremiadosInfoDTO {

	private String producer;
	private Integer interval;
	private Integer previousWin;
	private Integer followingWin;

}
