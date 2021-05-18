package tech.coner.crispyfish.model

import java.time.Duration

data class Run(
        val number: Int?,
        val rawTime: Duration?,
        val paxTime: Duration?,
        val penaltyType: PenaltyType?,
        val cones: Int?,
        val timeScored: Duration?,
        val timeScratchAsString: String?,
        val timeScratchAsDuration: Duration?
)
