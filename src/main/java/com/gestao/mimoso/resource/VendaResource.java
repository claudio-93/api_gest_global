package com.gestao.mimoso.resource;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.gestao.mimoso.model.Role;
import com.gestao.mimoso.model.User;
import com.gestao.mimoso.model.Venda;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping(value ="/api/venda") @RequiredArgsConstructor
public class VendaResource {

    /*
    @PostMapping("/getAll")
    public ResponseEntity<Role> getVenda(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(u);
    }

    @PostMapping("/save")
    public ResponseEntity<Role> saveVenda(@RequestBody @Valid Venda venda, @RequestHeader Authentication token){
        var vendas = new Venda();
        BeanUtils.copyProperties(venda, vendas);
        vendas.setRegistrarData(LocalDate.from(LocalDateTime.now(ZoneId.of("UTC"))));

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body();
    }

    @PostMapping("/update")
    public ResponseEntity<Role> updateVenda(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body();
    }

    @PostMapping("/delete")
    public ResponseEntity<Role> deleteVenda(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body();
    }

     */
}
