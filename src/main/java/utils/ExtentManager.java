package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent = null;

    public static ExtentReports createInstance(String fileName) {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
            sparkReporter.config().setDocumentTitle("bstackdemo Automation Report");
            sparkReporter.config().setReportName("bstackdemo Regression Suite");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "bstackdemo.com");
            extent.setSystemInfo("Browser", ConfigReader.getProperty("browser"));
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    public static ExtentReports getInstance() {
        return extent;
    }

    public static void resetExtent() {
        extent = null;
    }
}
