[![Build Status](https://travis-ci.org/cmateosl/rol-api.svg?branch=master)](https://travis-ci.org/cmateosl/rol-api)
[![Quality Gate](http://sonarqube.com/api/badges/gate?key=es.esky:rol-api)](http://sonarqube.com/dashboard/index/es.esky:rol-api)

## Rol API 

Rol API provides an API Restfull based on a collection of [best practices](http://www.vinaysahni.com/best-practices-for-a-pragmatic-restful-api) to create and administrate a rol game comunity.

## Code of Conduct

This project adheres to the Contributor Covenant [code of conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. Please report unacceptable behavior to mateos.c19@gmail.com.

## Downloading Artifacts

*For now there is no artifact to download*

Exist a develop version deploy in [heroku](https://rol-api.herokuapp.com/)

## Documentation

[Wiki](https://github.com/cmateosl/rol-api/wiki) is now work in progress.

## Issue Tracking

Report issues via the Github issues. Understand our issue management process by reading about [the lifecycle of an issue](). Think you've found a bug? Please open an issue with a sample of code that reproduce the error.

## Building from Source

The rol-api project uses [Maven 3.0](https://maven.apache.org/) build system. In the instructions below, `mvn` is invoked from the root of the source tree.

### Prerequisites

[Git](https://git-scm.com/) and [JDK 8 update 20 or later](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Be sure that your `JAVA_HOME` environment variable points to the `jdk1.8.0` folder extracted from the JDK download.

### Check out sources

`git clone git@github.com:cmateosl/rol-api.git`

### Build project

`mvn clean install`

### Run project

`mvn spring-boot:run`
