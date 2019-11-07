package collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CustomCollectorTest {

    private final List<String> empty = Collections.emptyList();
    private final List<String> single = Collections.singletonList("toto");
    private final List<String> multiple = Arrays.asList("toto", "tata");

    private final RuntimeException customRuntimeException = new RuntimeException("Not the right number of elements !");
    private final NoSuchElementException noSuchElementException = new NoSuchElementException("No value present");
    private final IllegalStateException illegalStateException = new IllegalStateException("More than one value was returned");

    @Test
    public void zeroOrOneForEmpty() {
        Assert.assertEquals(empty.stream().collect(CustomCollector.zeroOrOne()), Optional.empty());
    }

    @Test
    public void zeroOrOneForSingle() {
        Assert.assertEquals(single.stream().collect(CustomCollector.zeroOrOne()), Optional.of(single.get(0)));
    }

    @Test
    public void zeroOrOneForMultiple() {
        assertThatThrownBy(() -> multiple.stream().collect(CustomCollector.zeroOrOne()))
                .isInstanceOf(illegalStateException.getClass())
                .hasMessage(illegalStateException.getMessage());
    }

    @Test
    public void zeroOrOneCustomForEmpty() {
        Assert.assertEquals(empty.stream().collect(CustomCollector.zeroOrOne(() -> customRuntimeException)), Optional.empty());
    }

    @Test
    public void zeroOrOneCustomForSingle() {
        Assert.assertEquals(single.stream().collect(CustomCollector.zeroOrOne(() -> customRuntimeException)), Optional.of(single.get(0)));
    }

    @Test
    public void zeroOrOneCustomForMultiple() {
        assertThatThrownBy(() -> multiple.stream().collect(CustomCollector.zeroOrOne(() -> customRuntimeException)))
                .isInstanceOf(customRuntimeException.getClass())
                .hasMessage(customRuntimeException.getMessage());
    }

    @Test
    public void onlyOneForEmpty() {
        assertThatThrownBy(() -> empty.stream().collect(CustomCollector.onlyOne()))
                .isInstanceOf(noSuchElementException.getClass())
                .hasMessage(noSuchElementException.getMessage());
    }

    @Test
    public void onlyOneForSingle() {
        Assert.assertEquals(single.stream().collect(CustomCollector.onlyOne()), single.get(0));
    }

    @Test
    public void onlyOneForMultiple() {
        assertThatThrownBy(() -> multiple.stream().collect(CustomCollector.onlyOne()))
                .isInstanceOf(illegalStateException.getClass())
                .hasMessage(illegalStateException.getMessage());
    }

    @Test
    public void onlyOneCustomForEmpty() {
        assertThatThrownBy(() -> empty.stream().collect(CustomCollector.onlyOne(() -> customRuntimeException)))
                .isInstanceOf(noSuchElementException.getClass())
                .hasMessage(noSuchElementException.getMessage());
    }

    @Test
    public void onlyOneCustomForSingle() {
        Assert.assertEquals(single.stream().collect(CustomCollector.onlyOne(() -> customRuntimeException)), single.get(0));
    }

    @Test
    public void onlyOneCustomForMultiple() {
        assertThatThrownBy(() -> multiple.stream().collect(CustomCollector.onlyOne(() -> customRuntimeException)))
                .isInstanceOf(customRuntimeException.getClass())
                .hasMessage(customRuntimeException.getMessage());
    }
}