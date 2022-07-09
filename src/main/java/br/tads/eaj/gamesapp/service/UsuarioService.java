package br.tads.eaj.gamesapp.service;

import br.tads.eaj.gamesapp.domain.Jogos;
import br.tads.eaj.gamesapp.domain.Usuario;
import br.tads.eaj.gamesapp.repository.JogosInterface;
import br.tads.eaj.gamesapp.repository.UsuarioInterface;

import java.util.Optional;

public class UsuarioService {

    private UsuarioInterface repository;
    public UsuarioService(UsuarioInterface repository){
        this.repository = repository;
    }
}
