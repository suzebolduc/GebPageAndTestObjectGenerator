

// Created by Susan T Bolduc, 2020 and 2021
// If you have questions or feedback, please email suze@gsgtestautomation.com"
import geb.Page
import geb.navigator.Navigator
import org.apache.commons.lang3.StringUtils
import spock.lang.Shared

class GeneratePage extends Page {
    // ******************** Information you need to provide:
    // put the url of the page you want to work with here
    static url = 'http://<fill in your url here>>'
    // complex test static url = "http://automationpractice.com/index.php"

    // What the output file should be named and where to put it
    @Shared String pageFileName = "StarEastDemo"
    @Shared String outputPath = System.getProperty("user.dir") + "/src/functionalTest/resources/createdPages/"
    // ********************

    // variables to hold the gathered information
    @Shared String constructedTag
    @Shared String moduleName
    @Shared String navName
    @Shared String pageObject
    @Shared String testObject
    @Shared String urlDefinition
    @Shared String contentBlock
    @Shared String atStatement
    @Shared String methodsCreated
    @Shared def givenStatement = 0
    @Shared def whenStatement = null
    @Shared def thenStatement = null
    @Shared def expectStatement = null
    @Shared def whereStatement = null
    @Shared def testCaseInfo = null
    @Shared Boolean ignore = false
    @Shared String pageName = pageFileName + "Page"
    @Shared String testName = pageFileName + "Test"
    @Shared String toURL

    static content = {
        username {$("input", id: "username")}
        password {$("input", id: "password")}
        signInButton {$(id: "signIn")}
    }

    static at = {}

    //AMUI Handler Methods

    void clickSignInButton() {
        signInButton.click()
    }

    void enterUserName(String user) {
        waitFor {username.isDisplayed()}
        username.value(user)
    }

    void enterPassword(String pass) {
        password.value(pass)
    }

    //Initialize the variables for the page
    def initializeThePageParts() {
        //First the page itself
        pageObject = "package createdPages\n\n"
        pageObject = pageObject + "// This page was created using PATOG (Page and Test Object Generator) created by Suze Bolduc\n"
        pageObject = pageObject + "// If you have questions or feedback, please email susan.bolduc@mutualofomaha.com\n\n"
        pageObject = pageObject + "import geb.Page\n"
        pageObject = pageObject + "import geb.module.Select\n"
        pageObject = pageObject + "import geb.module.MultipleSelect\n"
        pageObject = pageObject + "import geb.module.Textarea\n"
        pageObject = pageObject + "import geb.module.TextInput\n"
        pageObject = pageObject + "import geb.module.Checkbox\n"
        pageObject = pageObject + "import geb.module.FormElement\n"
        pageObject = pageObject + "import geb.module.RadioButtons\n\n"
        pageObject = pageObject + "class $pageName extends Page{\n"

        //Second the URL Section
        urlDefinition = "    static url = \"$url\"\n"

        //Third:  the content block
        contentBlock = "    static content = {\n"

        //Fourth: the At statement
        atStatement = "    static at = { waitFor { "

        //Fifth:  Methods
        methodsCreated = "\n// These are the methods for the webElements in the Content Block\n"
    }

    def initializeTheTestParts() {
        testObject = "package createdPages\n\n"
        testObject = testObject + "// This page was created using PATOG (Page and Test Object Generator) created by Suze Bolduc\n"
        testObject = testObject + "// If you have questions or feedback, please email susan.bolduc@mutualofomaha.com\n\n"
        testObject = testObject + "import geb.spock.GebSpec\n"
        testObject = testObject + "import spock.lang.Stepwise\n"
        testObject = testObject + "import spock.lang.Unroll\n"
        testObject = testObject + "import spock.lang.Ignore\n"
        testObject = testObject + "@Stepwise\n"
        testObject = testObject + "@Unroll\n"
        testObject = testObject + "class $testName extends GebSpec {"
    }

    def processAllTags() {
        processHeaderTags()
        processALinkTags()
        processHeadingTags()
        processTextTags()
        processSelectTags()
        processButtonTags()
        processInputButtonTags()
        processInputSubmitTags()
        processInputTextTags()
        processInputCheckboxTags()
        processInputRadioButtonTags()
        processInputFileTags()
        processInputImageTags()
        processInputSearchTags()
        processImageTags()
        processSVGTags()
        processParagraphTags()
        processFooterTags()

//        Optional Tags
//        processLabelTags()
    }

