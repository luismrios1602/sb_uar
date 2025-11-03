package com.luzinho.sbuar;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RestApiController {

    List<Coffee> listCoffees = new ArrayList(List.of(
            new Coffee("Café Especial"),
            new Coffee("Café Americano"),
            new Coffee("Café Expreso Brasilia"))
    );

    @GetMapping
    String getStatus(){
        return "Success";
    }

    @GetMapping("status")
    ResponseEntity<String> getStatusResp(){
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
    }

    @GetMapping("coffee")
    ResponseEntity<Iterable<Coffee>> getCoffees(){
        return new ResponseEntity<>(this.listCoffees, HttpStatus.FOUND);
    }

    @PostMapping("coffee")
    ResponseEntity<Coffee> createCoffee(@RequestBody Coffee coffee){
        Coffee newCoffee = new Coffee(coffee.getName());
        this.listCoffees.add(newCoffee);
        return new ResponseEntity(newCoffee, HttpStatus.CREATED);
    }

    @PutMapping("coffee/{id}")
    ResponseEntity<Coffee> updateCoffee(@RequestBody Coffee coffee, @PathVariable String id){
        Integer index = this.listCoffees.indexOf(coffee);
        if (index > -1){
            listCoffees.get(index).setName(coffee.getName());
            return new ResponseEntity<>(coffee, HttpStatus.OK);
        }

        //If we are here is 'cause that iteration doesn't match any coffee, so we create a new Coffee
        this.listCoffees.add(coffee);
        return new ResponseEntity<>(coffee, HttpStatus.CREATED);
    }

}

@NoArgsConstructor
class Coffee {
    @Getter @Setter
    String id;
    @Getter @Setter
    String name;

    public Coffee(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Coffee(String name) {
        this(UUID.randomUUID().toString(), name);
    }
}
