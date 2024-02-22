package com.swiggy.wallet.requestModels;

import com.swiggy.wallet.entities.Country;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@AllArgsConstructor
public class UserRequestModel {

    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Country country;

}
