package br.tads.eaj.gamesapp.service;

import br.tads.eaj.gamesapp.domain.Jogos;
import br.tads.eaj.gamesapp.repository.JogosInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class JogosService {
    private JogosInterface repository;

    public JogosService(JogosInterface repository){
        this.repository = repository;
    }

    public Jogos cadastro(Jogos j){
        return repository.save(j);
    }

    public Jogos alterar(Jogos j){
        return repository.saveAndFlush(j);
    }
    public List <Jogos> Listall(){
        List<Jogos> lista_jogos = new ArrayList();
        List<Jogos> nova_lista_jogos = new ArrayList();

        lista_jogos = repository.findAll();
        var i = 0;

        for (i = 0; i <lista_jogos.size(); i++) {
            if( lista_jogos.get(i).getDeleted() == null){
                nova_lista_jogos.add( lista_jogos.get(i));
            }
        }

        return nova_lista_jogos;
    }
    public Jogos findById(Integer id){
        Optional<Jogos> jogos = repository.findById(id);
        return jogos.orElse(null);
    }
}
