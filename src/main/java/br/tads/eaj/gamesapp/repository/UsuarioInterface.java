package br.tads.eaj.gamesapp.repository;

import br.tads.eaj.gamesapp.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UsuarioInterface extends JpaRepository<Usuario,Long> {
}
