package com.arturlogan.cadastropessoasspring.exceptions;

public class PessoaNotFoundException extends RuntimeException{

    public PessoaNotFoundException(String msg) {
        super(msg);
    }
}
