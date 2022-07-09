package br.tads.eaj.gamesapp.repository;

import br.tads.eaj.gamesapp.domain.Jogos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogosInterface  extends JpaRepository<Jogos, Integer> {

}
