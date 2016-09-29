/*
 * (C) Sergey A. Tachenov
 * This thing belongs to public domain. Really.
 */
package name.tachenov.junitparams;

import junitparams.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

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