    // ***** this section builds the A tags
    def processALinkTags() {
        // get the tag collection
        Navigator aTags = $("a")
        if (aTags.size() > 0) {
            contentBlock = contentBlock + "        //These are the A link tags\n"
        }
        moduleName = ""
        // loop over the collectiondistributionUrl
        for (int i=0;i<aTags.size();i++) {
            constructedTag = createTag(aTags[i],"Link")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createClickMethod(navName)
            // build the test
            toURL = aTags[i].getAttribute("href")
            expectStatement = null
            whenStatement = "          click$navName" + "()"
            thenStatement = "          waitFor{currentUrl == \"$toURL\"}\n          waitFor{at FILLINPAGENAME}"
            whereStatement = null
            testCaseInfo = "can be Clicked"
            ignore = true
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
        }
    }

    def processHeadingTags() {
        Navigator hTags = $("h1")
        hTags = hTags + $("h2")
        hTags = hTags + $("h3")
        hTags = hTags + $("h4")
        hTags = hTags + $("h5")
        hTags = hTags + $("h6")

        if (hTags.size() > 0) {
            contentBlock = contentBlock + "\n        //These are the Header tags you'll need to update the names to be more meaningful\n"
        }
        moduleName = ""
        for (int i=0;i<hTags.size();i++) {
            //build the test
            constructedTag = createTag(hTags[i],"Header")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createTextMethod(navName)
            // build the test
            expectStatement = "          $navName" +".isDisplayed()"
            whenStatement = null
            thenStatement = null
            whereStatement = null
            ignore = false
            testCaseInfo = "is Displayed on the Page"
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
        }
    }

    def processTextTags() {
        Navigator textAreaTags = $("textarea")
        if (textAreaTags.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the textarea tags \n"
        }
        moduleName = "Textarea"
        // loop over the collection
        for (int i=0;i<textAreaTags.size();i++) {
            constructedTag = createTag(textAreaTags[i],"Text")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createEnterMethod(navName)
            createValueMethod(navName)
            // build the tests
            // build valid character
            expectStatement = null
            whenStatement = "          $navName" +"Text(enterString)"
            thenStatement = "          $navName" +"Value() == enterString"
            whereStatement = "          enterString << ['validTest','anotherValid','finalValid']"
            testCaseInfo = "Valid Case #enterString"
            ignore = false
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
            // build invalid character test
            expectStatement = null
            whenStatement = "          $navName" +"Text(enterString)"
            thenStatement = "          $navName" +"Value() == enterString"
            whereStatement = "          enterString << ['1234','*))*()*(','(343*&&&*']"
            testCaseInfo = "Invalid Case #enterString"
            ignore = false
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
            // build required Field test
            expectStatement = null
            whenStatement = "          $navName" +"Text('TestRequired')\n          $navName" + "Text(\"\")"
            thenStatement = "          $navName" +"Value() == \"\"\n//        check error message here"
            whereStatement = null
            testCaseInfo = "Required Field Test"
            ignore = false
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
            // build Maximimum length test
            def strLength = textAreaTags[i].getAttribute("maxlength")
            if (strLength.equals("")) {
                strLength = null
            } else {
                strLength = strLength.toInteger()
            }
            if (!strLength.equals(null)) {
                expectStatement = null
                whenStatement = "          $navName" + "Text(" + StringUtils.repeat("a",strLength + 5) + ")\n"
                thenStatement = "          $navName" + "Value() == " + StringUtils.repeat("a", strLength) + "\n"
                whereStatement = null
                testCaseInfo = "Field Length Test"
                ignore = false
                createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
            }
        }
    }

