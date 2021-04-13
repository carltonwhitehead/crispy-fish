package tech.coner.crispyfish.filetype.staging

import tech.coner.crispyfish.model.EventDay
import tech.coner.crispyfish.model.PenaltyType
import tech.coner.crispyfish.filetype.ecf.EventControlFile
import java.time.Duration
import java.time.format.DateTimeParseException
import java.util.regex.Pattern


class StagingFileAssistant {
    fun buildStagingFilenameFilter(eventControlFile: EventControlFile, eventDay: EventDay): StagingFilenameFilter {
        val originalFilePattern: Pattern = when (eventDay) {
            EventDay.ONE -> StagingFilenames.ORIGINAL_FILE_DAY_1
            EventDay.TWO -> StagingFilenames.ORIGINAL_FILE_DAY_2
        }
        val originalFileBaseName = getOriginalFileBaseName(eventControlFile)
        return StagingFilenameFilter(
                originalFileBaseName,
                originalFilePattern
        )
    }

    fun getOriginalFileBaseName(eventControlFile: EventControlFile): String {
        return eventControlFile.file.nameWithoutExtension
    }

    @Throws(StagingLineException::class)
    fun convertStagingTimeStringToDuration(stagingTime: String?): Duration {
        try {
            return Duration.parse("PT${stagingTime}S")
        } catch (e: DateTimeParseException) {
            throw StagingLineException(
                    "Staging time $stagingTime cannot be parsed to Duration",
                    e
            )
        }

    }

    @Throws(StagingLineException::class)
    fun convertStagingRunPenaltyStringToPenaltyType(penalty: String?): PenaltyType {
        if (penalty?.isNotEmpty() == true) {
            when (penalty.toUpperCase()) {
                "DNF" -> return PenaltyType.DID_NOT_FINISH
                "DSQ" -> return PenaltyType.DISQUALIFIED
                "RRN" -> return PenaltyType.RERUN
            }
            val cones = convertStagingRunPenaltyStringToConeCount(penalty)
            if (cones > 0) {
                return PenaltyType.CONE
            }
        }
        return PenaltyType.CLEAN
    }

    @Throws(StagingLineException::class)
    fun convertStagingRunPenaltyStringToConeCount(penalty: String): Int {
        try {
            return Integer.parseUnsignedInt(penalty)
        } catch (e: NumberFormatException) {
            throw StagingLineException(
                    "Unable to convert staging penalty cones $penalty to cone count",
                    e
            )
        }

    }
}
