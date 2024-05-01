package wku.smartplant.dto.order;

import lombok.Data;

@Data
public class AddressDTO {
    private String city;
    private String street;
    private String zipcode;
    private String specify;
}
