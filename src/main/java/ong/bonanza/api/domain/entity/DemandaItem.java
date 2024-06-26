package ong.bonanza.api.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ong.bonanza.api.domain.enumeration.StatusDemandaItem;

@Getter
@Setter
@ToString
@Entity
@Table(name = "demandas_itens")
public class DemandaItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Usuario aprovador;

    @NotNull
    @ManyToOne
    private Usuario solicitante;

    @NotNull
    @ManyToOne
    private Beneficiario beneficiario;

    @NotNull
    private Integer quantidadeSolicitada;

    @NotNull
    @Column(nullable = false)
    private Integer quantidadeAtendida;

    @NotNull
    @ManyToOne
    private Item item;

    @Column(length = 250)
    private String detalhes;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusDemandaItem status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "optlock", nullable = false)
    private Long versionNum;

}