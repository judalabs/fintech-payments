package com.financial.fintechorg.service.impl;

import static com.financial.fintechorg.helper.UUIDHelper.defaultUuid;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.financial.fintechorg.domain.user.User;
import com.financial.fintechorg.domain.user.UserType;
import com.financial.fintechorg.dto.UserDTO;
import com.financial.fintechorg.exception.ResourceNotFoundException;
import com.financial.fintechorg.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl sut;
    @Test
    void shouldReturnBalanceFromUser() {
        when(userRepository.findByUuid(defaultUuid)).thenReturn(stubUser());
        Assertions.assertThat(BigDecimal.valueOf(123)).isEqualByComparingTo(sut.getBalance(defaultUuid));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserNotFound() {
        Assertions.assertThatCode(() -> sut.getBalance(defaultUuid))
                .isExactlyInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldSaveUser() {
        final UserDTO userDTO = new UserDTO("email@abc", "doc1", "firstName", "lastName", UserType.MERCHANT);

        when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        final User save = sut.save(userDTO);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(save.getFirstName()).isEqualTo("firstName"),
                () -> Assertions.assertThat(save.getLastName()).isEqualTo("lastName"),
                () -> Assertions.assertThat(save.getEmail()).isEqualTo("email@abc"),
                () -> Assertions.assertThat(save.getUserType()).isEqualTo(UserType.MERCHANT),
                () -> Assertions.assertThat(save.getDocument()).isEqualTo("doc1")
        );
    }

    private static Optional<User> stubUser() {
        return Optional.of(User.builder()
                .balance(BigDecimal.valueOf(123l))
                .build());
    }
}