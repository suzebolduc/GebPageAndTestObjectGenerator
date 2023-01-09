## **Page and Test Objects Generator**

Created by _Susan T. Bolduc_ 
Published on _3/25/2021_

This tool creates Geb Test and Page objects by reading the page specified by a URL.

There are two pieces to the tool:

    1)  GeneratePage.groovy
    2)  CreatePageAndTestObjectTests.groovy

### Add this tool to your project

    1)  Add a package called PageAndTestObjectGenerationTool at the same level as your automated code package
    2)  Add a pages package within that package
    3)  Copy GeneratePage.groovy into that package
    4)  Add a tests package at the same level as the pages package
    5)  Copy CreatePageAndTestObjectTests.groovy into the tests package
    6)  In your resources package add a new directory called createdPages

It should look something like this:

TBA

### Configure the tool for your target URL

If you have specific instructions on getting to the page, such as a login script, put that in the 
setupSpec method of the CreatePageAndTestObjectTest.groovy file.

Update the following information in the GeneratePage.groovy file:

TBA

  1)  Fill in the url of the target page
  2)  Update the variable pageFilename with the name of the page and test files.  The tool will append Page and Test to the name.
  3)  Verify that the outputPath variable correctly identifies the path to your createdPages directory.

### Run the Test

Run the test file CreatePageAndTestObjectTest. 

    1)  Under the resources package, in the directory you created named createdPages you will find your newly created and formatted Page and Test classes.  
    2)  Review the classes and update them as necessary. 
    3)  Nove them into your project's pages and tests packages to use the classes.  

### Questions?  
Email:  suze@gsgtestautomation.com