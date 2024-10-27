package br.com.golden.raspberry.awards.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

@ExtendWith(MockitoExtension.class)
class GetProdutoresPremiadosServiceImplTest {

	@InjectMocks
	private GetProdutoresPremiadosServiceImpl service;
	@Mock
	private EntityManager em;
	
	@Test
	void testSucesso() {
		Query query = mock(Query.class);
		Tuple tuple = mock(Tuple.class);
		when(em.createNativeQuery(anyString(), eq(Tuple.class))).thenReturn(query);
		when(query.getResultList()).thenReturn(List.of(tuple));
		
		service.getProdutoresPremiados();
	}

}
