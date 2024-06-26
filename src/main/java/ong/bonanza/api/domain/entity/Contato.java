package ong.bonanza.api.domain.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ong.bonanza.api.domain.enumeration.TipoContato;

@Getter
@Setter
@ToString
@Entity
@Table(name = "contatos")
public class Contato {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Pessoa pessoa;

    private boolean principal;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoContato tipo;

    private String valor;

}
