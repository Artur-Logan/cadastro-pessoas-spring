package com.arturlogan.cadastropessoasspring.mappers;

import com.arturlogan.cadastropessoasspring.dto.requests.PessoaRequest;
import com.arturlogan.cadastropessoasspring.dto.responses.PessoaResponse;
import com.arturlogan.cadastropessoasspring.entities.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MapperPessoa {

    Pessoa toModel(PessoaRequest pessoaRequest);
    PessoaResponse toResponse(Pessoa pessoa);
    Pessoa atualizar(PessoaRequest pessoaRequest, @MappingTarget Pessoa pessoa);
}