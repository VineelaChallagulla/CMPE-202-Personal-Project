
import java.util.ArrayList;

import org.aspectj.lang.reflect.MethodSignature;

public aspect MethodTracking {
	private static ArrayList<Sequence> sequences = new ArrayList<Sequence>();
	private static String actor;
	

	pointcut methodCall():
		call(* *.*(..)) && within(*) && !call(* java..*(..)) &&  !within(MethodTracking)&& !within(Sequence) && !within(SequenceDiagramGenerator) && !within(SequencesToPlantumlSyntaxTransformer)&& !within(MethodTracking)&& !within(SyntaxToUmlDiagramGenerator);

	pointcut methodReturn():
		call(* *.*(..)) &&  within(*) && !call(* java..*(..)) && !within(MethodTracking)&& !within(Sequence) && !within(SequenceDiagramGenerator) && !within(SequencesToPlantumlSyntaxTransformer)&& !within(MethodTracking)&& !within(SyntaxToUmlDiagramGenerator);
	pointcut mainMethod() : execution(* *main(..)) && !within(SequenceDiagramGenerator) ;
	
	pointcut constructor() : call(*.new(..)) && within(Main) ; 
	
	before(): mainMethod()
	{
	 actor = 		 thisJoinPoint.getStaticPart().getSourceLocation().getWithinType().getName();
	


	}
	
	before(): constructor()
	{
	   Sequence sequence = new Sequence();
       String target = thisJoinPoint.getStaticPart().getSignature().getDeclaringType().getName();
	   sequence.setType(3);
	   sequence.setTargetClassName(target);
		sequence.setSorceClassName( thisJoinPoint.getStaticPart().getSourceLocation().getWithinType().getName());
		sequence.setMethodSignature("<<create>>");
		sequences.add(sequence);


	}



	before():methodCall(){		
			Sequence sequence = new Sequence();
			sequence.setType(0);
			String source;
			String target;
			if(thisJoinPoint.getThis() == null){
				source = thisJoinPoint.getStaticPart().getSourceLocation().getWithinType().getName();
			}else{
				source = thisJoinPoint.getThis().getClass().getName();
			}
			if(thisJoinPoint.getTarget() == null){
				target = thisJoinPoint.getStaticPart().getSourceLocation().getWithinType().getName();
			}else{
				target = thisJoinPoint.getTarget().getClass().getName();
			}
			sequence.setSorceClassName(source);
			sequence.setTargetClassName(target);
			String methodSignature = thisJoinPoint.getSignature().getName();
			
		      MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();

		         String[] parameterNames = signature.getParameterNames();	
		         StringBuffer parameters= new StringBuffer();
		        for (String parameter : parameterNames) {
		        	if(parameters.length()>0){
		        		parameters.append(", ");
		        	}
		        	parameters.append(parameter);
		            
		        }
			String returnType = signature.getReturnType().getTypeName();
			sequence.setMethodSignature(methodSignature+"("+parameters.toString()+")" +": " + returnType);
			if(!sequence.getSorceClassName().contains("java") && !sequence.targetClassName.contains("java")){
			sequences.add(sequence);
			}
		
	}

	after() returning(Object obj) : methodReturn() {


			Sequence sequence = new Sequence();
			sequence.setType(1);
			String source;
			String target;
			if(thisJoinPoint.getThis() == null){
				source = thisJoinPoint.getStaticPart().getSourceLocation().getWithinType().getName();
			}else{
				source = thisJoinPoint.getThis().getClass().getName();
			}
			if(thisJoinPoint.getTarget() == null){
				target = thisJoinPoint.getStaticPart().getSourceLocation().getWithinType().getName();
			}else{
				target = thisJoinPoint.getTarget().getClass().getName();
			}
			sequence.setSorceClassName(source);
			sequence.setTargetClassName(target);
			if (obj != null){
			sequence.setReturnObject(obj.toString());
			}
			if(!sequence.getSorceClassName().contains("java") && !sequence.targetClassName.contains("java")){
			sequences.add(sequence);
			}
		
	}

	after() throwing(Throwable t) : methodReturn() {
		if (thisJoinPoint.getThis() != null && thisJoinPoint.getTarget() != null) {
			Sequence sequence = new Sequence();
			sequence.setType(2);
			sequence.setSorceClassName(thisJoinPoint.getThis().getClass().getName());
			sequence.setTargetClassName(thisJoinPoint.getTarget().getClass().getName());
			sequence.setExceptionType(t.getClass().getName());
			if(!sequence.getSorceClassName().contains("java") && !sequence.targetClassName.contains("java")){
			sequences.add(sequence);
			}

		}
	}

	public static ArrayList<Sequence> getSequences() {
		return sequences;
	}

	public static String getActor() {
		return actor;
	}

	public static void setActor(String actor) {
		MethodTracking.actor = actor;
	}
	

}