    def processSelectTags() {
        Navigator selectTags = $("select")
        if (selectTags.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the select tags \n"
        }

        for (int i=0;i<selectTags.size();i++) {
            if (selectTags[i].getAttribute("multiple")){
                moduleName = "MultipleSelect"
            } else {
                moduleName = "Select"
            }
            constructedTag = createTag(selectTags[i], "SelectBox")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createSelectMethod(navName)
            createSelectedValueMethod(navName)
            createSelectedTextMethod(navName)
            // build select test
            expectStatement = null
            whenStatement = "          select$navName" + "(optionName)"
            thenStatement = "          $navName" +"SelectedText() ==  optionName\n"
            Navigator selNav = selectTags[i]
            def optionNames = selNav.children("option")
            whereStatement = "          optionName << [\""
            for (int j=0; j < optionNames.size(); j++){
                whereStatement = whereStatement + optionNames[j].text() + "\", \""
            }
            whereStatement = whereStatement.substring(0,whereStatement.length()-3) + "]"
            testCaseInfo = "Select Option #optionName"
            if (selectTags[i].getAttribute("multiple")){
                ignore = true
            } else {
                ignore = false
            }
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
        }
    }

    def processButtonTags() {
        Navigator buttonTags = $("button")
        if (buttonTags.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the button tags \n"
        }
        moduleName = "FormElement"
        for (int i=0;i<buttonTags.size();i++) {
            constructedTag = createTag(buttonTags[i],"Button")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createClickMethod(navName)
            createIsDisplayedMethod(navName)
            // build the test
            expectStatement = null
            whenStatement = "          waitFor(is$navName"+"Enabled())\n          click$navName" + "()"
            thenStatement = "          waitFor{currentUrl == \"\"}\n          waitFor{at FILLINPAGENAME}"
            whereStatement = null
            testCaseInfo = "Performs this action"
            ignore = true
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
        }
    }

    def processInputButtonTags() {
        Navigator buttonTags = $("input", type: "button")
        if (buttonTags.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the Input type button tags \n"
        }
        moduleName = "FormElement"
        for (int i=0;i<buttonTags.size();i++) {
            constructedTag = createTag(buttonTags[i],"InputButton")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createClickMethod(navName)
            createIsDisplayedMethod(navName)
            expectStatement = null
            whenStatement = "          waitFor(is$navName"+"Enabled())\n          click$navName" + "()"
            thenStatement = "          waitFor{currentUrl == \"\"}\n          waitFor{at FILLINPAGENAME}"
            whereStatement = null
            testCaseInfo = "completes action"
            ignore = true
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
        }
    }

    def processInputSubmitTags() {
        Navigator inputSubmitTags = $("input", type: "submit")
        if (inputSubmitTags.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the input submit button tags \n"
        }
        moduleName = ""
        for (int i=0;i<inputSubmitTags.size();i++) {
            constructedTag = createTag(inputSubmitTags[i],"SubmitButton")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createClickMethod(navName)
            createIsDisplayedMethod(navName)
            expectStatement = null
            whenStatement = "          waitFor(is$navName"+"Enabled())\n          click$navName" + "()"
            thenStatement = "          waitFor{currentUrl == \"\"}\n          waitFor{at FILLINPAGENAME}"
            whereStatement = null
            testCaseInfo = "completes action"
            ignore = true
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
        }
    }

    def processInputTextTags() {
        Navigator inputTextTags = $("input", type: "text")
        if (inputTextTags.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the input text button tags \n"
        }
        moduleName = "TextInput"
        for (int i=0;i<inputTextTags.size();i++) {
            constructedTag = createTag(inputTextTags[i],"InputText")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createEnterMethod(navName)
            createValueMethod(navName)
            // build valid character
            expectStatement = null
            whenStatement = "          $navName" +"Text(enterString)"
            thenStatement = "          $navName" +"Value() == enterString"
            whereStatement = "          enterString << ['validTest','anotherValid','finalValid']"
            testCaseInfo = "Valid Case #enterString"
            ignore = false
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
            // build invalid character test
            expectStatement = null
            whenStatement = "          $navName" +"Text(enterString)"
            thenStatement = "          $navName" +"Value() == enterString"
            whereStatement = "          enterString << ['1234','*))*()*(','(343*&&&*']"
            testCaseInfo = "Invalid Case #enterString"
            ignore = false
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
            // build required Field test
            expectStatement = null
            whenStatement = "          $navName" +"Text('TestRequired')\n          $navName" + "Text(\"\")"
            thenStatement = "          $navName" +"Value() == \"\"\n//        check error message here"
            whereStatement = null
            testCaseInfo = "Required Field Test"
            ignore = false
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
            // build Maximimum length test
            def strLength = inputTextTags[i].getAttribute("maxlength")
            if (strLength.equals("")) {
                strLength = null
            } else {
                strLength = strLength.toInteger()
            }
            if (!strLength.equals(null)) {
                expectStatement = null
                whenStatement = "          $navName" + "Text(" + StringUtils.repeat("a",strLength + 5) + ")\n"
                thenStatement = "          $navName" + "Value() == " + StringUtils.repeat("a", strLength) + "\n"
                whereStatement = null
                testCaseInfo = "Required Field Test"
                ignore = false
                createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
            }
        }
    }

