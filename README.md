# CustomCollectors

Here are some customed collectors which can help you in everyday life.

* SingleElementCollector : reduce a list to an element. Go to SingleElementCollectorTest to see examples of this Collector.
    * zeroOrOne : None if empty, Some(element) or throw if multiple elements
    * onlyOne : return the only element in list or throw