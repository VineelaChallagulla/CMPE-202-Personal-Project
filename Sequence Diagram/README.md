## **_Creating a Parser which converts Java Source Code into a UML Sequence Diagram_**  
### **The tools and libraries used to generate sequence diagram from given test code are:**
#### **Tools used:**  
##### **AspectJ:** Aspect Oriented Programing is used to implement simple behaviors that are not part of core concern of a class and are common to different classes.  
##### **UML Sequence Diagram Generator used:** PlantUML (http://plantuml.com)  
PlantUML is a component that is used to generate UML diagrams from plantUML syntax.  
##### **IDE:** Eclipse Neon 2  
##### **To run the code:**  
Java environment and Graphviz need to be installed to generate class diagrams. Graphviz can be downloaded and installed from [http://www.graphviz.org/Download..php]
The contents of the folders: Referenced Libraries and src along with sequence.sh  should be in the same folder while running the code.
Sequence.sh should have permissions to be executable.  

##### **Command to run the code:**  
./sequence.sh /SOURCE_PATH CLASS_CONTAINING_Main_METHOD /OUTPUT_PATH  
For example:  
./sequence.sh /home/vineela/cmpe202/umlparser/sequence Main /home/vineela/cmpe202/umlparser  
The output folder should have the required permissions to generate the sequence diagram.  
The sequence diagram will be generated in the output_folder with the name sequence.png
