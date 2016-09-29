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

import junitparams.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Parameterized.class)
@Parameterized.UseParametersRunnerFactory(ParameterizedJUnitParamsRunnerFactory.class)
public class ParameterizedJUnitParamsRunnerFactoryTest {

    private final int number;
    private final String string;
    
    public ParameterizedJUnitParamsRunnerFactoryTest(int number, String string) {
        this.number = number;
        this.string = string;
    }

    @Parameterized.Parameters(name = "{index}: {0}, {1}")
    public static Object[][] parameters() {
        return new Object[][]{
            {1, "String one"},
            {2, "String two"},};
    }

    @Test
    @Parameters({"1|String one", "2|String two"})
    public void stringsMatchOnlyIfNumbersMatch(int number, String string) {
        if (number == this.number) {
            assertThat(this.string).isEqualTo(string);
        } else {
            assertThat(this.string).isNotEqualTo(string);
        }
    }
    
    @Test
    public void childRunnersAreNamedCorrectly() throws Throwable {
        TestingParameterized parameterized = new TestingParameterized(
                ParameterizedJUnitParamsRunnerFactoryTest.class);
        assertThat(parameterized.getChildNames())
                .isEqualTo(new String[] {"[0: 1, String one]", "[1: 2, String two]"});
    }
    
    private static class TestingParameterized extends Parameterized {
        
        public TestingParameterized(Class<?> klass) throws Throwable {
            super(klass);
        }
        
        public String[] getChildNames() {
            return getChildren().stream()
                    .map(c -> c.getDescription().getDisplayName())
                    .toArray(String[]::new);
        }
        
    }

}
