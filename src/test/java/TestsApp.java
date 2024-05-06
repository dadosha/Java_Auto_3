import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestsApp {

    @Test
    void positiveTests() {
        open("http://localhost:9999/");
        $("input[name=\"name\"]").setValue("Койфман Станислав-Богданович");
        $("input[name=\"phone\"]").setValue("+79999999999");
        $("label[data-test-id=\"agreement\"]").click();
        $("button[type=\"button\"]").click();
        $("[data-test-id=\"order-success\"]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @ParameterizedTest
    @CsvSource({
            "zxcvzvvc",
            "!@#$%^",
    })
    void negativeTestFieldNameNegative(String name) {
        open("http://localhost:9999/");
        $("input[name=\"name\"]").setValue(name);
        $("input[name=\"phone\"]").setValue("+79999999999");
        $("label[data-test-id=\"agreement\"]").click();
        $("button[type=\"button\"]").click();
        $("span[data-test-id=\"name\"].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void negativeTestFieldNameEmpty() {
        open("http://localhost:9999/");
        $("input[name=\"phone\"]").setValue("+79999999999");
        $("label[data-test-id=\"agreement\"]").click();
        $("button[type=\"button\"]").click();
        $("span[data-test-id=\"name\"].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvSource({
            "+7999999999",
            "+799999999999",
            "79999999999",
    })
    void negativeTestsWithPhone(String phone) {
        open("http://localhost:9999/");
        $("input[name=\"name\"]").setValue("Койфман Станислав Богданович");
        $("input[name=\"phone\"]").setValue(phone);
        $("label[data-test-id=\"agreement\"]").click();
        $("button[type=\"button\"]").click();
        $("span[data-test-id=\"phone\"].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void negativeTestFieldPhoneEmpty() {
        open("http://localhost:9999/");
        $("input[name=\"name\"]").setValue("Койфман Станислав Богданович");
        $("label[data-test-id=\"agreement\"]").click();
        $("button[type=\"button\"]").click();
        $("span[data-test-id=\"phone\"].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void negativeTestNotCheckbox() {
        open("http://localhost:9999/");
        $("input[name=\"name\"]").setValue("Койфман Станислав Богданович");
        $("input[name=\"phone\"]").setValue("+79999999999");
        $("button[type=\"button\"]").click();
        $("label[data-test-id=\"agreement\"]").shouldHave(Condition.cssClass("input_invalid"));
    }
}
