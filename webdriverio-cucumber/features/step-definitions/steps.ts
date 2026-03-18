import { Given, Then } from '@wdio/cucumber-framework';
import { expect, browser } from '@wdio/globals'

import HomePage from '../pageobjects/home.page.js';
import PricingPage from '../pageobjects/pricing.page.js';

const pages = {
    home: HomePage,
    pricing: PricingPage
}

Given(/^I am on the (\w+) page$/, async (page) => {
    await pages[page].open()
});

Then(/^the title should be "(.*)"$/, async (title) => {
    await expect(browser).toHaveTitle(new RegExp(title));
});
