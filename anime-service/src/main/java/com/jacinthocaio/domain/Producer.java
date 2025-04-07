package com.jacinthocaio.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Producer {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("full_name")

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,insertable = false,updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime createdAt;

}

