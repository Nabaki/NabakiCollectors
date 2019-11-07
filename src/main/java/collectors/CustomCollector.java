package collectors;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class CustomCollector {

    private CustomCollector(){}

    private static final RuntimeException MORE_THAN_ONE_EXCEPTION = new IllegalStateException("More than one value was returned");

    /**
     * Collector which get an optional element if the stream contains less than one element or throw an exception
     *
     * @param runtimeExceptionF a custom RuntimeException in case the stream contains more than one matching element
     * @return Optional.empty if the stream is empty, or Some(T) if the stream contains only one element
     * @throws RuntimeException in case the stream contains more than one matching element
     */
    public static <T> Collector<T, ?, Optional<T>> zeroOrOne(Supplier<RuntimeException> runtimeExceptionF) {
        return Collectors.reducing((a, b) -> {
            throw runtimeExceptionF.get();
        });
    }

    /**
     * Collector which get an optional element if the stream contains less than one element or throw an exception
     *
     * @return Optional.empty if the stream is empty, or Some(T) if the stream contains only one element
     * @throws IllegalStateException in case the stream contains more than one matching element
     */
    public static <T> Collector<T, ?, Optional<T>> zeroOrOne() {
        return zeroOrOne(() -> MORE_THAN_ONE_EXCEPTION);
    }

    /**
     * Collector which get the only element in the stream, r throw an exception
     *
     * @param runtimeExceptionF a custom RuntimeException in case the stream contains more than one matching element
     * @return the only element of the stream
     * @throws NoSuchElementException in case the stream is empty
     * @throws RuntimeException       in case the stream contains more than one matching element
     */
    public static <T> Collector<T, ?, T> onlyOne(Supplier<RuntimeException> runtimeExceptionF) {
        return Collectors.collectingAndThen(zeroOrOne(runtimeExceptionF), Optional::get);
    }

    /**
     * Collector which get the only element in the stream, r throw an exception
     *
     * @return the only element of the stream
     * @throws NoSuchElementException in case the stream is empty
     * @throws IllegalStateException  in case the stream contains more than one matching element
     */
    public static <T> Collector<T, ?, T> onlyOne() {
        return Collectors.collectingAndThen(zeroOrOne(), Optional::get);
    }
}
