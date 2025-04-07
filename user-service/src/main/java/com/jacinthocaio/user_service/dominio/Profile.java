package com.jacinthocaio.user_service.dominio;

import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@With
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}
