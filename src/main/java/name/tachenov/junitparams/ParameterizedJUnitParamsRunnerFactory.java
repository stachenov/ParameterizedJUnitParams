/*
   Copyright 2016 Sergey A. Tachenov

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package name.tachenov.junitparams;

import org.junit.runner.*;
import org.junit.runners.model.*;
import org.junit.runners.parameterized.*;

/**
 * The factory for parameterized JUnitParams tests.
 * 
 * Makes the {@code Parameterized} runner create instances of JUnitParams
 * runners to actually run test methods. Use like this:
 * <p>
 * <pre>
&#064;RunWith(Parameterized.class)
&#064;Parameterized.UseParametersRunnerFactory(ParameterizedJUnitParamsRunnerFactory.class)
public class YourTest {

    private final int number;
    private final String string;

    public YourTest(int number, String string) {
        this.number = number;
        this.string = string;
    }

    &#064;Parameterized.Parameters
    public static Object[][] parameters() {
        return new Object[][]{
            {1, "String one"},
            {2, "String two"},};
    }
    
    &#064;Test
    &#064;Parameters({"1|String one", "2|String two"})
    public void testMethod(int number, String string) {
        // do some assertions
    }
}
 * </pre>
 * <p>
 * For every test method, you'll get a Cartesian product
 * of the parameterized fixtures with method parameters.
 * In the example above, the test method will be run four times,
 * two times with {@code this.string} equal to {@code "String one"},
 * but different parameters, and two more times with
 * {@code this.string} equal to {@code "String two"}, again
 * with different parameters.
 * 
 * @author Sergey A. Tachenov
 */
public class ParameterizedJUnitParamsRunnerFactory implements ParametersRunnerFactory {

    @Override
    public Runner createRunnerForTestWithParameters(TestWithParameters test)
            throws InitializationError {
        return new ParameterizedJUnitParamsRunner(test.getTestClass().getJavaClass(), test);
    }
    
}
