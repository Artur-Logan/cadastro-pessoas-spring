package com.arturlogan.cadastropessoasspring.services;

import com.arturlogan.cadastropessoasspring.dto.requests.PessoaRequest;
import com.arturlogan.cadastropessoasspring.dto.responses.PessoaResponse;
import com.arturlogan.cadastropessoasspring.entities.Pessoa;
import com.arturlogan.cadastropessoasspring.exceptions.PessoaNotFoundException;
import com.arturlogan.cadastropessoasspring.mappers.MapperPessoa;
import com.arturlogan.cadastropessoasspring.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final MapperPessoa mapperPessoa;

    public PessoaResponse salvar(PessoaRequest pessoaRequest){
        Pessoa pessoa = mapperPessoa.toModel(pessoaRequest);

        pessoaRepository.save(pessoa);

        return mapperPessoa.toResponse(pessoa);
    }

    public PessoaResponse obter(Long id){
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada com o Id: " + id));

        return mapperPessoa.toResponse(pessoa);
    }

    public void deletar(Long id){
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow( () -> new PessoaNotFoundException("Pessoa não encontrada com o Id: " + id));

        pessoaRepository.delete(pessoa);
    }

    public PessoaResponse atualizar(Long id, PessoaRequest pessoaRequest){
        Pessoa pessoa = pessoaRepository.findById(id).orElseThrow( () -> new PessoaNotFoundException("Pessoa não encontrada com o Id: " + id));

        mapperPessoa.atualizar(pessoaRequest, pessoa);

        pessoaRepository.save(pessoa);

        return mapperPessoa.toResponse(pessoa);
    }

    public List<PessoaResponse> listarTodos(){
        List<Pessoa> pessoaList = pessoaRepository.findAll();

        //decorar linha abaixo
        return pessoaList.stream().map(mapperPessoa::toResponse).collect(Collectors.toList());
    }
}
