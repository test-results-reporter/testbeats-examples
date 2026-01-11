const { request, settings, events } = require('pactum');
const { Before, AfterStep } = require('@cucumber/cucumber');


Before(() => {
  request.setBaseUrl('https://reqres.in');
  settings.setReporterAutoRun(false);
});

let req = {};
let res = {};

AfterStep(function(ctx) {
  this.attach(JSON.stringify({req, res}, null, 2), { mediaType: 'application/json' });
});

events.pactumEvents.on(events.EVENT_TYPES.AFTER_RESPONSE, (ctx) => {

  req = {};
  req.url = ctx.request.url;
  req.method = ctx.request.method;
  req.body = ctx.request.body;
  req.headers = ctx.request.headers;

  res = {};
  res.status = ctx.response.statusCode;
  res.body = ctx.response.body;
  res.headers = ctx.response.headers;

});