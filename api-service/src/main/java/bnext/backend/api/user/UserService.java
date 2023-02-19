package bnext.backend.api.user;


import bnext.backend.api.security.JWT.JwtUtil;
import bnext.backend.api.security.exceptions.DisabledUserException;
import bnext.backend.api.security.exceptions.InvalidUserCredentialsException;
import bnext.backend.api.security.vo.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public @NotNull List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        if (users.isEmpty())
            System.out.println("There's no user to show, the list is empty");
        return users;
    }

    public @Nullable User getUserById(@NotNull String userId) {
        HttpServletResponse response = null;
        if (userRepository.findById(UUID.fromString(userId)).isPresent()) {
            Optional<User> userRepo = userRepository.findById(UUID.fromString(userId));
            return userRepo.get();

        } else {
            System.out.println("Users does not exist");
            return null;

        }

    }

    public @Nullable User getUserByUsername(String username) {
        //System.out.println("Ecco l'username in input "+username);
        if (userRepository.findByUsername(username).isPresent()) {
            Optional<User> userRepo = userRepository.findByUsername(username);
            return userRepo.get();
            //return userRepository.findByUsername(username).get();
        } else {
            System.out.println("Users does not exist");
            return null;
        }

        /*return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> user.getName().equals(username))
                .findFirst()
                .orElse( new User());*/
    }

    public @NotNull ResponseEntity<String> deleteUser(@NotNull String userID) {

        if (userRepository.existsById(UUID.fromString(
                userID))) {

            userRepository.deleteById(UUID.fromString(userID));
            return new ResponseEntity<String>("Deleted user " + userID, HttpStatus.OK);

        } else return new ResponseEntity<String>("User " + userID + " does not exist", HttpStatus.OK);

    }

    public ResponseEntity<Response> signIn(User user) {
        //System.out.println("**************Entrato in metodo signin del service**************");
        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (DisabledException e) {
            throw new DisabledUserException("User Inactive");
        } catch (BadCredentialsException e) {
            throw new InvalidUserCredentialsException("Invalid Credentials");
        }
        User obtainedUser = userRepository.findByUsername(user.getUsername()).get();
        String token = jwtUtil.generateToken(authentication);

        Response response = new Response();
        response.setToken(token);

        response.setRoles(Collections.singletonList(obtainedUser.getRoles()));
        response.setUser(obtainedUser);
        //System.out.println("ecco user "+user.toString());
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> signUp(User user) {
        if (!userRepository.findByUsername(user.getUsername()).isEmpty()) {
            throw new RuntimeException("User already exists");
        }
        User newUser = User.builder()
                    .active(1)
                    .birthDate(user.getBirthDate())
                    .name(user.getName())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .permissions(user.getPermissions())
                    .surname(user.getSurname())
                    .username(user.getUsername())
                    .roles(user.getRoles())
                    .build();

        userRepository.save(newUser);
        return new ResponseEntity<String>("User successfully registered", HttpStatus.OK);
    }

    public String updateUser(User user) {
        // verifico che esista la macchina con l'id indicato
        if (userRepository.findById(user.getUserId()).isEmpty())
            return "No user with this id in the database";
        else {

            User storedUser = userRepository.findById(user.getUserId()).get();
            ReflectionUtils.doWithFields(user.getClass(), field -> {
                        field.setAccessible(true);
                        // il controllo sulla lista evita di assegnare una nuova lista se quella passata è vuota
                        // Le liste sono gestite dopo
                        if (field.get(user) != null && !field.getName().equals("id") && !(field.get(user) instanceof List)) {


                            // Se il field non è null allora accedo ai field della macchina salvata
                            ReflectionUtils.doWithFields(storedUser.getClass(),
                                    old_field -> {
                                        old_field.setAccessible(true);
                                        // se è lo stesso campo allora lo aggiorno
                                        if (old_field.getName().equals(field.getName())) {
                                            //System.out.println("Updating field " + old_field.getName() + ": " + field.get(user));
                                            old_field.set(storedUser, field.get(user));

                                        }


                                    });

                        }

                        // gestione liste politica ADD ALL
                    /*
                    storedCar.getFeedback().addAll(car.getFeedback());
                    storedCar.getReservation().addAll(car.getReservation());
                    */

                    }
            );

            //System.out.println("Ecco l'utente alla fine " + storedUser);
            //questo metodo basta gia ad aggiornare la macchina
            userRepository.save(storedUser);
            return "USER SUCCESSFULLY UPDATED";
        }
    }
    /*
    public List<Reservation> findByCarOwnerId(UUID userID) {
        User user = this.getUserById(userID.toString());
        if (user != null){

        }
        else{}
    }
     */
}
