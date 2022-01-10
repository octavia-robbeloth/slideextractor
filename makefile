# A makefile consists of rules with the following format:
# target: dependencies ...
#     commands

slideextractor: SlideExtractor.java
	javac -verbose -cp .:./libs/poi-bin-5.1.0/* SlideExtractor.java
	jar cfm SlideExtractor.jar Manifest.txt SlideExtractor.class
clean:
	rm -f *.class *.jar
