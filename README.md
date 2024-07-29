---

# Exercise: HTTP plugin for Rundeck

## Overview
This project is a plugin for Rundeck that provides HTTP notification capabilities. It includes strategies for handling various HTTP request methods such as GET, POST, PUT, and DELETE.

## Table of Contents
- [Overview](#overview)
- [Table of Contents](#table-of-contents)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Testing](#testing)

## Features
- **HTTP GET, POST, PUT, DELETE**: Supports multiple HTTP request methods.
- **Customizable Requests**: Allows setting custom request bodies and headers.
- **Mocking for Testing**: Includes mock implementations for unit testing.

## Installation
### Prerequisites
- Java 11 or higher
- Gradle 6.0.1 or higher

### Steps
1. **Clone the repository**:
   ```sh
   git clone https://github.com/benjamon99/rundeck-http-plugin.git
   cd your-folder
   ```

2. **Build the project**:
   ```sh
   ./gradlew clean build
   ```

## Usage
### Running the Plugin
To use the plugin in your Rundeck instance, follow these steps:

1. **Build the plugin JAR**:
   ```sh
   ./gradlew clean build
   ```

2. **Copy the JAR File**:
   - After building, the JAR file will be located in the `build/libs` directory.
   - Copy the JAR file to the `libext` directory of your Rundeck installation:
     ```sh
     cp build/libs/http-plugin-exercise-1.0.0-SNAPSHOT /path/to/rundeck/libext/
     ```
3. **Restart Rundeck**:
   - Restart the Rundeck service to load the new plugin.

4. **Add a Job**:
   - Once installed, the plugin can be used in your Rundeck jobs. Configure the plugin in the "Notitfications" tab and add at least one Workflow (e.g Command).

## Configuration
### Plugin Configuration
The plugin can be configured using the following parameters:
- `url`: The URL to which the HTTP request is sent.
- `method`: The HTTP method to use (GET, POST, PUT, DELETE).
- `body`: The body content for the request (optional).
- `contentType`: The content type of the request body (optional).

## Testing
### Running Unit Tests
1. **To run the unit tests, use the following command**:
   ```sh
   ./gradlew test
   ```

2. **View Test Results**: 
After running the tests, you can view the test results in the `build/reports/tests/test/index.html` file. Open this file in a web browser to see the detailed test report.

## Generating Javadoc
To generate Javadoc for the project, follow these steps:

1. **Run the Javadoc maven command**:
   ```sh
   mvn javadoc:javadoc
   ```
   
2. **View the Javadoc site**:
   Open the `index.html` file located in the `rundeck-http-plugin\build\docs\javadoc` directory with any web browser to view the generated Javadoc.

