package utils;

import absynt.*;
import java.io.PrintStream;

public class PrettyPrint {

    private int column;
    private int tab;
    public static final int NORM_COLUMN = 0;
    public static final int NORM_TAB = 4;

    public PrettyPrint(int i, int j) {
        try {
            column = i >= 0 ? i - i % j : 0;
            tab = j >= 0 ? j : 4;
            return;
        }
        catch(ArithmeticException _ex) {
            column = i - i % 4;
        }
        tab = 4;
    }

    public PrettyPrint() {
        this(0, 4);
    }
    public void print(Absyn absyn) {
        if(absyn instanceof Program)
            output((Program)absyn);
	if(absyn instanceof ChandecList)
	    output((ChandecList)absyn);
	if(absyn instanceof ProcessList)
	    output((ProcessList)absyn);
	if(absyn instanceof Chandec)
	    output((Chandec)absyn);
	/* Class name Process is ambigous ! */ 
	if(absyn instanceof absynt.Process)
	    output((absynt.Process)absyn);
	if(absyn instanceof Channel)
	    output((Channel)absyn);
	if(absyn instanceof VardecList)
	    output((VardecList)absyn);
	if(absyn instanceof TransitionList)
	    output((TransitionList)absyn);
	if(absyn instanceof AstateList)
	    output((AstateList)absyn);
	if(absyn instanceof Initstate)
	    output((Initstate)absyn);
	if(absyn instanceof Vardec)
	    output((Vardec)absyn);
	if(absyn instanceof Variable)
	    output((Variable)absyn);
	/*if(absyn instanceof Expr)
	  output((Expr)absyn); */
	if(absyn instanceof Transition)
	    output((Transition)absyn);
	if(absyn instanceof Astate)
	    output((Astate)absyn);
    }
		   

    private void output(Program program) {
        if(program != null) {
            System.out.println(whiteSpace(column) + "[Program] ");
            PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
            prettyprint.print(program.chans);
            prettyprint.print(program.procs);
	}
    }

    public void output(ChandecList chandeclist) {
        if(chandeclist != null) {
            System.out.println(whiteSpace(column) + "[ChandecList] ");
            PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
            for(; chandeclist != null; chandeclist = chandeclist.next)
                prettyprint.print(chandeclist.head);

        }
    }
    
    public void output(ProcessList processlist) {
        if(processlist != null) {
            System.out.println(whiteSpace(column) + "[ProcessList] ");
            PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
            for(; processlist != null; processlist = processlist.next)
                prettyprint.print(processlist.head);
	    
        }
    }
    
    public void output(Chandec chandec){
	if(chandec !=null){
	    System.out.println(whiteSpace(column) +"[Chandec] ");
	    PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
	    prettyprint.print(chandec);
	}

    }
    
    public void output(Channel channel){
	if(channel !=null){
	    System.out.println(whiteSpace(column) +"[Channel] "
			       +channel.name);
	    PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
	}
    }

    public void output(absynt.Process process){
	if(process !=null){
	    System.out.println(whiteSpace(column)+"[Process] ");
	    PrettyPrint prettyprint = new PrettyPrint(column +tab, tab);
	    prettyprint.print(process.vars);
	    prettyprint.print(process.steps);
	    prettyprint.print(process.states);
	    prettyprint.print(process.init);
	}
    }

    public void output(VardecList vardeclist){
	if(vardeclist != null) {
            System.out.println(whiteSpace(column) + "[VardecList] ");
            PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
            for(; vardeclist != null; vardeclist = vardeclist.next)
                prettyprint.print(vardeclist.head);
	    
        }
    }
    
    public void output(TransitionList transitionlist){
	if(transitionlist != null) {
            System.out.println(whiteSpace(column) + "[TransitionList] ");
            PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
            for(; transitionlist != null; transitionlist = 
		    transitionlist.next)
                prettyprint.print(transitionlist.head);
	    
        }
    }

    public void output(AstateList astatelist){
	if(astatelist != null) {
            System.out.println(whiteSpace(column) + "[AstateList] ");
            PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
            for(; astatelist != null; astatelist = 
		    astatelist.next)
                prettyprint.print(astatelist.head);
	    
        }
    }

    public void output(Vardec vardec){
	if(vardec !=null){
	    System.out.println(whiteSpace(column) + "[Vardec] ");
	    PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
	    prettyprint.print(vardec.var);
	    prettyprint.print(vardec.val);
	}
    }
    
    public void output(Variable variable){
	if(variable !=null){
	    System.out.println(whiteSpace(column) + "[Variable] " + 
			       variable.name);
	    PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
	}
    }
    
    /*
    private void print(Expr expr){
	if(expr !=null){
	    String string;
	    switch(expr){
	    case 0 :
		string ="<PLUS> ";
		break;
	    case 1:
		string ="<MINUS> ";
		break;
	    case 2:
		string ="<TIMES> ";
		break;
	    case 3:
		string ="<DIV> ";
		break;
	    case 4:
		string ="<AND> ";
		break;
	    case 5:
		string ="<OR> ";
		break;
	    case 6:
		string ="<NEG>";
		break;
	    case 7:
		string ="<EQ> ";
		break;
	    case 8:
		string ="<LESS> ";
		break;
	    case 9:
		string ="<GREATER ";
		break;
	    case 10:
		string ="<LEQ> ";
		break;
	    case 11:
		string ="<GEQ> ";
		break;
	    default:
		string =" ";
		break;
	    }
	    System.out.println(whiteSpace(column) + "[Expr] " + string);
            PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
	}
		
    }    
    */
    public void output(Initstate initstate){
	if(initstate !=null){
	    System.out.println(whiteSpace(column) + "[Initstate] " + 
			       initstate.name);
	    PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
	    prettyprint.print(initstate.assert);
	    prettyprint.print(initstate.pos);
	}
    }
    
    public void output(Position position){
	if(position !=null){
	    System.out.println(whiteSpace(column) + "[Position] " + 
			       position.x + " : " +
			       position.y);
	    PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
	}
    }

    public void output(Transition transition){
	if(transition !=null){
	    System.out.println(whiteSpace(column) + "[Transition] " );
	    PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
	    prettyprint.print(transition.source);
	    prettyprint.print(transition.target);
	    prettyprint.print(transition.lab);
	}
    }

    public void output(Astate astate){
	if(astate !=null){
	    System.out.println(whiteSpace(column) + "[Astate] " + 
			       astate.name);
	    PrettyPrint prettyprint = new PrettyPrint(column + tab, tab);
	    prettyprint.print(astate.assert);
	    prettyprint.print(astate.pos);
	}
    }
    
    private void print(Position position){
	if(position !=null){
	    System.out.println(whiteSpace(column) + "[Position] " +
			       position.x +" : " + position.y);
	    
	}
    }
    
    private String whiteSpace(int i) {
        String s = "";
        for(int j = 0; j < i; j++)
            s = j % tab != 0 ? s + " " : s + "|";
	
        return s;
    }      
    
}
