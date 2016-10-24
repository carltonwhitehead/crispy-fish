package org.crispy_fish.query;

import org.assertj.core.api.SoftAssertions;
import org.crispy_fish.datatype.underscore_pairs.SimpleStringUnderscorePairReader;
import org.crispy_fish.datatype.underscore_pairs.UnderscorePairReader;
import org.crispy_fish.domain.Result;
import org.crispy_fish.filetype.staging.SimpleStringStagingLineReader;
import org.crispy_fish.filetype.staging.StagingFileAssistant;
import org.crispy_fish.filetype.staging.StagingLineDomainReader;
import org.crispy_fish.filetype.staging.StagingLineReader;
import org.crispy_fish.util.TestEvent;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.data.Index.atIndex;
import static org.crispy_fish.util.ResultConditions.driverFinished;
import static org.crispy_fish.util.ResultConditions.driverNameNotNullOrEmpty;

public class RawResultsQueryTest {

    private RawResultsQuery rawResultsQuery;

    private UnderscorePairReader<String> underscorePairReader;
    private StagingLineReader<String> stagingLineReader;
    private StagingLineDomainReader<String> stagingLineDomainReader;

    @Before
    public void setup() {
        underscorePairReader = new SimpleStringUnderscorePairReader();
        stagingLineReader = new SimpleStringStagingLineReader(underscorePairReader);
        stagingLineDomainReader = new StagingLineDomainReader<>(new StagingFileAssistant(), stagingLineReader);
    }

