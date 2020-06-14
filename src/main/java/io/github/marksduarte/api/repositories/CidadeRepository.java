package io.github.marksduarte.api.repositories;

import io.github.marksduarte.api.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Transactional(readOnly = true)
    @Query("select c from Cidade c where c.estado.id = :estadoId order by c.nome")
    public List<Cidade> findCidades(@Param("estadoId") Integer estadoId);
}
