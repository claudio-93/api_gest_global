package com.gestao.mimoso.model;

import com.gestao.mimoso.service.UserServiceImplement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Logger;

@Entity
//@Table(name = "cargo")
@Data @NoArgsConstructor @AllArgsConstructor
public class Role implements Serializable{

    //private static final Logger logger	= LogManager.getLogger(claudio());
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //@Column(name = "id")
    private Long id;
    private String name;
    private LocalDate registrarData;
}

