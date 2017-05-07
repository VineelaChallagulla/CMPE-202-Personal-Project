#!/bin/bash 
cp MethodTracking.aj Sequence.java SequenceDiagramGenerator.java SequencesToPlantumlSyntaxTransformer.java SyntaxToUmlDiagramGenerator.java  $1


for i in  'commons-cli-1.4.jar' 'aspectjrt-1.6.9.jar' 'aspectj-1.8.10.jar' 'plantuml-6703.jar'
do
    CLASSPATH=$CLASSPATH:${PWD}/$i
done
export CLASSPATH
echo $CLASSPATH
cd $1
ajc -1.5 *.java *.aj
java SequenceDiagramGenerator $2 $3
