package br.tads.eaj.gamesapp.service;

import br.tads.eaj.gamesapp.domain.Jogos;
import br.tads.eaj.gamesapp.repository.JogosInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class JogosService {
    private JogosInterface repository;

    public JogosService(JogosInterface repository){
        this.repository = repository;
    }

    public Jogos cadastro(Jogos M){
        return repository.save(M);
    }

    public Jogos alterar(Jogos M){
        return repository.saveAndFlush(M);
    }
    public void excluir(Integer id){
        this.repository.deleteById(id);
    }

    public List <Jogos> Listall(){
        return repository.findAll();
    }
    public Jogos findById(Integer id){
        Optional<Jogos> jogos = repository.findById(id);
        return jogos.orElse(null);
    }
}
