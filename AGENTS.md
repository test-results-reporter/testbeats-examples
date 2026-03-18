# AGENTS.md

This repository contains examples of integrating various testing frameworks with [TestBeats](https://testbeats.com). This file provides instructions for AI coding agents to help maintain and add new examples to this project.

## Project Structure

Each testing framework integration resides in its own top-level directory:
- `cypress/`: Cypress E2E testing
- `playwright/`: Playwright E2E testing
- `java-testng/`: Java TestNG integration
- `cucumber-pactumjs/`: Cucumber with PactumJS integration

## Adding a New Example

When adding a new framework integration, follow these steps:

1. **Create a new directory**: Use a descriptive name for the framework (e.g., `vitest-example`).
2. **Initialize the project**: Add a `package.json` (for JS/TS), `pom.xml` (for Java), or equivalent for the chosen language.
3. **Configure the test reporter**: Set up the testing framework to generate results in a format supported by TestBeats (JUnit XML is preferred).
4. **Add TestBeats configuration**: Create a `testbeats.config.json` file in the new directory. Use environment variables for sensitive info like `api_key`.
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
       { "name": "quick-chart-test-summary" },
       { "name": "ci-info" }
     ],
     "results": [
       {
         "type": "junit",
         "files": ["path/to/results/*.xml"]
       }
     ]
   }
   ```
5. **Add sample tests**: Include 2-3 sample tests to demonstrate the integration.
6. **Create a README.md**: Add a `README.md` in the new directory explaining the setup, configuration, and how to run the tests.
7. **Add a GitHub Actions workflow**: Create a new workflow file in `.github/workflows/` (e.g., `new-framework.yaml`) that runs the tests and publishes results to TestBeats.

## CI/CD Workflow Template

When creating a new workflow in `.github/workflows/`, use the following pattern:

```yaml
name: Framework Name

on:
  workflow_dispatch:

defaults:
  run:
    shell: bash
    working-directory: ./your-directory

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - uses: actions/setup-node@v4 # Or other language setup
      with:
        node-version: lts/*
    - name: Install dependencies
      run: npm ci
    - name: Run tests
      run: npm run test
    - name: Publish to TestBeats
      run: npx testbeats@latest publish -c testbeats.config.json
      if: always()
      env:
        TEST_BEATS_API_KEY: ${{ secrets.TEST_BEATS_API_KEY }}
        SLACK_MVP_URL: ${{ secrets.SLACK_MVP_URL }}
```

## Common Commands

- **Run tests (Cypress)**: `cd cypress && npm test`
- **Publish results manually**: `npx testbeats@latest publish -c testbeats.config.json` (Requires `TEST_BEATS_API_KEY` environment variable)

## Code Style & Conventions

- Keep examples simple and focused on the TestBeats integration.
- Use environment variables for all secrets and sensitive configuration.
- Ensure every new example has a dedicated `README.md` and a GitHub Action workflow.
- Maintain consistent naming for configuration files (`testbeats.config.json`).
