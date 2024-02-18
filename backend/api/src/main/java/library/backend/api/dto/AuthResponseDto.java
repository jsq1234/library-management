package library.backend.api.dto;

import library.backend.api.utils.AuthStatus;

public record AuthResponseDto(String token, AuthStatus authStatus) {

}
