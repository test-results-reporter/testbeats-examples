# Cypress + TestBeats Integration

This example demonstrates how to integrate Cypress test results with TestBeats for automated test reporting.

## 📋 What's Included

- Sample Cypress E2E tests
- XML reporter configuration for JUnit format
- TestBeats configuration for publishing results
- GitHub Actions workflow for CI/CD
- Example test results and screenshots

## 🏗️ Project Structure

```
cypress/
├── cypress/
│   ├── e2e/
│   │   ├── testbeats-home.cy.js       # Sample tests for home page
│   │   └── testbeats-pricing.cy.js    # Sample tests for pricing page
│   ├── screenshots/                    # Auto-captured failure screenshots
│   └── support/
│       └── e2e.js
├── results/
│   └── cypress/e2e/*.xml              # Generated JUnit XML reports
├── cypress.config.js                   # Cypress configuration with XML reporter
├── testbeats.config.json              # TestBeats configuration
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

### Configuration

#### 1. Cypress Configuration (`cypress.config.js`)

The Cypress configuration uses `cypress-xml-reporter` to generate JUnit XML reports:

```javascript
const { defineConfig } = require("cypress");

module.exports = defineConfig({
  reporter: 'cypress-xml-reporter',
  e2e: {
    setupNodeEvents(on, config) {
      require('cypress-xml-reporter/src/plugin')(on);
    },
  },
});
```

#### 2. TestBeats Configuration (`testbeats.config.json`)

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
      "files": ["results/cypress/e2e/*.xml"]
    }
  ]
}
```

**Configuration Details:**
- `api_key`: Your TestBeats API key (set via environment variable)
- `targets`: Where to publish results (Slack, Teams, etc.)
- `extensions`: Additional features like charts and CI info
- `results`: Points to the generated JUnit XML files

### Running Tests

Run Cypress tests:
```bash
npm run test
```

This will:
1. Execute all tests in `cypress/e2e/`
2. Generate JUnit XML reports in `results/cypress/e2e/`
3. Capture screenshots for failed tests

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

The repository includes a GitHub Actions workflow (`.github/workflows/cypress.yaml`) that:

1. Runs on push/PR to main branch
2. Installs dependencies
3. Executes Cypress tests
4. Publishes results to TestBeats automatically

**Workflow highlights:**
```yaml
- name: Run Cypress tests
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

