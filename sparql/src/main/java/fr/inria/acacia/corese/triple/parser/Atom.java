package fr.inria.acacia.corese.triple.parser;

import fr.inria.acacia.corese.api.IDatatype;
import fr.inria.acacia.corese.triple.api.ElementClause;
import fr.inria.acacia.corese.triple.cst.RDFS;

/**
 * <p>Title: Corese</p>
 * <p>Description: A Semantic Search Engine</p>
 * <p>Copyright: Copyright INRIA (c) 2007</p>
 * <p>Company: INRIA</p>
 * <p>Project: Acacia</p>
 * @author Olivier Corby & Olivier Savoie
 */

public class Atom extends Expression implements ElementClause{
	
	boolean isone = false;
	boolean isall = false;
	boolean isdirect = false;
	//boolean isset = false;
	int star;
	
	public Atom() {
	}

    
    public Atom(String name) {
		super(name);
	}
	
        @Override
	public boolean equals(Object at){
		return  false;
	}

        @Override
	public Variable getVariable() {
		return null;
	}
	
	public Expression getExpression() {
        return null;
    }

        @Override
	boolean isAtom() {
		return true;
	}

	public boolean isLiteral() {
		return false;
	}
	
	public boolean isBlank() {
		return false;
	}
	
	public boolean isResource() {
		return false;
	}
	
	public boolean isIsall() {
		return isall;
	}

	public void setIsall(boolean isall) {
		this.isall = isall;
	}

	public boolean isIsdirect() {
		return isdirect;
	}

	public void setIsdirect(boolean isdirect) {
		this.isdirect = isdirect;
	}

	public boolean isIsone() {
		return isone;
	}

	public void setIsone(boolean isone) {
		this.isone = isone;
	}

	public int getStar() {
		return star;
	}

	public void setPath(int star) {
		this.star = star;
	}

//	public boolean isIsset() {
//		return isset;
//	}
//
//	public void setIsset(boolean isset) {
//		this.isset = isset;
//	}

        @Override
	public Atom getAtom() {
		return this;
	}

        @Override
	public Constant getConstant() {
		return null;
	}

        @Override
	public IDatatype getDatatypeValue() {
		return null;
	}

	public Atom getElement() {
		return this;
	}
	
	boolean validateData(ASTQuery ast){
		if (isBlankNode() || isBlank()){
			ast.record(this);
		}
		return true;
	}
	
}