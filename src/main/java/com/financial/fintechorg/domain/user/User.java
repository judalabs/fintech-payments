package com.financial.fintechorg.domain.user;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.GenericGenerator;

import com.financial.fintechorg.exception.InvalidSenderException;
import com.financial.fintechorg.exception.NoBalanceAvailableException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
@Builder
public class User {

    @Id
    @Column(name = "uuid")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;

    @Column(name = "email", unique = true, nullable = false)
    @Email(regexp = ".+@.+\\..+", message = "incorrect email")
    private String email;

    @Column(name = "document", unique = true, nullable = false)
    private String document;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Setter(value= AccessLevel.NONE)
    @Column(name = "balance")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public void sendTo(User receiver, BigDecimal amount) {
        User sender = this;
        if(sender.getUserType() == UserType.MERCHANT) {
            throw new InvalidSenderException(sender.getUuid());
        }

        if(amount.compareTo(sender.getBalance()) > 0) {
            throw new NoBalanceAvailableException();
        }
        sender.subtractAmount(amount);
        receiver.addAmount(amount);
    }

    public void addAmount(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void subtractAmount(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}
