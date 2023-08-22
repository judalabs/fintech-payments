package com.financial.fintechorg.infra;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.financial.fintechorg.domain.user.User;
import com.financial.fintechorg.exception.TransactionAuthorizationException;
import com.financial.fintechorg.service.AuthorizableTransaction;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultTransactionAuthorization implements AuthorizableTransaction {

    private static final String LINK = "https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6";
    private final RestTemplate restTemplate;

    @Override
    public boolean authorize(User send, BigDecimal value) {
        final ParameterizedTypeReference<Map<String, String>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};
        final ResponseEntity<Map<String, String>> authorizationResponse = restTemplate.exchange(
                LINK, HttpMethod.GET,null, parameterizedTypeReference);

        if(authorizationResponse.getStatusCode() == HttpStatus.OK && authorizationResponse.getBody() != null) {
            return "Autorizado".equals( authorizationResponse.getBody().get("message"));
        } else {
            throw new TransactionAuthorizationException(authorizationResponse.toString());
        }
    }
}
