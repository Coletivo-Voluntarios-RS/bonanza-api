package ong.bonanza.beneficiarioapi.domain.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "padrinhos", uniqueConstraints = @UniqueConstraint(columnNames = "pessoa_id"))
public class Padrinho {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Pessoa pessoa;

    @ManyToMany
    @JoinTable(name = "padrinhos_beneficiarios", inverseJoinColumns = @JoinColumn(name = "beneficiario_id"))
    private List<Beneficiario> beneficiarios;

}