# Changelog

## 0.5.7

- Query staging runs

## 0.5.6

- Don't duplicate the gpg configuration

## 0.5.5

- Separate deployment configurations per target

## 0.5.4

- CD job should sign github release versions with gpg

## 0.5.3

- CD job won't try to publish release versions

## 0.5.2

- CD job shouldn't try to publish release versions

## 0.5.1

- Refine CI/CD/Release processes
  - CI builds on main and PRs
  - CD publishes snapshots on main
  - Publish releases on Maven Central and GitHub Packages

## 0.5.0

- Migrate to Maven Central
  - New Maven coordinates:
    - Group ID: tech.coner.crispyfish
    - Artifact ID: crispyfish-library
- Package renamed from org.coner.crispyfish to tech.coner.crispyfish
- Adopt Nexus Staging Plugin
- Adopt Dokka

## 0.4.x

- Thrashing progress towards 0.5 series.
- Don't use versions in 0.4.x series.

## 0.3.1

- RegistrationResult without time will have null instead of empty string

## 0.3.0

- Overhauled Registration handling
  - All columns in .rgg file are now available in Registration models
  - Most Registration model properties are now nullable
    - Previously empty strings will now be null
    - Improves reliability of reading registration for starting and running events
  - Custom property map
  - Removed `RegistrationMapper` and other internal classes from the public API
    - Use `EventControlFile.query*()` methods to query registrations, results, etc
  - Registrations will not have results if they lack a position, such as DNS

## 0.2.3

- Upgraded dependencies
  - Kotlin 1.4
  - JVM Target 11

## 0.2.2

- Expanded Registration reading to include:
    - Best Run 

## 0.2.1

- Expanded Registration reading to include:
    - Member Number
    - Individual run times/penalties
    - Overall raw/pax times and standings
- Changed queries to source data cached in registration file

## 0.2.0

- New queries:
  - Class definitions
    - Categories
    - Handicaps
  - Registrations

## 0.1.1

- Clean up registrations file API and implementation

## 0.1.0

Initial release.

Features:
- Registrations
    - Locate correct file
    - Read registration records with basic information
- Query raw and pax times from staging files
