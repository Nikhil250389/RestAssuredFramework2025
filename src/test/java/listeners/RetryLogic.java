package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryLogic implements IRetryAnalyzer {
	private int retryCount = 0;
	private static final int maxRetryCount = 3;

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			retryCount++;
			System.out.println("Retrying test: " + result.getName() + " | Attempt: " + retryCount);
			return true;
		}
		return false;
	}
}