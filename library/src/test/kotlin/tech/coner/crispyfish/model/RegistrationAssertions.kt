package tech.coner.crispyfish.model

import assertk.Assert
import assertk.all
import assertk.assertions.*

fun Assert<Registration>.hasCategoryNull() {
    prop("category", Registration::category).isNull()
}

fun Assert<Registration>.hasCategoryAbbreviation(expected: String) {
    prop("category", Registration::category).isNotNull().hasAbbreviation(expected)
}

fun Assert<Registration>.hasHandicapAbbreviation(expected: String) {
    prop("handicap", Registration::handicap).isNotNull().hasAbbreviation(expected)
}

fun Assert<Registration>.hasNumber(expected: String) {
    prop("number", Registration::number).isEqualTo(expected)
}

fun Assert<Registration>.hasFirstName(expected: String) {
    prop("firstName", Registration::firstName).isEqualTo(expected)
}

fun Assert<Registration>.hasLastName(expected: String) {
    prop("lastName", Registration::lastName).isEqualTo(expected)
}

fun Assert<Registration>.carModel() = prop("carModel", Registration::carModel)
fun Assert<Registration>.hasCarModel(expected: String) {
    carModel().isEqualTo(expected)
}

fun Assert<Registration>.carColor() = prop("carColor", Registration::carColor)
fun Assert<Registration>.hasCarColor(expected: String) {
    prop("carColor", Registration::carColor).isEqualTo(expected)
}

fun Assert<Registration>.hasMemberNumber(expected: String) {
    prop("memberNumber", Registration::memberNumber).isEqualTo(expected)
}

fun Assert<Registration>.doesNotHaveMemberNumber() {
    prop("memberNumber", Registration::memberNumber).isNull()
}

fun Assert<Registration>.rawResult(body: Assert<RegistrationResult>.() -> Unit) {
    prop("rawResult", Registration::rawResult).isNotNull().all(body)
}

fun Assert<Registration>.paxResult(body: Assert<RegistrationResult>.() -> Unit) {
    prop("paxResult", Registration::paxResult).isNotNull().all(body)
}

fun Assert<Registration>.runNumber(runNumber: Int, body: Assert<RegistrationRun>.() -> Unit) {
    prop("runs", Registration::runs)
            .index(runNumber - 1)
            .all(body)
}