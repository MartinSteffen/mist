package modcheck;

import absynt.*;
import absynt.Process;


public class Modstate {
public Astate state;
public VardecList vars;

public Modstate(Astate a, VardecList v)
{
state=a;
vars=v;
}
} // class Modstate


