package tech.coner.crispyfish.query

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import org.junit.Test
import tech.coner.crispyfish.model.EventDay
import tech.coner.crispyfish.test.Events

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
            hasSize(456)
        }
    }
}