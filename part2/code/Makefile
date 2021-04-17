all: clean compile execute compileOutput executeOutput

compile:
	jflex scanner.flex
	java -jar ../java-cup-11b.jar -interface -parser Parser parser.cup
	javac -cp ../java-cup-11b-runtime.jar *.java

execute:
	java -cp ../java-cup-11b-runtime.jar:. Driver

compileOutput:
	javac output.java

executeOutput:
	java output.java

clean:
	rm -f *.class *~ Main.java Parser.java Scanner.java sym.java output.java
