package com.arturlogan.cadastropessoasspring.controllers;

import com.arturlogan.cadastropessoasspring.dto.requests.PessoaRequest;
import com.arturlogan.cadastropessoasspring.dto.responses.PessoaResponse;
import com.arturlogan.cadastropessoasspring.entities.Pessoa;
import com.arturlogan.cadastropessoasspring.services.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<PessoaResponse> salvar(@RequestBody PessoaRequest pessoaRequest){
        PessoaResponse pessoaResponse = pessoaService.salvar(pessoaRequest);

        return ResponseEntity.ok().body(pessoaResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> obter(@PathVariable Long id){
        PessoaResponse pessoaResponse = pessoaService.obter(id);

        return ResponseEntity.ok().body(pessoaResponse);
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponse>> listarTodos(){
        List<PessoaResponse> pessoaResponses = pessoaService.listarTodos();

        return ResponseEntity.ok().body(pessoaResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        pessoaService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> atualizar(@PathVariable Long id, @RequestBody PessoaRequest pessoaRequest){
        PessoaResponse pessoaResponse = pessoaService.atualizar(id, pessoaRequest);

        return ResponseEntity.ok().body(pessoaResponse);
    }
}