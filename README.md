## Description
This plugin provides an API document enricher with the Nuxeo Drive direct edit link. This enables to quickly build integration with Nuxeo Drive direct edit feature in third party applications.

## Important Note

**These features are not part of the Nuxeo Production platform.**

These solutions are provided for inspiration and we encourage customers to use them as code samples and learning resources.

This is a moving project (no API maintenance, no deprecation process, etc.) If any of these solutions are found to be useful for the Nuxeo Platform in general, they will be integrated directly into platform, not maintained here.

## Requirements
Building requires the following software:
- git
- maven

## How to build
```
git clone https://github.com/nuxeo-sandbox/nuxeo-labs-drive-document-enricher
cd nuxeo-labs-drive-document-enricher
mvn clean install
```

## How to use
Put *drive* in the enrichers.document header
```
enrichers.document: drive
```

## Deploying
- Install the package from the admin center or using nuxeoctl

## Known limitations
This plugin is a work in progress.

## About Nuxeo
Nuxeo dramatically improves how content-based applications are built, managed and deployed, making customers more agile, innovative and successful. Nuxeo provides a next generation, enterprise ready platform for building traditional and cutting-edge content oriented applications. Combining a powerful application development environment with SaaS-based tools and a modular architecture, the Nuxeo Platform and Products provide clear business value to some of the most recognizable brands including Verizon, Electronic Arts, Sharp, FICO, the U.S. Navy, and Boeing. Nuxeo is headquartered in New York and Paris. More information is available at [www.nuxeo.com](http://www.nuxeo.com).
