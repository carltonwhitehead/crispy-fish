package tech.coner.crispyfish.query

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isGreaterThan
import assertk.assertions.size
import tech.coner.crispyfish.test.Events
import org.junit.Test
import tech.coner.crispyfish.model.EventDay

class StagingRunsQueryTest {

    lateinit var query: StagingRunsQuery

    @Test
    fun testWithThscc2016Points1() {
        val testEvent = Events.Thscc2016Points1Danville
        query = StagingRunsQuery(
            stagingFile = testEvent.eventControlFile.stagingFile(eventDay = EventDay.ONE)
        )

        val actual = query.query()

        assertThat(actual).all {
            hasSize(455)
        }

//        val rawResults = paxTimeResultsQuery.query()
//
//        val softly = SoftAssertions()
//        softly.assertThat(rawResults)
//            .hasSize(89)
//            .has(ResultConditions.driverFinished(1, "X", "GS", "1"), Index.atIndex(0))
//            .has(ResultConditions.driverFinished(2, "X", "GS", "46"), Index.atIndex(1))
//            .has(ResultConditions.driverFinished(3, "X", "CS", "37"), Index.atIndex(2))
//            .has(ResultConditions.driverFinished(4, "", "HS", "24"), Index.atIndex(3))
//            .has(ResultConditions.driverFinished(5, "", "STR", "127"), Index.atIndex(4))
//            .has(ResultConditions.driverFinished(6, "", "ES", "84"), Index.atIndex(5))
//            .has(ResultConditions.driverFinished(7, "", "ES", "184"), Index.atIndex(6))
//            .has(ResultConditions.driverFinished(8, "", "STR", "8"), Index.atIndex(7))
//            .has(ResultConditions.driverFinished(9, "", "STR", "86"), Index.atIndex(8))
//            .has(ResultConditions.driverFinished(10, "", "CS", "8"), Index.atIndex(9))
//            .has(ResultConditions.driverFinished(79, "NOV", "STU", "33"), Index.atIndex(78))
//            .has(ResultConditions.driverFinished(80, "NOV", "STR", "18"), Index.atIndex(79))
//            .has(ResultConditions.driverFinished(81, "", "CSP", "211"), Index.atIndex(80))
//            .has(ResultConditions.driverFinished(82, "", "DSP", "17"), Index.atIndex(81))
//            .has(ResultConditions.driverFinished(83, "NOV", "HCS", "73"), Index.atIndex(82))
//            .has(ResultConditions.driverFinished(84, "NOV", "HCS", "226"), Index.atIndex(83))
//            .has(ResultConditions.driverFinished(85, "", "CSP", "26"), Index.atIndex(84))
//            .has(ResultConditions.driverFinished(86, "", "GS", "12"), Index.atIndex(85))
//            .has(ResultConditions.driverFinished(87, "", "AS", "44"), Index.atIndex(86))
//            .has(ResultConditions.driverFinished(88, "", "BSP", "28"), Index.atIndex(87))
//            .has(ResultConditions.driverFinished(89, "", "SMF", "76"), Index.atIndex(88))
//        softly.assertAll()
    }
}