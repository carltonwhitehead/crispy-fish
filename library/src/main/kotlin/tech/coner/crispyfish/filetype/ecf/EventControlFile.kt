package tech.coner.crispyfish.filetype.ecf

import tech.coner.crispyfish.datatype.underscorepairs.SimpleStringUnderscorePairReader
import tech.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import tech.coner.crispyfish.filetype.registration.RegistrationFileLocator
import tech.coner.crispyfish.filetype.staging.SimpleStringStagingLineReader
import tech.coner.crispyfish.filetype.staging.StagingFile
import tech.coner.crispyfish.filetype.staging.StagingFileAssistant
import tech.coner.crispyfish.filetype.staging.StagingFileLocator
import tech.coner.crispyfish.model.ClassDefinition
import tech.coner.crispyfish.model.EventDay
import tech.coner.crispyfish.model.Run
import tech.coner.crispyfish.model.StagingLineRegistration
import tech.coner.crispyfish.query.CategoriesQuery
import tech.coner.crispyfish.query.HandicapsQuery
import tech.coner.crispyfish.query.RegistrationsQuery
import tech.coner.crispyfish.query.StagingRunsQuery
import java.io.File
import java.io.FileNotFoundException

class EventControlFile(
        val file: File,
        val classDefinitionFile: ClassDefinitionFile,
        val isTwoDayEvent: Boolean,
        val conePenalty: Int,
        private val ecfAssistant: EventControlFileAssistant = EventControlFileAssistant(),
        private val stagingFileAssistant: StagingFileAssistant = StagingFileAssistant()
) {

    init {
        if (!ecfAssistant.isEventControlFile(file)) {
            throw NotEventControlFileException(file)
        }
    }

    val registrationFileLocator by lazy { RegistrationFileLocator(this) }

    fun registrationFile() = registrationFileLocator.locate()

    val stagingFileLocator by lazy { StagingFileLocator(this) }

    fun stagingFile(eventDay: EventDay = EventDay.ONE) = StagingFile(
            file = stagingFileLocator.locate(eventDay) ?: throw FileNotFoundException(),
            reader = SimpleStringStagingLineReader(SimpleStringUnderscorePairReader()),
            assistant = stagingFileAssistant
    )

    fun queryCategories() = CategoriesQuery(classDefinitionFile).query()

    fun queryHandicaps() = HandicapsQuery(classDefinitionFile).query()

    fun queryRegistrations(
        categories: List<ClassDefinition>? = null,
        handicaps: List<ClassDefinition>? = null
    ) = RegistrationsQuery(
            eventControlFile = this,
            categories = categories ?: queryCategories(),
            handicaps = handicaps ?: queryHandicaps()
    ).query()

    fun queryStagingRuns(
        eventDay: EventDay = EventDay.ONE
    ): List<Pair<StagingLineRegistration?, Run?>> {
        return StagingRunsQuery(stagingFile = stagingFile(eventDay = eventDay))
            .query()
    }

}
