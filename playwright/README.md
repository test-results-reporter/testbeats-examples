# Playwright + TestBeats Integration

This example demonstrates how to integrate Playwright test results with TestBeats for automated test reporting.

## 📋 What's Included

- Sample Playwright E2E tests (Chromium and Firefox)
- JUnit reporter configuration for TestBeats-compatible XML output
- TestBeats configuration for publishing results
- GitHub Actions workflow for CI/CD
- Failure screenshots and traces on retry

## 🏗️ Project Structure

```
playwright/
├── tests/
│   ├── testbeats-home.spec.ts    # Sample tests for TestBeats home page
│   └── testbeats-pricing.spec.ts # Sample tests for TestBeats pricing page
├── test-results/
│   ├── junit.xml                 # Generated JUnit XML report
│   └── */                       # Failure screenshots and traces per run
├── playwright.config.ts          # Playwright config with JUnit reporter
├── testbeats.config.json         # TestBeats configuration
└── package.json
```

## 🚀 Getting Started

### Prerequisites

- Node.js (LTS version)
- npm or yarn

### Installation

1. Install dependencies:
```bash
npm install
```

2. Install Playwright browsers (required for first run):
```bash
npx playwright install
```

### Configuration

#### 1. Playwright Configuration (`playwright.config.ts`)

The Playwright configuration uses the built-in JUnit reporter to generate XML reports for TestBeats:

- **Reporter**: `list` (console) and `junit` with output at `test-results/junit.xml`
- **Screenshots**: Captured on failure
- **Traces**: Captured on first retry (CI)
- **Projects**: Chromium and Firefox

#### 2. TestBeats Configuration (`testbeats.config.json`)

Configure TestBeats to publish results:

- `api_key`: Your TestBeats API key (set via environment variable)
- `targets`: Where to publish results (e.g. Slack with optional title and `only_failures`)
- `extensions`: Quick-chart test summary and CI info
- `results`: Points to `test-results/junit.xml` (JUnit type)
- `metadata`: Framework/runner/language for reporting (playwright, typescript)

### Running Tests

Run Playwright tests:
```bash
npm run test
```

Optional scripts:
- `npm run test:headed` — run with browser UI visible
- `npm run test:ui` — open Playwright UI mode

This will:
1. Execute all tests in `tests/` for Chromium and Firefox
2. Generate JUnit XML at `test-results/junit.xml`
3. Capture screenshots and traces for failures/retries

### Publishing Results to TestBeats

After running tests, publish results:
```bash
npm run publish
```

Or directly:
```bash
npx testbeats@latest publish -c testbeats.config.json
```

**Required Environment Variables:**
- `TEST_BEATS_API_KEY`: Your TestBeats API key
- `SLACK_MVP_URL`: Your Slack webhook URL (if using Slack target)

## 🔄 CI/CD Integration

### GitHub Actions

The repository includes a GitHub Actions workflow (`.github/workflows/playwright.yaml`) that:

1. Triggers via workflow_dispatch (manual run)
2. Installs dependencies and Playwright browsers
3. Runs Playwright tests
4. Uploads test results and screenshots as artifacts
5. Publishes results to TestBeats

**Workflow highlights:**
```yaml
- name: Install Playwright browsers
  run: npx playwright install --with-deps

- name: Run Playwright tests
  run: npm run test

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
