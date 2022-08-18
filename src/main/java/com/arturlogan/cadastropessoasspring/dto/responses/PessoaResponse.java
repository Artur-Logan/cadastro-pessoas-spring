package com.arturlogan.cadastropessoasspring.dto.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PessoaResponse {

    private Long id;
    private String nome;
    private String sobreNome;
    private Integer idade;
    private LocalDate dataCadastro;
    private String estadoCivil;
}