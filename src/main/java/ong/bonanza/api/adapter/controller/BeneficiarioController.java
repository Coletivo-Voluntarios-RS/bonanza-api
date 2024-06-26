package ong.bonanza.api.adapter.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import ong.bonanza.api.adapter.provider.AuthenticationProvider;
import ong.bonanza.api.application.usecase.BuscarBeneficiariosPaginadoUC;
import ong.bonanza.api.application.usecase.IniciarAtendimentoDemandaItemUC;

@RestController
@RequiredArgsConstructor
@RequestMapping("beneficiarios")
public class BeneficiarioController {

    private final AuthenticationProvider authenticationProvider;

    private final BuscarBeneficiariosPaginadoUC buscarBeneficiarioPaginadoUC;

    private final IniciarAtendimentoDemandaItemUC iniciarAtendimentoDemandaItemUC;

    @Operation(summary = "Buscar beneficiários", description = "Busca beneficiários com paginação e por ordem de último atualizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BuscarBeneficiariosPaginadoUC.BeneficiarioDTO.class)))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    ResponseEntity<List<BuscarBeneficiariosPaginadoUC.BeneficiarioDTO>> BuscarBeneficiarios(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok(buscarBeneficiarioPaginadoUC.executar(page, size));
    }

    @Operation(summary = "Inicia atendimento demanda item", security = @SecurityRequirement(name = "bearerAuth"), description = "Inicia Atendimento de demanda, se mais de um provedor tentar pegar a mesma demanda ao mesmo tempo será lancaçado um erro de conflito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = IniciarAtendimentoDemandaItemUC.AtendimentoDemandaItemDTO.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "422", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("{beneficiarioId}/demandas-itens/{demandaItemId}/atendimentos")
    ResponseEntity<IniciarAtendimentoDemandaItemUC.AtendimentoDemandaItemDTO> iniciarAtendimentoDemanda(
            @PathVariable UUID beneficiarioId,
            @PathVariable UUID demandaItemId,
            @RequestBody Integer quantidadeAtendimento) {

        IniciarAtendimentoDemandaItemUC.AtendimentoDemandaItemDTO atendimento = iniciarAtendimentoDemandaItemUC
                .executar(new IniciarAtendimentoDemandaItemUC.NovoAtendimentoDemandaItem(
                        authenticationProvider.authenticatedUserId(),
                        beneficiarioId,
                        demandaItemId,
                        quantidadeAtendimento));

        return ResponseEntity
                .created(URI.create(String.format("beneficiarios/%s/demandas-itens/%s/atendimentos/%s",
                        beneficiarioId.toString(),
                        demandaItemId.toString(),
                        atendimento)))
                .body(atendimento);

    }
}
