package com.luzinho.sbuar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("")
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
  
    @GetMapping("chapter4/user")
    ResponseEntity<String> getStatus(){
        return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
    }
    
    @GetMapping("chapter4/users")
    ResponseEntity<Iterable<Usuario>> getUsers() {
        Iterable<Usuario> users = this.userRepository.findAll();
        return (users.iterator().hasNext()) ? new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("chapter4/users")
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

    @PutMapping("chapter4/users/{id}")
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

    @DeleteMapping("chapter4/users/{id}")
    ResponseEntity<String> deleteUser(@PathVariable String id){
        Optional<Usuario> userFound = this.userRepository.findById(id);
        if (userFound.isEmpty()) return new ResponseEntity<>("id No Encontrado", HttpStatus.NOT_FOUND);

        this.userRepository.delete(userFound.get());
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

  // CHAPTER 5
    @Value("${user_name: Luis}")
    String nameValue;
    @Value("${user_lastName: Rios}")
    String lastNameValue;
    @Value("${user_fullName: ${user_name} ${user_lastName} } ")
    String fullName;

    final UserConfigProps userConfigProps;
    final UserBean userBean;

    UserController(UserConfigProps userProps, UserBean userBean){
        this.userConfigProps = userProps;
        this.userBean = userBean;
    }

    @GetMapping("chapter5/user/nameValue")
    String getNameValue(){
        return nameValue;
    }

    @GetMapping("chapter5/user/lastNameValue")
    String getLastNameValue(){
        return lastNameValue;
    }

    @GetMapping("chapter5/user/fullNameValue")
    String getFullName(){
        return fullName;
    }

    @GetMapping("chapter5/user/nameProp")
    String getNameProp(){
        return userConfigProps.getName();
    }

    @GetMapping("chapter5/user/lastNameProp")
    String getLastNameProp(){
        return userConfigProps.getLastName();
    }

    @GetMapping("chapter5/user/fullNameProp")
    String getFullNameProp(){
        return userConfigProps.getFullName();
    }

    @GetMapping("chapter5/user/fullNameBean")
    String getFullNameBean(){
        return userBean.getFullName();
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
