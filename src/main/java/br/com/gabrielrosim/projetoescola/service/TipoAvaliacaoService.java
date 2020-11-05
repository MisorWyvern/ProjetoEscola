package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.TipoAvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.TipoAvaliacaoMapper;
import br.com.gabrielrosim.projetoescola.model.TipoAvaliacao;
import br.com.gabrielrosim.projetoescola.repository.TipoAvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoAvaliacaoService {

    @Autowired
    TipoAvaliacaoRepository tipoAvaliacaoRepository;

    @Autowired
    TipoAvaliacaoMapper tipoAvaliacaoMapper;

    public List<TipoAvaliacaoDTO> getTiposAvaliacoes() {
        return tipoAvaliacaoRepository.findAll()
                .parallelStream()
                .map(tipoAvaliacaoMapper::toTipoAvaliacaoDTO)
                .collect(Collectors.toList());
    }

    public Optional<TipoAvaliacaoDTO> getTipoAvaliacaoById(Long id) {
        Optional<TipoAvaliacao> tipoAvaliacao = tipoAvaliacaoRepository.findById(id);
        return tipoAvaliacao.map(tipoAv -> tipoAvaliacaoMapper.toTipoAvaliacaoDTO(tipoAv));
    }

    public TipoAvaliacaoDTO criarTipoAvaliacao(TipoAvaliacaoDTO dto) {
        TipoAvaliacao tipoAvaliacao = tipoAvaliacaoMapper.toTipoAvaliacao(dto);
        TipoAvaliacao savedTipoAvaliacao = tipoAvaliacaoRepository.save(tipoAvaliacao);
        return tipoAvaliacaoMapper.toTipoAvaliacaoDTO(savedTipoAvaliacao);
    }


}
