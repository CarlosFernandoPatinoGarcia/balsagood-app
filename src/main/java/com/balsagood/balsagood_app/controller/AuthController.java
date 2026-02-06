package com.balsagood.balsagood_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balsagood.balsagood_app.dto.LoginRequestDTO;
import com.balsagood.balsagood_app.model.Usuario;
import com.balsagood.balsagood_app.config.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private com.balsagood.balsagood_app.repository.UsuarioRepository usuarioRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsuarioNombre(), request.getUsuarioClave()));

        String token = jwtService.generateToken(request.getUsuarioNombre());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody com.balsagood.balsagood_app.dto.RegisterRequestDTO request) {
        // 1. Verificar que el usuario exista
        if (usuarioRepository.findByUsuarioNombre(request.getUsuarioNombre()).isPresent()) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
        }

        // 2. Crear un usuario
        Usuario user = new Usuario();
        user.setUsuarioNombre(request.getUsuarioNombre());
        user.setUsuarioClave(passwordEncoder.encode(request.getUsuarioClave()));
        user.setUsuarioEstado('A'); // Active by default

        usuarioRepository.save(user);

        // 3. Generar el token
        String token = jwtService.generateToken(request.getUsuarioNombre());

        return ResponseEntity.ok(token);
    }
}
