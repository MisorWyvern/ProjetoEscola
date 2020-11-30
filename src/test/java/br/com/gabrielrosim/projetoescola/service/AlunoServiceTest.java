package br.com.gabrielrosim.projetoescola.service;

import br.com.gabrielrosim.projetoescola.dto.AlunoDTO;
import br.com.gabrielrosim.projetoescola.dto.mapper.AlunoMapper;
import br.com.gabrielrosim.projetoescola.model.Aluno;
import br.com.gabrielrosim.projetoescola.model.Programa;
import br.com.gabrielrosim.projetoescola.repository.AlunoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@DisplayName("Teste do Aluno Service")
@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class AlunoServiceTest {

    @Mock
    AlunoRepository alunoRepository;
    @Mock
    AlunoMapper alunoMapper;
    @InjectMocks
    AlunoService alunoService;

    @Test
    public void testGetAlunosOptionalEmpty() {
        Programa programa1 = new Programa(1L, "Fakitos", LocalDate.of(2020, 11, 24), LocalDate.of(2021, 2, 10), List.of());
        Aluno aluno1 = new Aluno("Pedro", "111.222.333-01", programa1);
        aluno1.setId(1L);
        aluno1.setActive(true);
        Aluno aluno2 = new Aluno("Jose", "111.222.333-02", programa1);
        aluno2.setId(2L);
        AlunoDTO dto1 = new AlunoDTO(aluno1.getId(),aluno1.getNome(),aluno1.getCpf(),aluno1.getPrograma().getId(), aluno1.getPrograma().getNome());
        AlunoDTO dto2 = new AlunoDTO(aluno2.getId(),aluno2.getNome(),aluno2.getCpf(),aluno2.getPrograma().getId(), aluno2.getPrograma().getNome());
        Pageable pageable = PageRequest.of(0,10, Sort.unsorted());
        Page<Aluno> alunoPage = new PageImpl<>(List.of(aluno1, aluno2), pageable, 2);

        Optional<Boolean> active = Optional.empty();

        Mockito.when(alunoRepository.findAll(pageable)).thenReturn(alunoPage);
        Mockito.when(alunoMapper.toAlunoDTO(aluno1)).thenReturn(dto1);
        Mockito.when(alunoMapper.toAlunoDTO(aluno2)).thenReturn(dto2);
        Page<AlunoDTO> listaAlunosDTO = alunoService.getAlunos(active, pageable);

        AlunoDTO alunoDTO1 = listaAlunosDTO.toList().get(0);
        AlunoDTO alunoDTO2 = listaAlunosDTO.toList().get(1);

        Mockito.verify(alunoRepository, times(1)).findAll(pageable);
        Mockito.verify(alunoMapper, times(1)).toAlunoDTO(aluno1);
        Mockito.verify(alunoMapper, times(1)).toAlunoDTO(aluno2);
        Assertions.assertEquals(2, listaAlunosDTO.toList().size());
        Assertions.assertAll(
                () -> Assertions.assertEquals(aluno1.getId(), alunoDTO1.getId()),
                () -> Assertions.assertEquals(aluno1.getNome(), alunoDTO1.getNome()),
                () -> Assertions.assertEquals(aluno1.getCpf(), alunoDTO1.getCpf()),
                () -> Assertions.assertEquals(aluno1.getPrograma().getId(), alunoDTO1.getIdPrograma())
        );
        Assertions.assertAll(
                () -> Assertions.assertEquals(aluno2.getId(), alunoDTO2.getId()),
                () -> Assertions.assertEquals(aluno2.getNome(), alunoDTO2.getNome()),
                () -> Assertions.assertEquals(aluno2.getCpf(), alunoDTO2.getCpf()),
                () -> Assertions.assertEquals(aluno2.getPrograma().getId(), alunoDTO2.getIdPrograma())
        );
    }

    @Test
    public void testGetAlunosTrue(){

    }
}
