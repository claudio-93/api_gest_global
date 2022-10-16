package com.gestao.mimoso.resource;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestao.mimoso.Excepction.ResourceNotFoundException;
import com.gestao.mimoso.model.Role;
import com.gestao.mimoso.model.User;
import com.gestao.mimoso.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
@RequestMapping(value ="/api") @RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageble){
        return ResponseEntity.ok().body(userService.getUsers(pageble));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id){
        Optional<User> getUserOptional = userService.findByOneUserBoolean(id);
        if (!getUserOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(getUserOptional.get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable(value ="id") Long id){
        Optional<User> getUserOptional = userService.findByOneUserBoolean(id);
                //.orElseThrow(()-> new ResourceNotFoundException("Formando", "id", id));
        if (!getUserOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado");
        }
        if (getUserOptional.isEmpty()){
            HttpServlet servelet;
        }
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("sucessfull");
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<Object> deleteByUser(@PathVariable Long id){
        Optional<User> getUserOptional = userService.findByOneUserBoolean(id);
        if (!getUserOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado");
        }
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("sucessfull");
    }

    @GetMapping("/delete/userAll")
    public ResponseEntity<Object> deleteByUserAll(Pageable pageble){
        Page<User> getUserOptional = userService.getUsers(pageble);
        if (getUserOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado");
        }
        userService.deleteAllUser();
        return ResponseEntity.status(HttpStatus.OK).body("Utilizadores Eliminados com sucesso");
    }

    @PutMapping("/update/user")
    public ResponseEntity<Object> updateUser(@PathVariable User user){
        Optional<User> getUserOptional = userService.findByOneUserBoolean(user.getId());
        if (!getUserOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado");
        }
        var use = getUserOptional.get();
        BeanUtils.copyProperties(user, use);
        use.setId(getUserOptional.get().getId());
        use.setRegistrarData(getUserOptional.get().getRegistrarData());
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(use));
    }

    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUsers(@Valid @RequestBody User user){
        if (userService.existsByUsername(user.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }


    @PostMapping("/role/addtouser")
    public ResponseEntity<Role> addRoleUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String token  = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String refresh_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                //Para receber as requisições via JSON
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", refresh_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch (Exception exception){
                //response.setHeader("error", exception.getMessage());
                //response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.Value());
                Map<String, String > error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

@Data
class RoleToUserForm{
    private String userName;
    private String roleName;
}