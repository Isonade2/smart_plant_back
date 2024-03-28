package wku.smartplant.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;
    private String specify;

    public Address() {
    }

    public Address(String city, String street, String zipcode, String specify) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.specify = specify;
    }
}
