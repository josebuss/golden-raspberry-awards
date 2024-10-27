package br.com.golden.raspberry.awards.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.golden.raspberry.awards.dto.ProdutoresPremiadosDTO;
import br.com.golden.raspberry.awards.dto.ProdutoresPremiadosInfoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;

@Service
public class GetProdutoresPremiadosServiceImpl implements GetProdutoresPremiadosService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public ProdutoresPremiadosDTO getProdutoresPremiados() {
		return ProdutoresPremiadosDTO.builder() //
				.min(getProdutoresIntervalo(false)) //
				.max(getProdutoresIntervalo(true)) //
				.build();
	}

	@SuppressWarnings("unchecked")
	private List<ProdutoresPremiadosInfoDTO> getProdutoresIntervalo(boolean maiorIntervalo) {
		var query = em.createNativeQuery(buildSql(maiorIntervalo), Tuple.class);
		List<Tuple> resultList = query.getResultList();
		return resultList.stream() //
				.map(GetProdutoresPremiadosServiceImpl::convertToDto) //
				.collect(Collectors.toList());
	}

	private static ProdutoresPremiadosInfoDTO convertToDto(Tuple tuple) {
		return ProdutoresPremiadosInfoDTO.builder() //
				.producer(tuple.get("produtor", String.class)) //
				.interval(tuple.get("intervalo", Integer.class)) //
				.previousWin(tuple.get("ano_anterior", Integer.class)) //
				.followingWin(tuple.get("ano", Integer.class)) //
				.build();
	}

	private static String buildSql(boolean maiorIntervalo) {
		var sql = new StringBuilder() //
				.append(" with premios_ordenados ") //
				.append("      as (select produtor, ") //
				.append("                 ano, ") //
				.append("                 lag(ano, 1) over (partition by produtor order by ano) as ano_anterior ") //
				.append("          from   filme ") //
				.append("          where  vencedor = true), ") //
				.append("      intervalos ") //
				.append("      as (select produtor, ") //
				.append("                 ano, ") //
				.append("                 ano_anterior, ") //
				.append("                 ano - ano_anterior as intervalo ") //
				.append("          from   premios_ordenados ") //
				.append("          where  ano_anterior is not null) ") //
				.append(" select i.produtor, ") //
				.append("        i.intervalo, ") //
				.append("        i.ano, ") //
				.append("        i.ano_anterior ") //
				.append(" from   intervalos i ") //
				.append(" where  i.intervalo = (select ") //
				.append(getOperacao(maiorIntervalo)) //
				.append("(intervalo) from intervalos) ") //
				.append(" order  by i.produtor asc");
		return sql.toString();
	}

	private static String getOperacao(boolean maiorIntervalo) {
		return maiorIntervalo ? "max" : "min";
	}

}
