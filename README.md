# ParameterizedJUnitParams
A runner factory to make JUnitParams work with Parameterized and vice versa

Note that versions up to 1.0.4 were only available through JCenter.
Now that it's being shut down, versions starting with 1.0.5 will be published
to Maven Central (1.0.5 is already there, and it's identical to 1.0.4):
```
    <groupId>name.tachenov.ParameterizedJUnitParams</groupId>
    <artifactId>ParameterizedJUnitParams</artifactId>
    <version>1.0.5</version>
```

Use it like this:
```java
@RunWith(Parameterized.class)
@Parameterized.UseParametersRunnerFactory(ParameterizedJUnitParamsRunnerFactory.class)
```
Then just use the features of both Parameterized and JUnitParams.
You'll get a Cartesian product of both. For example:
```java
@RunWith(Parameterized.class)
@Parameterized.UseParametersRunnerFactory(ParameterizedJUnitParamsRunnerFactory.class)
public class ExampleTest {

    private final String parameterizedParam;
    
    public ExampleTest(String param) {
        this.parameterizedParam = param;
    }
    
    @Parameterized.Parameters(name = "{0}")
    public static Object[] parameters() {
        return new Object[] {"String1", "String2"};
    }
    
    @Test
    @Parameters({"String3", "String4"})
    public void testSomething(String junitParamsParam) {
        assertThat(parameterizedParam).isIn("String1", "String2");
        assertThat(junitParamsParam).isIn("String3", "String4");
    }
    
}
```
The method ```testSomething``` will be executed 4 times with different
combinations: String1/String3, String1/String4, String2/String3, String2/String4.