    def processInputCheckboxTags() {
        Navigator inputCheckbox = $("input", type: "checkbox")
        if (inputCheckbox.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the input checkbox button tags \n"
        }
        moduleName = "Checkbox"
        for (int i=0;i<inputCheckbox.size();i++) {
            constructedTag = createTag(inputCheckbox[i],"Checkbox")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createCheckboxCheckMethod(navName)
            createValidateCheckBoxCheckedMethod(navName)
            createCheckboxUncheckMethod(navName)
            createValidateCheckBoxUncheckedMethod(navName)
            // build check test
            expectStatement = null
            whenStatement = "          $navName" +"Check()"
            thenStatement = "          $navName" +"IsChecked()"
            whereStatement = null
            testCaseInfo = "checkbox is checked"
            ignore = false
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
            // build uncheck test
            expectStatement = null
            whenStatement = "          $navName" +"Uncheck()"
            thenStatement = "          $navName" +"IsUnchecked()"
            whereStatement = null
            testCaseInfo = "uncheck checkbox"
            ignore = false
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
        }
    }

    def processInputRadioButtonTags() {
        Navigator inputRadioButtons = $("input", type: "radio")
        String currentName
        String previousName
        String button
        if (inputRadioButtons.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the input radio button tags \n"
        }
        moduleName = "RadioButtons"
        for (int i=0;i<inputRadioButtons.size();i++) {
            currentName = inputRadioButtons[i].getAttribute("name")
            button = inputRadioButtons[i].getAttribute("value")

            constructedTag = createTag(inputRadioButtons[i], "Radio")
            constructedTag = constructedTag.substring(0, constructedTag.indexOf(",")) + ", name: \"" + currentName + "\"" + constructedTag.substring(constructedTag.indexOf(")"))
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "

            createRadioButtonSelectionMethod(navName)
            createValidateRadioButtonSelectedMethod(navName)
            // build radiobuttons
            expectStatement = null
            whenStatement = "          $navName" + "SelectRadioButton(\"" + button + "\")"
            thenStatement = "          $navName" + "VerifyRadioButton(\"" + button + "\")"
            whereStatement = null
            testCaseInfo = "verify radiobutton checked"
            ignore = false
            createTest(expectStatement, whenStatement, thenStatement, whereStatement, testCaseInfo, ignore)
        }

    }

    def processInputFileTags() {
        Navigator inputFile = $("input", type: "file")
        if (inputFile.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the input file button tags \n"
        }
        moduleName = ""
        for (int i=0;i<inputFile.size();i++) {
            constructedTag = createTag(inputFile[i],"FileTag")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "

            createClickMethod(navName)
        }
    }

    def processInputImageTags() {
        Navigator inputImage = $("input", type: "image")
        if (inputImage.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the input image button tags \n"
        }
        moduleName = ""
        for (int i=0;i<inputImage.size();i++) {
            constructedTag = createTag(inputImage[i],"InputImage")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "

            createClickMethod(navName)
        }
    }

    def processInputSearchTags() {
        Navigator inputSearch = $("input", type: "search")
        if (inputSearch.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the input search tags \n"
        }
        moduleName = ""
        for (int i=0;i<inputSearch.size();i++) {
            constructedTag = createTag(inputSearch[i],"InputSearch")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "

            createClickMethod(navName)
            createEnterMethod(navName)
            createValueMethod(navName)
        }
    }

