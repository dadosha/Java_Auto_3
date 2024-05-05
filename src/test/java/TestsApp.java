import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestsApp {

    @Test
    void FirstTest() {
        open("http://localhost:9999/");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void PositiveTests() {
        open("http://localhost:9999/");
        $(".form_theme_alfa-on-white input[name=\"name\"]").setValue("Койфман Станислав-Богданович");
        $(".form_theme_alfa-on-white input[name=\"phone\"]").setValue("+79999999999");
        $(".form_theme_alfa-on-white label[data-test-id=\"agreement\"]").click();
        $(".form_theme_alfa-on-white button[type=\"button\"]").click();
        $("[data-test-id=\"order-success\"]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @ParameterizedTest
    @CsvSource({
            "zxcvzvvc",
            "!@#$%^",
    })
    void NegativeTestFieldNameNegative(String name) {
        open("http://localhost:9999/");
        $(".form_theme_alfa-on-white input[name=\"name\"]").setValue(name);
        $(".form_theme_alfa-on-white input[name=\"phone\"]").setValue("+79999999999");
        $(".form_theme_alfa-on-white label[data-test-id=\"agreement\"]").click();
        $(".form_theme_alfa-on-white button[type=\"button\"]").click();
        $(".form_theme_alfa-on-white span[data-test-id=\"name\"] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void NegativeTestFieldNameEmpty() {
        open("http://localhost:9999/");
        $(".form_theme_alfa-on-white input[name=\"phone\"]").setValue("+79999999999");
        $(".form_theme_alfa-on-white label[data-test-id=\"agreement\"]").click();
        $(".form_theme_alfa-on-white button[type=\"button\"]").click();
        $(".form_theme_alfa-on-white span[data-test-id=\"name\"] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvSource({
            "+7999999999",
            "+799999999999",
            "79999999999",
    })
    void NegativeTestsWithPhone(String phone) {
        open("http://localhost:9999/");
        $(".form_theme_alfa-on-white input[name=\"name\"]").setValue("Койфман Станислав Богданович");
        $(".form_theme_alfa-on-white input[name=\"phone\"]").setValue(phone);
        $(".form_theme_alfa-on-white label[data-test-id=\"agreement\"]").click();
        $(".form_theme_alfa-on-white button[type=\"button\"]").click();
        $(".form_theme_alfa-on-white span[data-test-id=\"phone\"] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void NegativeTestFieldPhoneEmpty() {
        open("http://localhost:9999/");
        $(".form_theme_alfa-on-white input[name=\"name\"]").setValue("Койфман Станислав Богданович");
        $(".form_theme_alfa-on-white label[data-test-id=\"agreement\"]").click();
        $(".form_theme_alfa-on-white button[type=\"button\"]").click();
        $(".form_theme_alfa-on-white span[data-test-id=\"phone\"] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void NegativeTestNotCheckbox() {
        open("http://localhost:9999/");
        $(".form_theme_alfa-on-white input[name=\"name\"]").setValue("Койфман Станислав Богданович");
        $(".form_theme_alfa-on-white input[name=\"phone\"]").setValue("+79999999999");
        $(".form_theme_alfa-on-white button[type=\"button\"]").click();
        $(".form_theme_alfa-on-white label.input_invalid").click();
    }
}
