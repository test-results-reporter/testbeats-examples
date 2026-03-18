# WebDriverIO + Cucumber + TestBeats

This repository demonstrates how to integrate [WebDriverIO](https://webdriver.io) with [Cucumber](https://cucumber.io) and [TestBeats](https://testbeats.com).

## 🚀 Setup

1.  **Install Dependencies**

    ```bash
    npm install
    ```

2.  **Configure TestBeats**

    Update `testbeats.config.json` with your Slack or Teams webhook URL.

    ```json
    {
      "targets": [
        {
          "name": "slack",
          "inputs": {
            "url": "YOUR_SLACK_WEBHOOK_URL"
          }
        }
      ]
    }
    ```

3.  **Run Tests**

    ```bash
    npm test
    ```

4.  **Publish Results manually**

    ```bash
    npx testbeats@latest publish -c testbeats.config.json
    ```

## 📊 CI/CD Integration

The integration is automated using GitHub Actions. See [.github/workflows/webdriverio-cucumber.yaml](../.github/workflows/webdriverio-cucumber.yaml) for details.
