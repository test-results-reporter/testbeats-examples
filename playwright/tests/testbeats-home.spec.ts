import { expect, test } from '@playwright/test';

test.describe('TestBeats Home Page Tests', () => {
  test.beforeEach(async ({ page }) => {
    await page.goto('https://testbeats.com');
  });

  test('should have the correct title - PASS', async ({ page }) => {
    await expect(page).toHaveTitle(/TestBeats/);
  });

  test('should have the correct title - FAIL', async ({ page }) => {
    // This test will fail intentionally
    await expect(page).toHaveTitle(/Wrong Title That Does Not Exist/);
  });
});