    @Test
    public void testWithThscc2016Points1() throws QueryException {
        final TestEvent testEvent = TestEvent.THSCC_2016_POINTS_1;
        List<String> lines = testEvent.getStagingFileLines();
        rawResultsQuery = new RawResultsQuery(
                testEvent.buildEventControlFileMock(),
                stagingLineReader,
                stagingLineDomainReader
        );

        List<Result> rawResults = rawResultsQuery.query(lines);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rawResults)
                .hasSize(89)
                .has(driverFinished(1, "STR", "127"), atIndex(0))
                .has(driverFinished(2, "XCSP", "162"), atIndex(1))
                .has(driverFinished(3, "XGS", "1"), atIndex(2))
                .has(driverFinished(4, "XCSP", "62"), atIndex(3))
                .has(driverFinished(5, "STR", "8"), atIndex(4))
                .has(driverFinished(6, "XCS", "37"), atIndex(5))
                .has(driverFinished(7, "STR", "86"), atIndex(6))
                .has(driverFinished(8, "XGS", "46"), atIndex(7))
                .has(driverFinished(9, "ES", "84"), atIndex(8))
                .has(driverFinished(10, "ES", "184"), atIndex(9))
                .has(driverFinished(79, "HS", "41"), atIndex(78))
                .has(driverFinished(80, "DS", "67"), atIndex(79))
                .has(driverFinished(81, "DSP", "17"), atIndex(80))
                .has(driverFinished(82, "NOVHS", "17"), atIndex(81))
                .has(driverFinished(83, "CSP", "26"), atIndex(82))
                .has(driverFinished(84, "NOVHCS", "73"), atIndex(83))
                .has(driverFinished(85, "NOVHCS", "226"), atIndex(84))
                .has(driverFinished(86, "GS", "12"), atIndex(85))
                .has(driverFinished(87, "SMF", "76"), atIndex(86))
                .has(driverFinished(88, "BSP", "28"), atIndex(87))
                .has(driverFinished(89, "AS", "44"), atIndex(88));
        softly.assertAll();
    }

    @Test
    public void testWithThscc2016Points2() throws QueryException {
        final TestEvent testEvent = TestEvent.THSCC_2016_POINTS_2;
        List<String> lines = testEvent.getStagingFileLines();
        rawResultsQuery = new RawResultsQuery(
                testEvent.buildEventControlFileMock(),
                stagingLineReader,
                stagingLineDomainReader
        );

        List<Result> rawResults = rawResultsQuery.query(lines);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rawResults)
                .hasSize(114)
                .has(driverFinished(1, "STR", "8"), atIndex(0))
                .has(driverFinished(2, "STR", "86"), atIndex(1))
                .has(driverFinished(3, "XGS", "9"), atIndex(2))
                .has(driverFinished(4, "CS", "15"), atIndex(3))
                .has(driverFinished(5, "CS", "51"), atIndex(4))
                .has(driverFinished(6, "STR", "42"), atIndex(5))
                .has(driverFinished(7, "STU", "197"), atIndex(6))
                .has(driverFinished(8, "HS", "24"), atIndex(7))
                .has(driverFinished(9, "CS", "8"), atIndex(8))
                .has(driverFinished(10, "STR", "32"), atIndex(9))
                .has(driverFinished(114, "NOVES", "981"), atIndex(113))
                .has(driverFinished(113, "NOVBSP", "281"), atIndex(112))
                .has(driverFinished(112, "NOVES", "45"), atIndex(111))
                .has(driverFinished(111, "AS", "44"), atIndex(110))
                .has(driverFinished(110, "NOVHCS", "226"), atIndex(109))
                .has(driverFinished(109, "NOVHS", "35"), atIndex(108))
                .has(driverFinished(108, "NOVHS", "103"), atIndex(107))
                .has(driverFinished(107, "NOVHS", "17"), atIndex(106))
                .has(driverFinished(106, "DSP", "51"), atIndex(105))
                .has(driverFinished(105, "SMF", "76"), atIndex(104));
        softly.assertAll();
    }

    @Test
    public void testWithThscc2016Points3() throws QueryException {
        final TestEvent testEvent = TestEvent.THSCC_2016_POINTS_3;
        List<String> lines = testEvent.getStagingFileLines();
        rawResultsQuery = new RawResultsQuery(
                testEvent.buildEventControlFileMock(),
                stagingLineReader,
                stagingLineDomainReader
        );

        List<Result> rawResults = rawResultsQuery.query(lines);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rawResults)
                .hasSize(100)
                .has(driverFinished(1, "xbs", "804"), atIndex(0))
                .has(driverFinished(2, "xcs", "9"), atIndex(1))
                .has(driverFinished(3, "asp", "9"), atIndex(2))
                .has(driverFinished(4, "am", "1"), atIndex(3))
                .has(driverFinished(5, "str", "8"), atIndex(4))
                .has(driverFinished(6, "ds", "15"), atIndex(5))
                .has(driverFinished(7, "str", "86"), atIndex(6))
                .has(driverFinished(8, "xgs", "64"), atIndex(7))
                .has(driverFinished(9, "xcs", "78"), atIndex(8))
                .has(driverFinished(10, "xcs", "81"), atIndex(9))
                .has(driverFinished(100, "novam", "77"), atIndex(99))
                .has(driverFinished(99, "novhs", "99"), atIndex(98))
                .has(driverFinished(98, "stx", "54"), atIndex(97))
                .has(driverFinished(97, "novfsp", "73"), atIndex(96))
                .has(driverFinished(96, "noves", "0"), atIndex(95))
                .has(driverFinished(95, "cs", "19"), atIndex(94))
                .has(driverFinished(94, "novcam", "182"), atIndex(93))
                .has(driverFinished(93, "hs", "93"), atIndex(92))
                .has(driverFinished(92, "novhs", "44"), atIndex(91))
                .has(driverFinished(91, "novcsp", "26"), atIndex(90));
        softly.assertAll();
    }

    @Test
    public void testWithThscc2016Points9() throws QueryException {
        final TestEvent testEvent = TestEvent.THSCC_2016_POINTS_9;
        List<String> lines = testEvent.getStagingFileLines();
        rawResultsQuery = new RawResultsQuery(
                testEvent.buildEventControlFileMock(),
                stagingLineReader,
                stagingLineDomainReader
        );

        List<Result> rawResults = rawResultsQuery.query(lines);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rawResults)
                .hasSize(128)
                .has(driverFinished(1, "XP", "58"), atIndex(0))
                .has(driverFinished(2, "ASP", "41"), atIndex(1))
                .has(driverFinished(3, "STR", "8"), atIndex(2))
                .has(driverFinished(4, "GS", "78"), atIndex(3))
                .has(driverFinished(5, "CS", "78"), atIndex(4))
                .has(driverFinished(6, "STR", "73"), atIndex(5))
                .has(driverFinished(7, "CS", "15"), atIndex(6))
                .has(driverFinished(8, "CS", "5"), atIndex(7))
                .has(driverFinished(9, "STR", "32"), atIndex(8))
                .has(driverFinished(10, "CS", "116"), atIndex(9))
                .has(driverFinished(128, "NOVSM", "333"), atIndex(127))
                .has(driverFinished(127, "SM", "51"), atIndex(126))
                .has(driverFinished(126, "NOVXP", "45"), atIndex(125))
                .has(driverFinished(125, "CS", "11"), atIndex(124))
                .has(driverFinished(124, "NOVGS", "347"), atIndex(123))
                .has(driverFinished(123, "OFES", "62"), atIndex(122))
                .has(driverFinished(122, "NOVCAM", "41"), atIndex(121))
                .has(driverFinished(121, "NOVDS", "35"), atIndex(120))
                .has(driverFinished(120, "NOVES", "21"), atIndex(119))
                .has(driverFinished(119, "NOVSTR", "56"), atIndex(118))
                .has(driverFinished(118, "NOVSTF", "98"), atIndex(117));
        softly.assertAll();
    }

    @Test
    @Ignore(value = "Relatively minor issue, ignore until #23 is resolved")
    public void testIssue23IsFixed() throws QueryException {
        final TestEvent testEvent = TestEvent.THSCC_2016_POINTS_9;
        List<String> lines = testEvent.getStagingFileLines();
        rawResultsQuery = new RawResultsQuery(
                testEvent.buildEventControlFileMock(),
                stagingLineReader,
                stagingLineDomainReader
        );

        List<Result> rawResults = rawResultsQuery.query(lines);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(rawResults)
                .has(driverNameNotNullOrEmpty(), atIndex(7)) // position 8, CS 15
                .has(driverNameNotNullOrEmpty(), atIndex(99)); // position 100, ES 11
        softly.assertAll();
    }
}