package com.gestao.mimoso.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    @Column(name = "id", nullable = false)
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) //@Column(name = "id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private LocalDate registrarData;
}
