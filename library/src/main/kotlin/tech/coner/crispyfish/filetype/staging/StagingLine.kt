package tech.coner.crispyfish.filetype.staging

import tech.coner.crispyfish.model.Registration
import tech.coner.crispyfish.model.Run
import tech.coner.crispyfish.model.StagingLineRegistration

data class StagingLine(
    val stagingLineRegistration: StagingLineRegistration?,
    val registration: Registration?,
    val run: Run?
)