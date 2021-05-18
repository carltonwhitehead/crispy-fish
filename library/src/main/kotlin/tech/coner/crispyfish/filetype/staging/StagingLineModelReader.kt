package tech.coner.crispyfish.filetype.staging

import tech.coner.crispyfish.model.PenaltyType
import tech.coner.crispyfish.model.Run
import tech.coner.crispyfish.model.StagingLineRegistration
import java.time.Duration

class StagingLineModelReader<L>(
        private val stagingFileAssistant: StagingFileAssistant,
        private val stagingLineReader: StagingLineReader<L>
) {

    fun readRegistration(stagingLine: L): StagingLineRegistration? {
        val registration = StagingLineRegistration(
                number = stagingLineReader.getRegisteredDriverNumber(stagingLine),
                classing = stagingLineReader.getRegisteredDriverClass(stagingLine)?.toUpperCase(),
                name = stagingLineReader.getRegisteredDriverName(stagingLine),
                car = stagingLineReader.getRegisteredDriverCar(stagingLine),
                carColor = stagingLineReader.getRegisteredDriverCarColor(stagingLine)
        )
        return when {
            registration.number.isNullOrBlank() -> null
            registration.classing.isNullOrBlank() -> null
            else -> registration
        }
    }

    fun readRun(stagingFileLine: L): Run? {
        val raw = stagingLineReader.getRunRawTime(stagingFileLine)
        val pax = stagingLineReader.getRunPaxTime(stagingFileLine)
        val penalty = stagingLineReader.getRunPenalty(stagingFileLine)
        try {
            val runRawTime = stagingFileAssistant.convertStagingTimeStringToDuration(raw)
            val runPenaltyType = stagingFileAssistant.convertStagingRunPenaltyStringToPenaltyType(penalty)
            val runPaxTime = try {
                stagingFileAssistant.convertStagingTimeStringToDuration(pax)
            } catch (e: StagingLineException) {
                when (runPenaltyType) {
                    PenaltyType.CONE -> throw StagingLineException("Unable to parse pax time from coned run", e)
                    PenaltyType.DID_NOT_FINISH,
                    PenaltyType.DISQUALIFIED,
                    PenaltyType.RERUN -> { /* no-op */ }
                    PenaltyType.CLEAN -> throw StagingLineException("Unable to parse pax time from clean run", e)
                }
                null
            }
            val runCones = when {
                runPenaltyType === PenaltyType.CONE -> stagingFileAssistant.convertStagingRunPenaltyStringToConeCount(penalty!!)
                runPenaltyType === PenaltyType.CLEAN -> 0
                else -> null
            }
            return Run(
                number = stagingLineReader.getRunNumber(stagingFileLine)?.toIntOrNull(),
                rawTime = runRawTime,
                paxTime = runPaxTime,
                penaltyType = runPenaltyType,
                cones = runCones,
                timeScored = null,
                timeScratchAsString = raw,
                timeScratchAsDuration = stagingFileAssistant.convertStagingTimeStringToDuration(raw)
            )
        } catch (e: StagingLineException) {
            return null
        }

    }
}
