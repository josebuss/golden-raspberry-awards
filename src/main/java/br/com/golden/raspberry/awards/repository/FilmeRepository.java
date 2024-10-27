package br.com.golden.raspberry.awards.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.golden.raspberry.awards.entity.FilmeEntity;

public interface FilmeRepository extends JpaRepository<FilmeEntity, Integer> {

	boolean existsByProdutorAndTitulo(String produtor, String titulo);

}
