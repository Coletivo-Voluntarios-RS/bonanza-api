package ong.bonanza.beneficiarioapi.domain.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ong.bonanza.beneficiarioapi.domain.entity.Beneficiario;
import ong.bonanza.beneficiarioapi.domain.repository.BeneficiarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BeneficiarioService {

    private final BeneficiarioRepository beneficiarioRepository;

    public List<Beneficiario> buscarTodosPaginado(int page, int size) {
        return beneficiarioRepository.findAll(
                PageRequest.of(
                        page,
                        size,
                        Sort.by("createdAt").descending()))
                .toList();
    }

}