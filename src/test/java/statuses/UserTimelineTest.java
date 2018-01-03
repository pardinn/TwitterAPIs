package statuses;

import common.RestUtilities;
import constants.EndPoints;
import constants.Path;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class UserTimelineTest {

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;

    @BeforeClass
    public void setUp() {
        reqSpec = RestUtilities.getRequestSpecification();
        reqSpec.queryParam("screen_name","apiautomation");
        reqSpec.basePath(Path.STATUSES);

        resSpec = RestUtilities.getResponseSpecification();
    }

    @Test
    public void readTweets() {
        given()
                .spec(reqSpec)
        .when()
                .get(EndPoints.STATUSES_USER_TIMELINE)
        .then()
//                .log().all()
                .spec(resSpec)
                .body("user.screen_name", hasItem("apiautomation"));
    }

    @Test
    public void readTweetsWithQueryParam() {
        given()
                .spec(RestUtilities.createQueryParam(reqSpec,"count","1"))
        .when()
                .get(EndPoints.STATUSES_USER_TIMELINE)
        .then()
//                .log().all()
                .spec(resSpec)
                .body("user.screen_name", hasItem("apiautomation"));
    }

    @Test
    public void assertScreenNameListInGetResponse(){
        RestUtilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);
        Response res = RestUtilities.getResponse(
                RestUtilities.createQueryParam(reqSpec,"count","2"),"get");
        ArrayList<String> screenNameList = res.path("user.screen_name");
        System.out.println(screenNameList);
        Assert.assertTrue(screenNameList.contains("apiautomation"));
    }
}
