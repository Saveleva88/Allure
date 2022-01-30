package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import ru.netology.delivery.data.RegistrationByCardInfo;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Locale;

@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class Registration {

        public RegistrationByCardInfo generateByCard(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationByCardInfo(
                    faker.address().city(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber()
            );
        }
    }
    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
