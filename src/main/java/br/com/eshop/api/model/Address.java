package br.com.eshop.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "o nome da rua deve ter mais de 4 caracteres")
    private String street;

    @NotBlank
    @Size(min = 5, message = "o nome da moradia deve ter mais de 4 caracteres")
    private String buildingName;

    @NotBlank
    @Size(min = 4, message = "o nome da cidade deve ter mais de 4 caracteres")
    private String city;

    @NotBlank
    @Size(min = 2, message = "o nome do estado deve ter mais de 4 caracteres")
    private String state;

    @NotBlank
    @Size(min = 2, message = "o nome da cidade deve ter mais de 4 caracteres")
    private String country;

    @NotBlank
    @Size(min = 6, message = "o cep deve ter mais de 6 caracteres")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String pincode, String country, String city, String state, String buildingName, String street) {
        this.pincode = pincode;
        this.country = country;
        this.city = city;
        this.state = state;
        this.buildingName = buildingName;
        this.street = street;
    }
}
