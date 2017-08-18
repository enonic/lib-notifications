Push notifications library for Enonic XP
========================================

[![Build Status](https://travis-ci.org/enonic/lib-notifications.svg?branch=master)](https://travis-ci.org/enonic/lib-notifications)
[![codecov](https://codecov.io/gh/enonic/lib-notifications/branch/master/graph/badge.svg)](https://codecov.io/gh/enonic/lib-notifications)
[![License](https://img.shields.io/github/license/enonic/lib-notifications.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

This library allows making HTTP requests to a remote server and receiving the response.
See documentation here: https://enonic-docs.s3.amazonaws.com/com.enonic.lib/lib-notifications/index.html


## Building

To build this project, execute the following:

```
./gradlew clean build
```

## Publishing

To release this project, execute the following:

```
./gradlew clean build uploadArchives
```

## Documentation

Building the documentation is done executing the following:

```
./gradlew buildDoc
```

And publishing the docs to S3:

```
./gradlew publishDoc
```
