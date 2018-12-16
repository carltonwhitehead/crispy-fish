package org.coner.crispyfish.query

import org.coner.crispyfish.model.*
import org.coner.crispyfish.filetype.ecf.EventControlFile
import org.coner.crispyfish.filetype.staging.StagingLineDomainReader
import org.coner.crispyfish.filetype.staging.StagingLineReader

import java.time.Duration
import java.util.HashMap
import kotlin.streams.toList

class RawTimeResultsQuery(private val eventControlFile: EventControlFile, private val stagingLineReader: StagingLineReader<String>, private val stagingLineDomainReader: StagingLineDomainReader<String>) {

    @Throws(QueryException::class)
    fun query(stagingFileLines: List<String>): List<Result> {
        val driverBestRawResults = HashMap<Numbers, Result>()
        for (stagingFileLine in stagingFileLines) {
            val driver = stagingLineDomainReader.readDriver(stagingFileLine)
            val run = stagingLineDomainReader.readRun(stagingFileLine)
            if (driver == null || run == null) {
                continue
            }
            if (run.penaltyType?.shouldOmitRunFromResults == true) continue
            run.timeScratchAsString = stagingLineReader.getRunRawTime(stagingFileLine)
            run.timeScratchAsDuration = run.rawTime
            var penaltyDuration = Duration.ZERO
            when (run.penaltyType) {
                PenaltyType.CONE -> penaltyDuration = Duration.ofSeconds((run.cones!! * eventControlFile.conePenalty).toLong())
                PenaltyType.DID_NOT_FINISH -> penaltyDuration = Duration.ofDays(1)
                PenaltyType.DISQUALIFIED -> penaltyDuration = Duration.ofDays(2)
                PenaltyType.RERUN -> { throw IllegalStateException("Run should be omitted from results") }
                PenaltyType.CLEAN -> { /* no-op */ }
            }
            run.timeScored = run.rawTime!!.plus(penaltyDuration)
            val shouldPutResult: Boolean
            shouldPutResult = if (driverBestRawResults.containsKey(driver.numbers)) {
                val bestResult = driverBestRawResults[driver.numbers]
                run.timeScored?.compareTo(bestResult?.run?.timeScored!!) ?: 0 < 0
            } else {
                true
            }
            if (!shouldPutResult) {
                continue
            }
            val result = Result()
            result.driver = driver
            result.run = run
            driverBestRawResults[driver.numbers!!] = result
        }
        val results = driverBestRawResults.values.stream()
                .sorted(compareBy { it.run?.timeScored })
                .toList()
        var position = 1
        for (result in results) {
            result.position = position++
        }
        return results
    }

}
