package com.phonebook.restAssuredTests;

import com.phonbook.dto.ContactDto;
import com.phonbook.dto.MessageDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactTests extends TestBase{

    String id;

    @BeforeMethod
    public void precondition() {
        ContactDto contactDto = ContactDto.builder()
                .name("Oliver")
                .lastName("Kan")
                .phone("1234567890")
                .email("kan@gm.com")
                .address("Berlin")
                .description("goalkeeper")
                .build();

        String message = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(contactDto)
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("Contact was added!"))
                .extract().path("message");

        String[] all = message.split(": ");
        id = all[1];
    }

    @Test
    public void deleteByIDSuccessTest() {

        // MessageDto messageDto =
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message",equalTo("Contact was deleted!"))
                .extract().response().as(MessageDto.class);
        // System.out.println(messageDto);
    }
}
