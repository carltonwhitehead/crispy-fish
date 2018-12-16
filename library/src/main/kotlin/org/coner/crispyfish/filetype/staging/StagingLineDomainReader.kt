package org.coner.crispyfish.filetype.staging

import org.coner.crispyfish.model.PenaltyType
import org.coner.crispyfish.model.Registration
import org.coner.crispyfish.model.Numbers
import org.coner.crispyfish.model.Run

class StagingLineDomainReader<L>(
        private val stagingFileAssistant: StagingFileAssistant,
        private val stagingLineReader: StagingLineReader<L>
) {

    fun readDriver(stagingLine: L): Registration {
        val driver = Registration()
        driver.name = stagingLineReader.getRegisteredDriverName(stagingLine)
        driver.carModel = stagingLineReader.getRegisteredDriverCar(stagingLine)
        val numbers = Numbers()
        numbers.classing = stagingLineReader.getRegisteredDriverClass(stagingLine)
        numbers.number = stagingLineReader.getRegisteredDriverNumber(stagingLine)
        driver.numbers = numbers
        return driver
    }

    fun readRun(stagingFileLine: L): Run? {
        val run = Run()
        val raw = stagingLineReader.getRunRawTime(stagingFileLine)
        val pax = stagingLineReader.getRunPaxTime(stagingFileLine)
        val penalty = stagingLineReader.getRunPenalty(stagingFileLine)
        try {
            run.rawTime = stagingFileAssistant.convertStagingTimeStringToDuration(raw)
            run.penaltyType = stagingFileAssistant.convertStagingRunPenaltyStringToPenaltyType(penalty)
            try {
                run.paxTime = stagingFileAssistant.convertStagingTimeStringToDuration(pax)
            } catch (e: StagingLineException) {
                when (run.penaltyType) {
                    PenaltyType.CONE -> throw StagingLineException("Unable to parse pax time from coned run", e)
                    PenaltyType.DID_NOT_FINISH,
                    PenaltyType.DISQUALIFIED,
                    PenaltyType.RERUN -> { /* no-op */ }
                    PenaltyType.CLEAN -> throw StagingLineException("Unable to parse pax time from clean run", e)
                    else -> throw IllegalStateException("Unrecognized run.penaltyType: " + run.penaltyType!!, e)
                }
            }

            run.cones = when {
                run.penaltyType === PenaltyType.CONE -> stagingFileAssistant.convertStagingRunPenaltyStringToConeCount(penalty!!)
                run.penaltyType === PenaltyType.CLEAN -> 0
                else -> null
            }
        } catch (e: StagingLineException) {
            return null
        }

        return run
    }
}
