package com.arturlogan.cadastropessoasspring.dto.requests;

import lombok.Data;

@Data
public class PessoaRequest {

    private String nome;
    private String sobreNome;
    private Integer idade;
    private String estadoCivil;
}
