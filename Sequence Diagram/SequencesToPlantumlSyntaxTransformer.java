

import java.util.ArrayList;

public class SequencesToPlantumlSyntaxTransformer {
	
	private static String CALL = " -> ";
	private static String RETURN_CALL  = " <-- ";
	
	public static String  transform(ArrayList<Sequence> sequences ) {
		StringBuffer buffer = new StringBuffer("@startuml\n");

            for (Sequence sequence : sequences) {
            	
            	if(sequence.getType()  == 0){
            	buffer.append(sequence.getSorceClassName()+ CALL+ sequence.getTargetClassName());
            	buffer.append(" : " + sequence.getMethodSignature()).append("\n");
            	}            	
            	
            	if(sequence.getType()  == 1){
            		if(!sequence.getSorceClassName().equals(sequence.getTargetClassName())){
                	buffer.append(sequence.getSorceClassName()+ RETURN_CALL+ sequence.getTargetClassName() );
                	if(sequence.getReturnObject() != null){
                	buffer.append(" : " + sequence.getReturnObject()).append("\n");
                	}else{
                		buffer.append("\n");
                		
                	}
            		}
                }
            	if(sequence.getType()  == 2){

                	buffer.append(sequence.getSorceClassName()+ CALL+ sequence.getTargetClassName() + "throws"+ sequence.getExceptionType()).append("\n");

                }
            	
         
}            buffer.append("\n").append("@enduml\n");

			return buffer.toString();


    }
}
