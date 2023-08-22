package com.financial.fintechorg.infra;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.financial.fintechorg.domain.user.User;
import com.financial.fintechorg.dto.NotificationDTO;
import com.financial.fintechorg.exception.NotificationException;
import com.financial.fintechorg.service.NotifiableTransaction;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultNotificationService implements NotifiableTransaction {

    private static final String LINK = "http://o4d9z.mocklab.io/notify";

    @Qualifier("timeout")
    private final RestTemplate restTemplate;

    @Override
    public void sendNotification(User user, String message) throws ResourceAccessException {
        final NotificationDTO notificationRequest = new NotificationDTO(user.getEmail(), message);
        final ResponseEntity<String> response = restTemplate.postForEntity(LINK, notificationRequest, String.class);
        if(response.getStatusCode() != HttpStatus.OK)
            throw new NotificationException(response.toString());
    }
}
