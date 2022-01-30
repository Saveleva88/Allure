package ru.netology.delivery.data;

import lombok.Data;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Data
public class RegistrationByCardInfo {
    private final String city;
    private final String name;
    private final String phone;
}