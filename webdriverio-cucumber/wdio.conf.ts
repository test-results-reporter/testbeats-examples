import type { Options } from '@wdio/types'

export const config: Options.Testrunner = {
  runner: 'local',
  tsConfigPath: './tsconfig.json',
  specs: [
    './features/**/*.feature'
  ],
  maxInstances: 10,
  capabilities: [
    {
      browserName: 'chrome',
      'goog:chromeOptions': {
        args: ['headless', 'disable-gpu']
      }
    }
  ],
  logLevel: 'info',
  bail: 0,
  waitforTimeout: 10000,
  connectionRetryTimeout: 120000,
  connectionRetryCount: 3,
  framework: 'cucumber',
  reporters: [
    'spec',
    [
      'cucumberjs-json',
      {
        jsonFolder: './reports/',
        language: 'en',
        reportFilePerRetry: false,
      },
    ]
  ],
  cucumberOpts: {
    require: ['./features/step-definitions/steps.ts'],
    backtrace: false,
    requireModule: [],
    dryRun: false,
    failFast: false,
    name: [],
    snippets: true,
    source: true,
    strict: false,
    tagExpression: '',
    timeout: 60000,
    ignoreUndefinedDefinitions: false
  }
}
