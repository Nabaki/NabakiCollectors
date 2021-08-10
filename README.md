# Nabaki's collectors

Here are some customed collectors which can help you in everyday life.

* SingleElementCollector : like findFirst, but throw exception if there is more than one element. Go to SingleElementCollectorTest to see examples of this Collector.
    * zeroOrOne returns None if the stream is empty, Some(element) or throw if multiple elements
    * onlyOne returns the only element in list or throw if none or multiple