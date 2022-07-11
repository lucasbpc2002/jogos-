package br.tads.eaj.gamesapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Jogos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    String nome;
    @NotBlank
    String desenvolvedora;
    @NotBlank
    String empresa;
    @NotBlank
    String anolancamento;
    @NotBlank
    String plataformas;
    @NotBlank
    String preco;
    @NotBlank
    String imagemurl;
    Date deleted;
}
