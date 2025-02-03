package dev.davletshin.deal.domain.client;


import dev.davletshin.deal.domain.enums.Gender;
import dev.davletshin.deal.domain.enums.MaritalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@Builder
public class Client {

    @Id
    @Column(name = "client_id_uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "birth_date")
    protected LocalDate birthdate;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private int dependentAmount;
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "passport_id")
    @JdbcTypeCode(SqlTypes.JSON)
    private Passport passport;

    @Column(name = "employment_id")
    @JdbcTypeCode(SqlTypes.JSON)
    private Employment employment;
}
