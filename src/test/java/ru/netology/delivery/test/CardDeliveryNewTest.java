package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.netology.delivery.data.DataGenerator;
import ru.netology.delivery.data.RegistrationByCardInfo;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;


public class CardDeliveryNewTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @Test
    void shouldRegisterByAccountNumber() {
        open("http://localhost:9999");

        RegistrationByCardInfo firstSending = DataGenerator.Registration.generateByCard("ru");

        String firstDate = generateDate(7);
        String secondDate = generateDate(11);

        // firstSending

        $("[placeholder='Город']").setValue(firstSending.getCity());
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[name='name']").setValue(firstSending.getName());
        $("[name='phone']").setValue(firstSending.getPhone());
        $("[data-test-id='agreement']").click();
        $$(".button__text").find(exactText("Запланировать")).click();

        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstDate), Duration.ofSeconds(15));

        //secondSending

        $("[placeholder='Город']").sendKeys(Keys.CONTROL + "a");
        $("[placeholder='Город']").sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(BACK_SPACE);
        $("[name='name']").sendKeys(Keys.CONTROL + "a");
        $("[name='name']").sendKeys(Keys.DELETE);
        $("[name='phone']").sendKeys(Keys.CONTROL + "a");
        $("[name='phone']").sendKeys(Keys.DELETE);
        $("[data-test-id='agreement']").click();

        $("[placeholder='Город']").setValue(firstSending.getCity());
        $("[data-test-id='date'] input").setValue(secondDate);
        $("[name='name']").setValue(firstSending.getName());
        $("[name='phone']").setValue(firstSending.getPhone());
        $("[data-test-id='agreement']").click();
        $$(".button__text").find(exactText("Запланировать")).click();

        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(visible).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
    }

    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
