package fr.nabaki.collector;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class SingleElementCollectorTest {

    private final List<String> emptyList = Collections.emptyList();
    private final List<String> singleElementList = Collections.singletonList("element1");
    private final List<String> multipleElementsList = Arrays.asList("element1", "element2");

    private final RuntimeException customRuntimeException = new RuntimeException("Not the right number of elements !");
    private final NoSuchElementException noSuchElementException = new NoSuchElementException("No value present");
    private final IllegalStateException illegalStateException = new IllegalStateException("More than one value was returned");

    @Test
    public void zeroOrOneForEmpty() {
        Assert.assertEquals(emptyList.stream().collect(SingleElementCollector.zeroOrOne()), Optional.empty());
    }

    @Test
    public void zeroOrOneForSingle() {
        Assert.assertEquals(singleElementList.stream().collect(SingleElementCollector.zeroOrOne()), Optional.of(singleElementList.get(0)));
    }

    @Test
    public void zeroOrOneForMultiple() {
        assertThatThrownBy(() -> multipleElementsList.stream().collect(SingleElementCollector.zeroOrOne()))
                .isInstanceOf(illegalStateException.getClass())
                .hasMessage(illegalStateException.getMessage());
    }

    @Test
    public void zeroOrOneCustomForEmpty() {
        Assert.assertEquals(emptyList.stream().collect(SingleElementCollector.zeroOrOne(() -> customRuntimeException)), Optional.empty());
    }

    @Test
    public void zeroOrOneCustomForSingle() {
        Assert.assertEquals(singleElementList.stream().collect(SingleElementCollector.zeroOrOne(() -> customRuntimeException)), Optional.of(singleElementList.get(0)));
    }

    @Test
    public void zeroOrOneCustomForMultiple() {
        assertThatThrownBy(() -> multipleElementsList.stream().collect(SingleElementCollector.zeroOrOne(() -> customRuntimeException)))
                .isInstanceOf(customRuntimeException.getClass())
                .hasMessage(customRuntimeException.getMessage());
    }

    @Test
    public void onlyOneForEmpty() {
        assertThatThrownBy(() -> emptyList.stream().collect(SingleElementCollector.onlyOne()))
                .isInstanceOf(noSuchElementException.getClass())
                .hasMessage(noSuchElementException.getMessage());
    }

    @Test
    public void onlyOneForSingle() {
        Assert.assertEquals(singleElementList.stream().collect(SingleElementCollector.onlyOne()), singleElementList.get(0));
    }

    @Test
    public void onlyOneForMultiple() {
        assertThatThrownBy(() -> multipleElementsList.stream().collect(SingleElementCollector.onlyOne()))
                .isInstanceOf(illegalStateException.getClass())
                .hasMessage(illegalStateException.getMessage());
    }

    @Test
    public void onlyOneCustomForEmpty() {
        assertThatThrownBy(() -> emptyList.stream().collect(SingleElementCollector.onlyOne(() -> customRuntimeException)))
                .isInstanceOf(noSuchElementException.getClass())
                .hasMessage(noSuchElementException.getMessage());
    }

    @Test
    public void onlyOneCustomForSingle() {
        Assert.assertEquals(singleElementList.stream().collect(SingleElementCollector.onlyOne(() -> customRuntimeException)), singleElementList.get(0));
    }

    @Test
    public void onlyOneCustomForMultiple() {
        assertThatThrownBy(() -> multipleElementsList.stream().collect(SingleElementCollector.onlyOne(() -> customRuntimeException)))
                .isInstanceOf(customRuntimeException.getClass())
                .hasMessage(customRuntimeException.getMessage());
    }
}