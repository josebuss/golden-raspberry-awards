package br.com.golden.raspberry.awards.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoresPremiadosDTO {
	
	private List<ProdutoresPremiadosInfoDTO> min;
	private List<ProdutoresPremiadosInfoDTO> max;
	

}
