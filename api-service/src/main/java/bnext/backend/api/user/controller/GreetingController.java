package bnext.backend.api.user.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/greet")
// Classe che gestisce la home con i saluti in base ai ruoli assegnati
public class GreetingController {

    // utile per l'autenticazione a due fattori
    /*@GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }*/

    @GetMapping("/")
    public @NotNull String home() {
        return ("<h1>Welcome</h1>");
    }

    // Hanno l'autorizzazione gli utenti che sono user e admin
    // (quindi più è alto è il livello più operazioni possono fare)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<String> greetingUser() {
        return new ResponseEntity<String>("Welcome, you have USER role", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> greetingAdmin() {
        return new ResponseEntity<String>("Welcome, you have ADMIN role", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/userOrAdmin")
    public ResponseEntity<String> greetingUserOrAdmin() {
        return new ResponseEntity<String>("Welcome, you have USER or ADMIN role", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ANONYMOUS')")
    @GetMapping("/anonymous")
    public ResponseEntity<String> greetingAnonymous() {
        return new ResponseEntity<String>("Welcome, you have ANONYMOUS role", HttpStatus.OK);
    }

}
