# Java + TestNG + TestBeats Integration

This example demonstrates how to integrate Java TestNG test results with TestBeats for automated test reporting.

## 📋 What's Included

- Sample TestNG tests with Selenium WebDriver
- Maven configuration with Surefire plugin for JUnit XML reports
- Automatic screenshot capture for failed tests
- TestBeats configuration for publishing results
- GitHub Actions workflow for CI/CD
- Example test results and screenshots

## 🏗️ Project Structure

```
java-testng/
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── testbeats/
│                   ├── TestBeatsHomePageTests.java      # Sample tests for home page
│                   ├── TestBeatsPricingPageTests.java  # Sample tests for pricing page
│                   ├── ScreenshotUtil.java            # Utility for capturing screenshots
│                   └── ScreenshotListener.java         # TestNG listener for screenshots
├── target/
│   ├── surefire-reports/
│   │   └── junitreports/
│   │       └── *.xml                                    # Generated JUnit XML reports
│   └── screenshots/
│       └── *.png                                        # Screenshots from failed tests
├── pom.xml                                              # Maven configuration
├── testng.xml                                           # TestNG suite configuration
├── testbeats.config.json                               # TestBeats configuration
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome browser (for Selenium tests)

### Installation

1. Install dependencies:
```bash
mvn clean install
```

### Configuration

#### 1. Maven Configuration (`pom.xml`)

The project uses:
- **TestNG**: Testing framework
- **Selenium WebDriver**: Browser automation
- **Maven Surefire Plugin**: Generates JUnit XML reports compatible with TestBeats

#### 2. TestNG Configuration (`testng.xml`)

Defines the test suite and which test classes to run:

```xml
<suite name="TestBeats Test Suite">
    <test name="TestBeats Tests">
        <classes>
            <class name="com.testbeats.TestBeatsHomePageTests"/>
            <class name="com.testbeats.TestBeatsPricingPageTests"/>
        </classes>
    </test>
</suite>
```

#### 3. TestBeats Configuration (`testbeats.config.json`)

Configure TestBeats to publish results:

```json
{
  "api_key": "{TEST_BEATS_API_KEY}",
  "targets": [
    {
      "name": "slack",
      "inputs": {
        "url": "{SLACK_MVP_URL}"
      }
    }
  ],
  "extensions": [
    {
      "name": "quick-chart-test-summary"
    },
    {
      "name": "ci-info"
    }
  ],
  "results": [
    {
      "type": "junit",
      "files": ["target/surefire-reports/junitreports/*.xml"]
    }
  ]
}
```

**Configuration Details:**
- `api_key`: Your TestBeats API key (set via environment variable)
- `targets`: Where to publish results (Slack, Teams, etc.)
- `extensions`: Additional features like charts and CI info
- `results`: Points to the JUnit XML reports in `junitreports/` directory (one file per test class)

**Note:** Maven Surefire generates multiple report files:
- `junitreports/*.xml` - JUnit XML reports (one per test class) - **Recommended for TestBeats**
- `TEST-TestSuite.xml` - Aggregated Surefire report
- `testng-results.xml` - TestNG native format

The config uses `junitreports/*.xml` as these are proper JUnit XML format reports that TestBeats can process. If you prefer a single aggregated report, you can use `["target/surefire-reports/TEST-TestSuite.xml"]` instead.

### Running Tests

Run TestNG tests:
```bash
mvn clean test
```

This will:
1. Compile the test classes
2. Execute all tests defined in `testng.xml`
3. Generate JUnit XML reports in `target/surefire-reports/`
4. Capture screenshots for failed tests in `target/screenshots/`

**Screenshot Capture:**
- Screenshots are automatically captured when a test fails
- Screenshots are saved to `target/screenshots/` directory
- Filename format: `{ClassName}_{MethodName}_{Timestamp}.png`
- Screenshots are included in GitHub Actions artifacts for easy debugging

### Publishing Results to TestBeats

After running tests, publish results:
```bash
npx testbeats@latest publish -c testbeats.config.json
```

**Required Environment Variables:**
- `TEST_BEATS_API_KEY`: Your TestBeats API key
- `SLACK_MVP_URL`: Your Slack webhook URL (if using Slack target)

## 🔄 CI/CD Integration

### GitHub Actions

The repository includes a GitHub Actions workflow (`.github/workflows/java-testng.yaml`) that:

1. Runs on push/PR to main branch
2. Sets up Java 11
3. Installs Chrome and ChromeDriver
4. Executes TestNG tests via Maven
5. Publishes results to TestBeats automatically

**Workflow highlights:**
```yaml
- name: Run TestNG tests
  run: mvn clean test

- run: npx testbeats@latest publish -c testbeats.config.json
  if: always()
  env:
    TEST_BEATS_API_KEY: ${{ secrets.TEST_BEATS_API_KEY }}
    SLACK_MVP_URL: ${{ secrets.SLACK_MVP_URL }}
```

**Setup Required:**
Add these secrets to your GitHub repository:
- `TEST_BEATS_API_KEY`
- `SLACK_MVP_URL`

## 📝 Notes

- Tests run in headless Chrome mode by default for CI/CD compatibility
- Maven Surefire automatically generates JUnit XML reports that TestBeats can consume
- The test suite includes both passing and failing tests to demonstrate TestBeats reporting capabilities