    def processImageTags() {
        Navigator imageTags = $("img")
        if (imageTags.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the image (IMG) tags \n"
        }
        moduleName = ""
        for (int i=0;i<imageTags.size();i++) {
            constructedTag = createTag(imageTags[i],"Image")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "

            createClickMethod(navName)
        }
    }

    def processSVGTags() {
        Navigator svgTags = $("svg")
        if (svgTags.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the svgtags \n"
        }
        moduleName = ""
        for (int i=0;i<svgTags.size();i++) {
            constructedTag = createTag(svgTags[i],"SVG")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "

            createClickMethod(navName)
        }
    }

    def processParagraphTags() {
        Navigator paragraph = $("p")
        if (paragraph.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the paragraph tags \n"
        }
        moduleName = ""
        for (int i=0;i<paragraph.size();i++) {
            constructedTag = createTag(paragraph[i],"Paragraph")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "

            createTextMethod(navName)
        }
    }

    def processHeaderTags() {
        Navigator paragraph = $("header")
        if (paragraph.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the header tags \n"
        }
        moduleName = ""
        for (int i=0;i<paragraph.size();i++) {
            constructedTag = createTag(paragraph[i],"Header")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createTextMethod(navName)
        }
    }

    def processFooterTags() {
        Navigator paragraph = $("footer")
        if (paragraph.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the footer tags \n"
        }
        moduleName = ""
        for (int i=0;i<paragraph.size();i++) {
            constructedTag = createTag(paragraph[i],"Footer")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createTextMethod(navName)
        }
    }

    // Optional build tools
    def processLabelTags() {
        Navigator labelTags = $("label")
        if (labelTags.size() > 0 ) {
            contentBlock = contentBlock + "\n        //These are the label tags \n"
        }
        moduleName = ""
        for (int i=0;i<labelTags.size();i++) {
            constructedTag = createTag(labelTags[i],"Label")
            contentBlock = contentBlock + constructedTag
            atStatement = atStatement + "$navName && "
            createTextMethod(navName)
        }
    }

    //This method will allow us to choose the best constructedTag prefix possible based on the information in the navigator
    String createTag(Navigator nav, String type) {
        // Initialize local variables
        String tagAttributeSelected
        String attributeType
        String tagContents
        String tagType

        // List of attributes that we want to examine in order of precedence

        String[] attributes = ["id","name","class","data-tid","title","option","type","value","text", "aria-label","alt"]

        for (int i=0;i<attributes.size(); i++) {
            if (attributes[i] == "text") {
                tagAttributeSelected = nav.text()
            } else {
                tagAttributeSelected = nav.getAttribute(attributes[i])
            }

            if (tagAttributeSelected != "") {
                attributeType = attributes[i]
                tagContents = tagAttributeSelected
                i = 100
            }
        }

        if (tagContents == null | tagContents == "") {
            navName = "PlaceholderName"
            tagContents = "PlaceholderContents"
            attributeType = "PlaceholderAttribute"
        }

        // clean up the nav name
        navName = tagContents.toLowerCase() + type
        navName = navName.replace(" ","")
            .replace("!","")
            .replace("@","")
            .replace("?","")
            .replace(".","")
            .replace(",","")
            .replace("#","")
            .replace("\$","")
            .replace("(","")
            .replace(")","")
            .replace("-","")

        attributeType = attributeType.replace(" ","")
                .replace("!","")
                .replace("@","")
                .replace("?","")
                .replace(".","")
                .replace(",","")
                .replace("#","")
                .replace("\$","")
                .replace("(","")
                .replace(")","")
                .replace("-","")


        tagType = nav.tag()

        constructedTag = "        " + navName  + " {\$(\"$tagType\", $attributeType: \"$tagContents\")"
        if (moduleName != "") {
            constructedTag = constructedTag + ".module($moduleName)"
        }
        constructedTag = constructedTag + "}\n"

    }

    // Methods to create methods  ;-)

