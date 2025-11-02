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
@RequestMapping("/chapter3")
public class RestApiController {

    List<Coffee> listCoffees = new ArrayList<>();

    public RestApiController(){
        this.listCoffees.addAll(List.of(
                new Coffee("Café Especial"),
                new Coffee("Café Americano"),
                new Coffee("Café Expreso Brasilia")
                ));
    }

    @GetMapping
    String getStatus(){
        return "Success";
    }

    @GetMapping("status")
    ResponseEntity<String> getStatusResp(){
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
    }

    @GetMapping("coffees")
    ResponseEntity<Iterable<Coffee>> getCoffees(){
        return new ResponseEntity<>(this.listCoffees, HttpStatus.FOUND);
    }

    @PostMapping("coffees")
    ResponseEntity<Coffee> createCoffee(@RequestBody Coffee coffee){
        this.listCoffees.add(coffee);
        return new ResponseEntity<Coffee>(coffee, HttpStatus.CREATED);
    }

    @PutMapping("coffees/{id}")
    ResponseEntity<Coffee> updateCoffee(@RequestBody Coffee coffee, @PathVariable String id){
        int index = -1;
        for (Coffee c : listCoffees){
            if (c.getId().equals(id)){
                index = this.listCoffees.indexOf(c);
                this.listCoffees.set(index, coffee);
            }
        }

        return (index == -1) ? this.createCoffee(coffee) : new ResponseEntity<>(coffee, HttpStatus.OK);
    }

    @DeleteMapping("coffees/{id}")
    ResponseEntity<String> deleteCoffee(@PathVariable String id){
        int index = -1;
        for (Coffee c : listCoffees) {
            if (c.getId().equals(id)) {
                index = this.listCoffees.indexOf(c);
                listCoffees.remove(index);
            }
        }

        return (index == -1 ) ? new ResponseEntity<>("Don't deleted", HttpStatus.NOT_FOUND) : new ResponseEntity<>("success", HttpStatus.OK);
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
