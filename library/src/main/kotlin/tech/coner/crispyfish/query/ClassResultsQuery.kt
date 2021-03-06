package tech.coner.crispyfish.query

import tech.coner.crispyfish.filetype.classdefinition.ClassDefinitionFile
import tech.coner.crispyfish.filetype.ecf.EventControlFile
import tech.coner.crispyfish.model.ClassDefinition
import tech.coner.crispyfish.model.EventDay
import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.Result

class ClassResultsQuery(
        private val classDefinitionFile: ClassDefinitionFile,
        private val eventControlFile: EventControlFile,
        private val eventDay: EventDay = EventDay.ONE
) {

    fun query(): Map<ClassDefinition?, List<Result>> {
        val categories = CategoriesQuery(classDefinitionFile).query()
        val handicaps = HandicapsQuery(classDefinitionFile).query()
        val registrations = RegistrationsQuery(
                eventControlFile = eventControlFile,
                categories = categories,
                handicaps = handicaps
        ).query()
        val registrationsByResultGrouping = registrations
                .sortedBy { it.classResult?.time ?: Int.MAX_VALUE.toString() }
                .groupBy { it.classDefinitionForClassResults }
        return registrations
            .mapNotNull { registration ->
                registration.classResult?.let { classResult ->
                    Result(
                        driver = registration,
                        position = registrationsByResultGrouping.getValue(registration.classDefinitionForClassResults)
                            .indexOf(registration) + 1,
                        time = classResult.time
                    )
                }
            }
            .sortedBy { it.position }
            .groupBy { it.driver.classDefinitionForClassResults }
    }

    private val Registration.classDefinitionForClassResults: ClassDefinition?
        get() = if (category?.paxed == true) {
            category
        } else {
            handicap
        }
}