package tests;

import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;


public class APItests {

    @Test
    void countPostsPerUser() {
        countNumberOfPostsForUsers();
    }

    public void countNumberOfPostsForUsers() {
        List postsResponse = RestAssured.get("https://jsonplaceholder.typicode.com/posts").as(List.class);
        int numberOfPosts = 0;
        int userNumber = 1;
        String numberOfPostsPerUser = null;
        List<String> results = new ArrayList<>();

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
