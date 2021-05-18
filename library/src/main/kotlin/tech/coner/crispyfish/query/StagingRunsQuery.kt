package tech.coner.crispyfish.query

import tech.coner.crispyfish.filetype.staging.StagingFile
import tech.coner.crispyfish.model.Run
import tech.coner.crispyfish.model.StagingLineRegistration

class StagingRunsQuery(
    private val stagingFile: StagingFile
) {

    fun query(): List<Pair<StagingLineRegistration?, Run?>> {
        val reader = stagingFile.modelReader()
        val pairs = mutableListOf<Pair<StagingLineRegistration?, Run?>>()
        val lines = stagingFile.file.readLines()
        for (line in lines) {
            val registration = reader.readRegistration(line)
            val run = reader.readRun(line)
            pairs += registration to run
        }
        return pairs
    }
}