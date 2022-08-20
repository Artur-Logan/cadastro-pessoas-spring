package com.arturlogan.cadastropessoasspring.services;

import com.arturlogan.cadastropessoasspring.dto.requests.PessoaRequest;
import com.arturlogan.cadastropessoasspring.dto.responses.PessoaResponse;
import com.arturlogan.cadastropessoasspring.entities.Pessoa;
import com.arturlogan.cadastropessoasspring.exceptions.PessoaNotFoundException;
import com.arturlogan.cadastropessoasspring.repositories.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PessoaServiceTest {

    @MockBean
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Test
    void salvarSucesso() {
        //cenario
        PessoaRequest pessoaRequest = getPessoaRequest();

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome(getPessoaRequest().getNome());
        pessoa.setSobreNome(getPessoaRequest().getSobreNome());
        pessoa.setDataCadastro(LocalDate.now());
        pessoa.setIdade(getPessoaRequest().getIdade());
        pessoa.setEstadoCivil(getPessoaRequest().getEstadoCivil());

        //execução
        when(pessoaRepository.save(any()))
                .thenReturn(pessoa);

        //verificação
        assertEquals(pessoaRequest.getNome(), pessoa.getNome());
        assertEquals(pessoaRequest.getSobreNome(), pessoa.getSobreNome());
        assertEquals(pessoaRequest.getIdade(), pessoa.getIdade());
        assertEquals(pessoaRequest.getEstadoCivil(), pessoa.getEstadoCivil());
    }

    @Test
    void obterSucesso(){
        //cenario
        Long id = 1L;

        Pessoa pessoa = getPessoa(id);

        when(pessoaRepository.findById(any())).
                thenReturn(Optional.of(pessoa));

        //execução
        PessoaResponse pessoaResponse = pessoaService.obter(id);


        //verificação
        assertEquals(id, pessoaResponse.getId());
    }

    @Test
    void deletarSucesso() {
        //cenario
        Long id = 1L;

        when(pessoaRepository.findById(any()))
                .thenReturn(Optional.of(getPessoa(id)));

        //execução
        pessoaService.deletar(1L);
    }

    @Test
    void atualizarSucesso() {
        //cenario
        PessoaRequest pessoaRequest = getPessoaRequest();

        Pessoa pessoa = getPessoa(1L);

        pessoa.setNome(pessoaRequest.getNome());
        pessoa.setSobreNome(pessoaRequest.getSobreNome());
        pessoa.setIdade(pessoaRequest.getIdade());
        pessoa.setEstadoCivil(pessoaRequest.getEstadoCivil());

        when(pessoaRepository.findById(any()))
                .thenReturn(Optional.of(pessoa));

        when(pessoaRepository.save(any()))
                .thenReturn(pessoa);

        //execução
        PessoaResponse pessoaResponse = pessoaService.atualizar(pessoa.getId(), pessoaRequest);

        //verificação
        assertEquals(pessoaRequest.getNome(), pessoaResponse.getNome());
        assertEquals(pessoaRequest.getSobreNome(), pessoaResponse.getSobreNome());
        assertEquals(pessoaRequest.getIdade(), pessoaResponse.getIdade());
        assertEquals(pessoaRequest.getEstadoCivil(), pessoaResponse.getEstadoCivil());
        assertNotNull(pessoaResponse.getId());
    }

    @Test
    void listarTodosSucesso() {
        //cenario
        Pessoa pessoa = getPessoa(1L);
        Pessoa pessoa2 = getPessoa(2L);
        Pessoa pessoa3 = getPessoa(3L);
        Pessoa pessoa4 = getPessoa(4L);
        Pessoa pessoa5 = getPessoa(5L);
        Pessoa pessoa6 = getPessoa(6L);

        List<Pessoa> pessoaList = List.of(
                pessoa, pessoa2, pessoa3, pessoa4, pessoa5, pessoa6
        );

        when(pessoaRepository.findAll())
                .thenReturn(pessoaList);
        //execução
        List<PessoaResponse> pessoaResponses = pessoaService.listarTodos();

        //verificação
        assertTrue(pessoaList.size() > 0);
        assertEquals(6, pessoaList.size());
    }

    //testes esperando falha----------------------------------------------------------------------

    @Test
    void salvarFalha() {
        //cenario
        PessoaRequest pessoaRequest = getPessoaRequest();

        //execução
        when(pessoaRepository.save(any()))
                .thenThrow(new RuntimeException("Não foi possivel salvar a pessoa."));

        //verificação
        assertThrows(RuntimeException.class, () -> pessoaService.salvar(pessoaRequest));
    }

    @Test
    void deletarFalha() {
        //cenario
        Long id = 1L;

        when(pessoaRepository.findById(any()))
                .thenReturn(Optional.ofNullable(null));

        //execução
        assertThrows(PessoaNotFoundException.class,
                () -> pessoaService.deletar(1L));
    }

    @Test
    void atualizarFalha() {
        //cenario
        Long id = 1L;
        PessoaRequest pessoaRequest = getPessoaRequest();

        Pessoa pessoa = null;

        when(pessoaRepository.findById(any()))
                .thenReturn(Optional.ofNullable(pessoa));

        when(pessoaRepository.save(any()))
                .thenReturn(pessoa);

        //execução
        assertThrows(PessoaNotFoundException.class,
                () -> pessoaService.atualizar(id, pessoaRequest));
    }

    @Test
    void listarTodosFalha() {
        //cenario
        when(pessoaRepository.findAll())
                .thenReturn(null);

        //execução
        assertThrows(NullPointerException.class,
                () -> pessoaService.listarTodos());
    }

    @Test
    void obterFalha(){
        //cenario
        Long id = 1L;

        Pessoa pessoa = null;

        when(pessoaRepository.findById(any())).
                thenReturn(Optional.ofNullable(pessoa));

        //verificação
        assertThrows(PessoaNotFoundException.class,
                () -> pessoaService.obter(id));
    }

    private PessoaRequest getPessoaRequest(){
        PessoaRequest pessoaRequest = new PessoaRequest();

        pessoaRequest.setNome("Teste");
        pessoaRequest.setSobreNome("Teste");
        pessoaRequest.setIdade(10);
        pessoaRequest.setEstadoCivil("Teste");

        return pessoaRequest;
    }

    private Pessoa getPessoa(Long id){
        Pessoa pessoa = new Pessoa();

        pessoa.setId(id);
        pessoa.setDataCadastro(LocalDate.now());
        pessoa.setNome("Teste");
        pessoa.setSobreNome("Teste");
        pessoa.setIdade(10);
        pessoa.setEstadoCivil("Teste");

        return pessoa;
    }
}