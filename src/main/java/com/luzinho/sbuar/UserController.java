package com.luzinho.sbuar;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("chapter4")
public class UserController {

    UserRepository userRepository;
//    List<Usuario> listUsers = new ArrayList<>();
    
    public UserController(UserRepository userRepository){
//        this.listUsers.addAll(List.of(
//                new Usuario("Luis"),
//                new Usuario("Jorge"),
//                new Usuario("David"),
//                new Usuario("Dalgwis")
//        ));
        this.userRepository = userRepository;
    }
    @GetMapping
    ResponseEntity<String> getStatus(){
        return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
    }
    
    @GetMapping("users")
    ResponseEntity<Iterable<Usuario>> getUsers() {
        Iterable<Usuario> users = this.userRepository.findAll();
        return (users.iterator().hasNext()) ? new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("users")
    ResponseEntity<Usuario> createUser(@RequestBody Usuario user){
        try {
            Usuario newUser = new Usuario(user);
            this.userRepository.save(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("users/{id}")
    ResponseEntity<Usuario> updateUser(@RequestBody Usuario user, @PathVariable String id){
        try {
            Optional<Usuario> userFound = this.userRepository.findById(id);
            HttpStatus status = (userFound.isEmpty()) ? HttpStatus.CREATED : HttpStatus.OK;

            this.userRepository.save(user);
            return new ResponseEntity<>(user, status);

        } catch (Exception e) {
            System.err.println(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("users/{id}")
    ResponseEntity<String> deleteUser(@PathVariable String id){
        Optional<Usuario> userFound = this.userRepository.findById(id);
        if (userFound.isEmpty()) return new ResponseEntity<>("id No Encontrado", HttpStatus.NOT_FOUND);

        this.userRepository.delete(userFound.get());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}

@Entity
@NoArgsConstructor
class Usuario{
    @Id
    @Getter @Setter
    String id;

    @Getter @Setter
    String name;

    Usuario(String id, String name){
        this.id = id;
        this.name = name;
    }

    public Usuario(String name){
        this(UUID.randomUUID().toString(), name);
    }

    public Usuario(Usuario user){
        this.id = UUID.randomUUID().toString();
        this.name = user.getName();
    }
}
