import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

public class ParasoftImpactedTestSuiteRunner
    extends Suite
{
    private static Map<String, Set<String>> TEST_METHODS_TO_RUN = null;

    public ParasoftImpactedTestSuiteRunner(Class<?> arg0, RunnerBuilder arg1) throws InitializationError
    {
        super(arg0, arg1);
        try {
            extractMethodNamesFromTestsuite(arg0);
            filter(getCustomFilter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getName() {
        return getTestClass()
                .getJavaClass()
                .getAnnotation(SuiteDescription.class)
                .value();
    }

    private static void extractMethodNamesFromTestsuite(Class<?> clazz)
    {
        Method getMethods;
        try {
            getMethods = clazz.getMethod("getMethods", new Class<?>[0]);
            Object o = getMethods.invoke(null, new Object[0]);
            if (o instanceof Map<?, ?>) {
                TEST_METHODS_TO_RUN = (Map<String, Set<String>>) o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Filter getCustomFilter()
    {
        Filter f = new Filter()
        {

            public boolean shouldRun(Description desc)
            {
                if (TEST_METHODS_TO_RUN == null) {
                    return true;
                }
                String methodName = desc.getMethodName();
                if (methodName == null) {
                    return true;

                }
                Set<String> methodsForClass = TEST_METHODS_TO_RUN.get(desc.getTestClass().getName());
                if (methodsForClass != null) {
                    for (String methodForClass : methodsForClass) {
                        if (methodName.equals(methodForClass) || methodName.startsWith(methodForClass + "[")) {
                            return true;
                        }
                    }
                }
                return false;
            }

            public String describe()
            {
                return null;
            }
        };
        return f;
    }
}