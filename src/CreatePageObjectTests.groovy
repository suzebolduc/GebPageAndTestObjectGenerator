package com.mutualofomaha.automated.PageAndTestObjectGenerationTool.tests

// Created by Susan T Bolduc, 2020 and 2021
// If you have questions or feedback, please email susan.bolduc@mutualofomaha.com"
import com.mutualofomaha.automated.PageAndTestObjectGenerationTool.pages.GeneratePage
import geb.spock.GebSpec

class CreatePageObjectTests extends GebSpec {

//    def setupSpec() {
//        to GeneratePage
//
//        // As you work through your project, you may need to add additional navigation steps here
//    }

    def "Create a new page object"() {
        given:
        to GeneratePage
        initializeThePageParts()
        initializeTheTestParts()

        when:
        //set up each type of constructedTag
        processAllTags()

        then:
        createThePage()
        createTheTest()

    }
}
