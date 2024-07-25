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
- Maven 3.6 or higher

### Steps
1. **Clone the repository**:
   ```sh
   git clone https://github.com/benjamon99/rundeck-http-plugin.git
   cd your-folder
   ```

2. **Build the project**:
   ```sh
   mvn clean install
   ```

## Usage
### Running the Plugin
To use the plugin in your Rundeck instance, follow these steps:

1. **Build the plugin JAR**:
   ```sh
   mvn package
   ```

2. **Deploy the JAR**:
   Copy the generated JAR file from the `target` directory to the `libext` directory of your Rundeck installation.

## Configuration
### Plugin Configuration
The plugin can be configured using the following parameters:
- `url`: The URL to which the HTTP request is sent.
- `method`: The HTTP method to use (GET, POST, PUT, DELETE).
- `body`: The body content for the request (optional).
- `contentType`: The content type of the request body (optional).

## Testing
### Running Unit Tests
To run the unit tests, use the following command:

## Generating Javadoc
To generate Javadoc for the project, follow these steps:

1. **Run the Javadoc maven command**:
   ```sh
   mvn javadoc:javadoc
   ```
   
2. **View the Javadoc site**:
   Open the `index.html` file located in the `target/site/apidocs` directory with any web browser to view the generated Javadoc.

```sh
mvn test
```