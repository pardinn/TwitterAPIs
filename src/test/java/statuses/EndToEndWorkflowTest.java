package statuses;

import common.RestUtilities;
import constants.EndPoints;
import constants.Path;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matcher;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class EndToEndWorkflowTest {

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    private String tweetId="";

    @BeforeClass
    public void setUp() {
        reqSpec = RestUtilities.getRequestSpecification();
        reqSpec.basePath(Path.STATUSES);

        resSpec = RestUtilities.getResponseSpecification();
    }

    @Test
    public void postTweet(){
        Response response =
                given()
                        .spec(RestUtilities.
                                createQueryParam(reqSpec,"status", "Tweet generated using RestAssured API with Java"))
                .when()
                        .post(EndPoints.STATUSES_TWEET_POST)
                .then()
                        .spec(resSpec)
                        .extract().response();

        JsonPath jsPath = RestUtilities.getJsonPath(response);
        tweetId = jsPath.get("id_str");
        System.out.println("The tweet id is: " + tweetId);
    }

    @Test(dependsOnMethods = {"postTweet"})
    public void readTweet(){
        RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_READ_SINGLE);
        Response response = RestUtilities.getResponse(
                RestUtilities.createQueryParam(reqSpec,"id",tweetId), "get");
        String text = response.path("text");
        System.out.println("The tweet text is: " + text);
    }

    @Test(dependsOnMethods = {"readTweet"})
    public void deleteTweet(){
        given()
                .spec(RestUtilities.createPathParam(reqSpec,"id", tweetId))
        .when()
                .post(EndPoints.STATUSES_TWEET_DESTROY)
        .then()
                .spec(resSpec);
    }
}
