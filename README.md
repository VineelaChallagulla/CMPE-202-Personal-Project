	 	 	
## **_Creating a Parser which converts Java Source Code into a UML Class Diagram_** 

### **The tools and libraries used to generate class diagram from given test code are:**  

#### **Tools used:**  
##### **Grammar Parser used:** Antlr (http://www.antlr.org)  
Antlr is a tool used for generating a parser for any language with defined grammar.

##### **UML Class Diagram Generator used:** PlantUML (http://plantuml.com)
PlantUML is a component that is used to generate UML diagrams from plantUML syntax.

##### **IDE:** Eclipse Neon 2
##### **Apache Maven:** It is a build automation tool that describes how software is built and also describes its dependencies.

##### **To run the code:**
Java environment and Graphviz need to be installed to generate class diagrams. Graphviz can be downloaded and installed from [http://www.graphviz.org/Download..php]


##### **Command to run the code:**
-i /SOURCE_PATH -o /OUTPUT_PATH

For example:
java -jar umlparser.jar java -jar umlparser.jar -i /home/vineela/cmpe202/umlparser/test4 -o /home/vineela/cmpe202/umlparser

The output folder should have the required permissions to generate the class diagram.

The class diagram will be generated in the output_folder with the name classdiagram.png
