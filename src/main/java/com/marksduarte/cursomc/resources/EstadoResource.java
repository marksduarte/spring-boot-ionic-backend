package com.marksduarte.cursomc.resources;

import com.marksduarte.cursomc.dto.CidadeDTO;
import com.marksduarte.cursomc.dto.EstadoDTO;
import com.marksduarte.cursomc.services.CidadeService;
import com.marksduarte.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<EstadoDTO> estados = estadoService.findAll()
                .stream().map(o -> new EstadoDTO(o)).collect(Collectors.toList());
        return ResponseEntity.ok().body(estados);
    }

    @RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<CidadeDTO> cidades = cidadeService.findByEstado(estadoId)
                .stream().map(o -> new CidadeDTO(o)).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidades);
    }
}
