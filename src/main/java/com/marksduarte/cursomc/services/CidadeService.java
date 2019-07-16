package com.marksduarte.cursomc.services;

import com.marksduarte.cursomc.domain.Cidade;
import com.marksduarte.cursomc.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> findByEstado(final Integer estadoId) {
        return cidadeRepository.findCidades(estadoId);
    }
}
