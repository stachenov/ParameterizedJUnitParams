/*
 * (C) Sergey A. Tachenov
 * This thing belongs to public domain. Really.
 */
package name.tachenov.junitparams;

import java.util.*;
import junitparams.*;
import org.junit.runners.model.*;
import org.junit.runners.parameterized.*;

/**
 * The runner for parameterized JUnitParams tests.
 * <p>
 * It is typically not needed to create objects of this class directly.
 * Use {@link ParameterizedJUnitParamsRunnerFactory} to do that.
 * You probably only need to use this class if you want to extend it
 * and create your own factory, returning your customized runners.
 * 
 * @author Sergey A. Tachenov
 */
public class ParameterizedJUnitParamsRunner extends JUnitParamsRunner {

    private final TestWithParameters test;

    /**
     * Constructs new JUnitParams runner compatible with Parameterized.
     * @param klass the class being tested
     * @param test the test encapsulated with parameters
     * @throws InitializationError whenever is thrown by a base class
     */
    public ParameterizedJUnitParamsRunner(Class<?> klass, TestWithParameters test)
            throws InitializationError {
        super(klass);
        this.test = test;
    }

    /**
     * Creates a parameterized test instance. Uses a temporary instance
     * of {@link ParameterizedTestCreator} to do the job.
     * 
     * @return new instance
     * @throws Exception whatever is thrown by the creator
     */
    @Override
    protected Object createTest() throws Exception {
        return new ParameterizedTestCreator(test).createTest();
    }

    /**
     * Delegates to the test provided to the constructor.
     * It is needed because only the parameterized runner knows how
     * to name parameterized test instances. It passes that name,
     * encapsulated into a {@code TestWithParameters} instance,
     * to the factory, which passes it here.
     * 
     * @return the name of the test provided to the constructor
     */
    @Override
    protected String getName() {
        return test.getName();
    }

    /**
     * A temporary runner used only to create test instances.
     * <p>
     * {@code BlockJUnit4ClassRunnerWithParameters} can't be used directly because
     * it wouldn't even be constructed successfully: it tries to validate the
     * test class and fails if there are any test methods with parameters.
     * In order to avoid this, this class overrides
     * {@link #collectInitializationErrors(java.util.List) collectInitializationErrors}
     * and delegates it to the outer instance ({@code ParameterizedJUnitParamsRunner}),
     * which knows how to validate JUnitParams tests properly.
     */
    protected class ParameterizedTestCreator
            extends BlockJUnit4ClassRunnerWithParameters {

        /**
         * Creates new test creator for the given test.
         * @param test test with parameters
         * @throws InitializationError whenever is thrown by a base class
         */
        public ParameterizedTestCreator(TestWithParameters test)
                throws InitializationError {
            super(test);
        }

        /**
         * Delegates validation to the outer instance.
         * 
         * @param errors the error list to be filled
         */
        @Override
        protected void collectInitializationErrors(List<Throwable> errors) {
            ParameterizedJUnitParamsRunner.this.collectInitializationErrors(errors);
        }
    }
    
}
