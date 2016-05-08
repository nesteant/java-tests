Purpose of the test:
One of my friends sent me a test which was used as a proof that lambdas are causing huge performance penalty. And for 
small collections this penalty was huge and shocking. 

Result:
As expected lamdas are giving some performance penalty, however it is not big if you're using it on large collections.
One of unexpected but obvious gotchas is that the first lambda in the jvm will be executed with the 
30ms (for my machine) penalty which is caused by ForkJoinPool initialization and was not covered by initial test at all.