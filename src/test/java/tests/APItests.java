package tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import java.util.*;

import static io.restassured.RestAssured.given;


public class APItests {

    @Test
    void countPostsPerUser() {
        countNumberOfPostsForUsers();
    }

    //obtains all post ids and checks for duplicate values
    @Test
    public void verifyThatThereAreNoDuplicatePostIds() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());

        JsonPath jsonPath = new JsonPath(response.asString());
        List<Integer> postIds = jsonPath.getList("id");
        System.out.println(checkForDuplicateIds(postIds));

    }

    //saves duplicate values to a list and outputs them if there are any
    private <T> Set<T> checkForDuplicateIds(Collection<T> collection) {
        Set<T> duplicates = new LinkedHashSet<>();
        Set<T> uniques = new HashSet<>();

        for(T t : collection) {
            if(!uniques.add(t)) {
                duplicates.add(t);
                System.out.println("Duplicate id found: " + t);
            }
        }
        if (duplicates.isEmpty()) {
            System.out.println("No duplicate ids found");
        }
        return duplicates;
    }

    //outputs the number of posts per user, based on the userId
    public void countNumberOfPostsForUsers() {
        List postsResponse = RestAssured.get("https://jsonplaceholder.typicode.com/posts").as(List.class);

        int numberOfPosts = 0;
        int userNumber = 1;
        String numberOfPostsPerUser;
        List<String> results = new ArrayList<>();

//Iterates through the response and counts the number of posts for each userId
        for (int i = 1; i <= 10; i++) {
            for (Object postObject : postsResponse) {
                if (postObject.toString().contains("userId=" + i)) {
                    userNumber = i;
                    numberOfPosts++;
                }
            }
            numberOfPostsPerUser = "(" + userNumber + "," + numberOfPosts + ")";
            results.add(numberOfPostsPerUser);
            numberOfPosts = 0;
        }
        System.out.println(results);
    }

}
