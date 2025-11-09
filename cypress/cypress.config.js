const { defineConfig } = require("cypress");

module.exports = defineConfig({
  reporter: 'cypress-xml-reporter',
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
      require('cypress-xml-reporter/src/plugin') (on);
    },
  },
});