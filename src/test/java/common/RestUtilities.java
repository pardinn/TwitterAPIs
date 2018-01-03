package common;

import constants.Auth;
import constants.Path;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class RestUtilities {

    public static String ENDPOINT;
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static RequestSpecification REQUEST_SPEC;
    public static ResponseSpecBuilder RESPONSE_BUILDER;
    public static ResponseSpecification RESPONSE_SPEC;

    public static void setEndPoint(String endPoint) {
        ENDPOINT = endPoint;
    }

    public static RequestSpecification getRequestSpecification() {
        AuthenticationScheme authScheme =
                RestAssured.oauth(Auth.CONSUMER_KEY, Auth.CONSUMER_SECRET, Auth.ACCESS_TOKEN, Auth.ACCESS_SECRET);
        REQUEST_BUILDER = new RequestSpecBuilder();
        REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
        REQUEST_BUILDER.setAuth(authScheme);
        REQUEST_SPEC = REQUEST_BUILDER.build();
        return REQUEST_SPEC;
    }

    public static ResponseSpecification getResponseSpecification() {
        RESPONSE_BUILDER = new ResponseSpecBuilder();
        RESPONSE_BUILDER.expectStatusCode(200);
        RESPONSE_BUILDER.expectResponseTime(lessThan(4L), TimeUnit.SECONDS);
        RESPONSE_SPEC = RESPONSE_BUILDER.build();
        return RESPONSE_SPEC;
    }

    public static RequestSpecification createQueryParam(RequestSpecification reqSpec, String param, String value) {
        return reqSpec.queryParam(param, value);
    }

    public static RequestSpecification createQueryParam(RequestSpecification reqSpec, Map<String, String> queryMap) {
        return reqSpec.queryParams(queryMap);
    }

    public static RequestSpecification createPathParam(RequestSpecification reqSpec, String param, String value) {
        return reqSpec.pathParam(param, value);
    }

    public static Response getResponse() {
        return given().get(ENDPOINT);
    }

    public static Response getResponse(RequestSpecification reqSpec, String type) {
        REQUEST_SPEC.spec(reqSpec);
        Response response = null;

        switch (type.toLowerCase()) {
            case "get":
                response = given().spec(REQUEST_SPEC).get(ENDPOINT);
                break;
            case "post":
                response = given().spec(REQUEST_SPEC).post(ENDPOINT);
                break;
            case "put":
                response = given().spec(REQUEST_SPEC).put(ENDPOINT);
                break;
            case "delete":
                response = given().spec(REQUEST_SPEC).delete(ENDPOINT);
                break;
            default:
                System.out.println("Type is not supported");
        }

        if (response != null) {
//            response.then().log().all();
            response.then().spec(RESPONSE_SPEC);
        }
        return response;
    }

    public static JsonPath getJsonPath(Response res){
        String path = res.asString();
        return new JsonPath(path);
    }

    public static XmlPath getXmlPath(Response res){
        String path = res.asString();
        return new XmlPath(path);
    }

    public static void resetBasePath(){
        RestAssured.basePath = null;
    }

    public static void setContentType (ContentType type){
        given().contentType(type);
    }
}
