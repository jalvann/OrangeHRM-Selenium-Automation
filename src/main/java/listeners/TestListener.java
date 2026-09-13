package listeners;

import org.testng.IConfigurationListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, IConfigurationListener {

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("======================================");
        System.out.println("❌ TEST FAILED");
        System.out.println("Class  : " + result.getTestClass().getName());
        System.out.println("Method : " + result.getMethod().getMethodName());
        System.out.println("Reason : " + result.getThrowable());
        System.out.println("======================================");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("======================================");
        System.out.println("⚠ TEST SKIPPED");
        System.out.println("Class  : " + result.getTestClass().getName());
        System.out.println("Method : " + result.getMethod().getMethodName());
        System.out.println("Reason : " + result.getThrowable());
        System.out.println("======================================");
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        System.out.println("======================================");
        System.out.println("❌ CONFIGURATION FAILED");
        System.out.println("Class  : " + result.getTestClass().getName());
        System.out.println("Method : " + result.getMethod().getMethodName());
        System.out.println("Reason : " + result.getThrowable());
        System.out.println("======================================");
    }
}