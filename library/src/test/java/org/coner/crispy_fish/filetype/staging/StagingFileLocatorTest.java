package org.coner.crispy_fish.filetype.staging;

import org.coner.crispy_fish.domain.EventDay;
import org.coner.crispy_fish.filetype.ecf.EventControlFile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StagingFileLocatorTest {

    private StagingFileLocator stagingFileLocator;

    @Mock
    StagingFileAssistant stagingFileAssistant;
    @Mock
    EventControlFile eventControlFile;
    @Mock
    Path eventControlFilePath;
    @Mock
    File eventControlFileParentAsFile;
    @Mock
    StagingFilenameFilter stagingFilenameFilter;

    @Before
    public void setup() {
        stagingFileLocator = new StagingFileLocator(stagingFileAssistant);
        when(eventControlFile.getPath()).thenReturn(eventControlFilePath);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLocateWithoutEventControlFileItShouldThrow() throws Exception {
        stagingFileLocator.locate(null, EventDay.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLocateWithEventButWithoutEventDayItShouldThrow() throws Exception {
        stagingFileLocator.locate(eventControlFile, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenLocateWithOneDayEventForEventDayTwoItShouldThrow() throws Exception {
        when(eventControlFile.isTwoDayEvent()).thenReturn(false);

        stagingFileLocator.locate(eventControlFile, EventDay.TWO);
    }

    @Test
    public void whenLocateWithSimpleStagingItShouldLocateStaging() {
        Path ecfParent = mock(Path.class);
        when(eventControlFilePath.getParent()).thenReturn(ecfParent);
        when(ecfParent.toFile()).thenReturn(eventControlFileParentAsFile);
        when(stagingFileAssistant.buildStagingFilenameFilter(same(eventControlFile), any(EventDay.class)))
                .thenReturn(stagingFilenameFilter);
        Path ecfPath = Paths.get("foo.ecf");
        File[] files = new File[]{ecfPath.toFile()};
        when(eventControlFileParentAsFile.listFiles(stagingFilenameFilter)).thenReturn(files);

        Path actual = stagingFileLocator.locate(eventControlFile, EventDay.ONE);

        assertThat(actual).isEqualTo(ecfPath);
    }

    @Test
    public void whenSelectFileWithSimpleStagingItShouldSelectIt() {
        File expected = Paths.get("foo.st1").toFile();
        File[] files = new File[]{
                expected
        };

        File actual = stagingFileLocator.selectFile(files);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenSelectFileWithSimpleStagingAndSomeGarbageItShouldSelectExpected() {
        File expected = Paths.get("foo.st1").toFile();
        File[] files = new File[]{
                Paths.get("foo.txt").toFile(),
                Paths.get("foo.html").toFile(),
                expected,
                Paths.get("foo.st1_log").toFile(),
        };

        File actual = stagingFileLocator.selectFile(files);

        assertThat(actual).isEqualTo(expected);
    }

}
