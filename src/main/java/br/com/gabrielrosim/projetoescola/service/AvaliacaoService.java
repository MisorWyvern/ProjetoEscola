package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.AvaliacaoDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.AvaliacaoMapper;
import br.com.gabrielrosim.projetoescola.exception.AvaliacaoAlreadyExistsException;
import br.com.gabrielrosim.projetoescola.model.Avaliacao;
import br.com.gabrielrosim.projetoescola.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    DisciplinaService disciplinaService;
    @Autowired
    AlunoService alunoService;
    @Autowired
    TipoAvaliacaoService tipoAvaliacaoService;

    @Autowired
    AvaliacaoMapper avaliacaoMapper;

    public List<AvaliacaoDTO> getAvaliacoes() {
        return avaliacaoRepository.findAll()
                .parallelStream()
                .map(avaliacaoMapper::toAvaliacaoDTO)
                .collect(Collectors.toList());
    }


    public Optional<AvaliacaoDTO> criarAvaliacao(AvaliacaoDTO dto) {

        if(disciplinaService.getDisciplinaByIndex(dto.getIdDisciplina()).isEmpty()){
            return Optional.empty();
        }

        if(tipoAvaliacaoService.getTipoAvaliacaoById(dto.getIdTipoAvaliacao()).isEmpty()){
            return Optional.empty();
        }

        if(alunoService.getAlunoByIndex(dto.getIdAluno()).isEmpty()){
            return Optional.empty();
        }

        Avaliacao avaliacao = avaliacaoMapper.toAvaliacao(dto);

        if(avaliacaoRepository.findByDisciplinaAndTipoAvaliacaoAndAluno(
                avaliacao.getDisciplina(), avaliacao.getTipoAvaliacao(), avaliacao.getAluno()).isPresent()){
            throw new AvaliacaoAlreadyExistsException();
        }

        if(avaliacao.getNota() == null || avaliacao.getNota() < 0){
            avaliacao.setNota((double) 0);
        }

        if(avaliacao.getDataAplicacao() == null){
            avaliacao.setDataAplicacao(LocalDate.now());
        }

        Avaliacao savedAvaliacao = avaliacaoRepository.save(avaliacao);
        return Optional.of(avaliacaoMapper.toAvaliacaoDTO(savedAvaliacao));

    }
}
