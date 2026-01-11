const { spec } = require('pactum');
const { like } = require('pactum-matchers');
const { Given } = require('@cucumber/cucumber');


Given('I create a new user', async () => {
  await spec()
    .post('/api/users')
    .withJson({
      "name": "morpheus",
      "job": "leader"
    })
    .expectStatus(201)
    .expectJsonMatch({
      "name": "morpheus",
      "job": "leader",
      "id": like("402"),
      "createdAt": like("2024-07-21T12:48:03.531Z")
    });
});

Given('I fetch user by id', async () => {
  await spec()
    .get('/api/users/2')
    .expectStatus(200)
    .expectJsonLike({
      "data": {
        "id": 2,
        "email": "janet.weaver@reqres.in",
        "first_name": "Janet",
        "last_name": "Weaver",
        "avatar": "https://reqres.in/img/faces/2-image.jpg"
      }
    });
});

Given('I fetch all users', async () => {
  await spec()
    .get('/api/users')
    .expectStatus(200)
    .expectJsonLike({
      "data": {
        "id": 2,
        "email": "janet.weaver@reqres.in",
        "first_name": "Janet",
        "last_name": "Weaver",
        "avatar": "https://reqres.in/img/faces/2-image.jpg"
      }
    });
});