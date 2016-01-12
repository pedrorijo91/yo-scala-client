# Yo-scala-client

[![Build Status](https://travis-ci.org/pedrorijo91/yo-scala-client.svg?branch=master)](https://travis-ci.org/pedrorijo91/yo-scala-client)  [![Download](https://api.bintray.com/packages/pedrorijo91/maven/yo-scala-client/images/download.svg)](https://bintray.com/pedrorijo91/maven/yo-scala-client/_latestVersion) [![Codacy Badge](https://api.codacy.com/project/badge/grade/84d510245e42422c8691aea5f38a73dc)](https://www.codacy.com/app/pedrorijo91/yo-scala-client) [![Codacy Badge](https://api.codacy.com/project/badge/coverage/84d510245e42422c8691aea5f38a73dc)](https://www.codacy.com/app/pedrorijo91/yo-scala-client)

---

### yo-scala-client

Yo-scala-client is a client for [Yo API](http://docs.justyo.co/docs/) written in Scala.

Currently only supports [API Tokens Endpoints](http://docs.justyo.co/docs/api-tokens) but working to support the new [REST API](http://docs.justyo.co/docs/oauth).

### Usage

```
val client = new YoClient("<api-token>")

val response = client.numberSubscribers
```

Responses are always an `Either[E, T]`.

`Left` returns the error code, the message returned by the API, and in some cases still extra information

`Right`returns information retuned by API for correct requests

### Contributing

Check [CONTRIBUTING.md](CONTRIBUTING.md)

### Issue tracking

After checking already reported issues, report your issues to [Github issues](https://github.com/pedrorijo91/yo-scala-client/issues)
