# Twitter APIs

In this project, I'm getting all Twitter related classes from [Rest API Training](https://github.com/pardinn/RestAPITraining) project and organizing them into a framework.

All knowledge was taken from [this Udemy course](https://www.udemy.com/rest-api-automation/?src=sac&kw=rest).

## IMPORTANT NOTE
If you intend to use this framework, you will be required to create the following class under `src/test/java/constants` package:

```java
package constants;

public class Auth {
    public static final String CONSUMER_KEY = "your twitter consumer key";
    public static final String CONSUMER_SECRET = "your twitter consumer secret";
    public static final String ACCESS_TOKEN = "your twitter access token";
    public static final String ACCESS_SECRET = "your twitter access secret";
}
```

You can create your Twitter keys at the following location:

https://apps.twitter.com

You'll need to create a new app (if you don't have one for your account already).
After that, you'll need to generate an Access Token for you.
Once you've done all of this, you should have the **Consumer Key**, **Consumer Secret**, **Access Token** and **Access Secret**.
