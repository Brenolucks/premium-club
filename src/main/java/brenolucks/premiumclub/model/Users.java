package brenolucks.premiumclub.model;

import brenolucks.premiumclub.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tb_users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private int phoneNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @OneToOne(mappedBy = "usersID")
    private Subscription subscription;
}