    def createClickMethod (String tagName) {
        // build the method and add it to the page
        String newMethod = "    def click" + tagName +"() {\n"
        newMethod = newMethod + "         $tagName" + ".click()\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createIsDisplayedMethod(String tagName) {
        String newMethod = "    def is" + tagName +"Enabled() {\n"
        newMethod = newMethod + "         $tagName" + ".enabled\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createEnterMethod(String tagName) {
        String newMethod = "    def " + tagName +"Text(String inputText) {\n"
        newMethod = newMethod + "         $tagName" + ".text = inputText\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createSelectMethod(String tagName) {
        String newMethod = "    def select" + tagName +"(String option) {\n"
        newMethod = newMethod + "         $tagName" + ".selected = option\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createSelectedValueMethod(String tagName){
        String newMethod = "    def " + tagName +"SelectedValue() {\n"
        newMethod = newMethod + "         $tagName" + ".selected\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }
    def createSelectedTextMethod(String tagName){
        String newMethod = "    def " + tagName +"SelectedText() {\n"
        newMethod = newMethod + "         $tagName" + ".selectedText\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createTextMethod(String tagName) {
        String newMethod = "    def " + tagName +"Text() {\n"
        newMethod = newMethod + "         $tagName" + ".text()\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createValueMethod(String tagName){
        String newMethod = "    def " + tagName +"Value() {\n"
        newMethod = newMethod + "         $tagName" + ".value()\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createCheckboxCheckMethod(String tagName){
        String newMethod = "    def " + tagName +"Check() {\n"
        newMethod = newMethod + "         $tagName" + ".check() \n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createValidateCheckBoxCheckedMethod(String tagName){
        String newMethod = "    def " + tagName + "IsChecked() {\n"
        newMethod = newMethod + "         $tagName" + ".checked\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createCheckboxUncheckMethod(String tagName){
        String newMethod = "    def " + tagName +"Uncheck() {\n"
        newMethod = newMethod + "         $tagName" + ".uncheck() \n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createValidateCheckBoxUncheckedMethod(String tagName){
        String newMethod = "    def " + tagName + "IsUnchecked() {\n"
        newMethod = newMethod + "         $tagName" + ".unchecked\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createRadioButtonSelectionMethod(String tagName){
        String newMethod = "    def " + tagName +"SelectRadioButton(String radioButton) {\n"
        newMethod = newMethod + "         $tagName" + ".checked = radioButton\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    def createValidateRadioButtonSelectedMethod(String tagName){
        String newMethod = "    def " + tagName +"VerifyRadioButton(String radioButton) {\n"
        newMethod = newMethod + "         $tagName" + ".checked == radioButton\n"
        newMethod = newMethod + "    }\n\n"
        methodsCreated = methodsCreated + newMethod
    }

    //  Test Creation

    def createTest(String expectstmt, String whenstmt, String thenstmt, String wherestmt,String caseType, Boolean ignoreIt) {

        def test = ""

        if (ignore) {
            test = "\n     //TODO -- Complete necessary refactoring and then remove @Ignore\n"
            test = test + "     @Ignore"
        }

        test = test + "\n     def \"Verify $navName $caseType\""  +"() {\n"
        if (givenStatement == 0 && !ignore) {
            test = test + "          given:\n"
            test = test + "          to $pageName\n\n"

            givenStatement = givenStatement + 1
        }

        if (!expectStatement.equals(null)) {
            test = test + "          expect:\n"
            test = test + expectStatement + "\n"
        } else {
            test = test + "          when:\n"
            test = test + whenStatement + "\n\n"
            test = test + "          then:\n"
            test = test + thenStatement + "\n"
        }

        if (!wherestmt.equals(null)) {
            test = test + "\n          where:\n"
            test = test + whereStatement + "\n"
        }

        test = test + "     }\n"
        testObject = testObject + test
    }

    // assemble the final page
    def createThePage() {
        //close the content block
        contentBlock = contentBlock + "    }\n"

        //chop off the last && and close the At statement
        atStatement = atStatement.substring( 0, atStatement.length() - 3) + " }\n}\n"

        //Concatenate all the pieces into a single block
        pageObject = pageObject + urlDefinition + contentBlock + atStatement + methodsCreated + "\n}"
        //println pageObject

        // write all the content of the pageObject into a file
        File file = new File(outputPath + pageName + ".groovy")
        file.write pageObject

        return true
    }

    def createTheTest() {
        testObject = testObject + "\n}"
        File file = new File(outputPath + testName + ".groovy")
        file.write testObject

        return true
    }
}
