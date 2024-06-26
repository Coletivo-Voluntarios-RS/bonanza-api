package ong.bonanza.api.adapter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ong.bonanza.api.adapter.provider.AuthenticationProvider;
import ong.bonanza.api.application.service.AuthService;

@RequiredArgsConstructor
@Service
public class JwtAuthService implements AuthService {

    private final AuthenticationProvider authenticationProvider;

    @Override
    public boolean possuiAlgumaRole(String... roles) {
        return List.of(roles).stream().anyMatch(authenticationProvider::hasRole);
    }

    @Override
    public UUID idUsuarioAutenticado() {
        return authenticationProvider.authenticatedUserId();
    }

}
