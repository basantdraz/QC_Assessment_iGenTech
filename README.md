# QC_Assessment_iGenTech
This repository contains my Quality Control assessment for iGen Tech. It includes:

Manual Testing – Test cases and bug reports.  
Automation Testing – Selenium, TestNG, and Page Object Model (POM) framework.  
API Testing – REST Assured with Java. 

## UI/ِAPI Automation Testing – OrangeHRM

This project automates the OrangeHRM web application.

---
## Tools
- IntelliJ IDEA
- Selenium WebDriver
- TestNG
- Maven
- Chrome Browser
- REST Assured (API)
- Git & GitHub

---
## Dependencies (pom.xml)
### UI Automation Testing

```xml
<dependencies>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-chrome-driver</artifactId>
        <version>3.141.59</version>
    </dependency>

    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>6.14.3</version>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-support</artifactId>
        <version>3.141.59</version>
</dependency>
</dependencies>
```

### API Automation Testing

```xml
<<dependencies>
    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>5.4.0</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>json-path</artifactId>
      <version>5.5.0</version>
      <scope>test</scope>
    </dependency>


    <dependency>
      <groupId>org.testng</groupId>
      <artifactId>testng</artifactId>
      <version>7.10.2</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-simple</artifactId>
      <version>2.0.7</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>4.24.0</version>
    </dependency>
  </dependencies>
```
---
## Setup Instructions

1.Clone the repository:
git clone https://github.com/basantdraz/QC_Assessment_iGenTech.git

2.Open the project in IntelliJ IDEA and make sure Maven dependencies are loaded:
Right-click on the project → Maven → Reload project

3.Download ChromeDriver matching your Chrome version:
[Download ChromeDriver](https://chromedriver.chromium.org/downloads)

4.Place the ChromeDriver executable in the resources/ folder of the project.



######################################################
## Notes
-In case API Testing for Localhost 
Ensure OrangeHRM local server is running:
[OrangeHRM server](https://github.com/orangehrm/orangehrm)
Update localhost URL in the API test code if necessary.

-Ensure your Chrome version matches the ChromeDriver version in the resources folder.

-The code uses explicit waits and assertions to make tests reliable and maintainable.

-Tests use assertions to validate API responses.
   
