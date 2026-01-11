# example-pactum-cucumber

example repo to showcase integration of pactum and cucumber with testbeats

## Installation

```bash
npm install
```

## Running Tests

```bash
npm run test
```

## Reporting Results

```bash
npx testbeats@latest publish --slack "<slack-webhook>" --cucumber "reports/cucumber-report.json" --ci-info --chart-test-summary
```

- You can find the report at [reports/cucumber-report.json](reports/cucumber-report.json)
- To view a live example, check out the GitHub workflow at [test.yaml](.github/workflows/test.yaml)


