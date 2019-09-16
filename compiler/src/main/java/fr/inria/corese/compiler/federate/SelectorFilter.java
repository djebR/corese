package fr.inria.corese.compiler.federate;

import fr.inria.corese.sparql.triple.parser.ASTQuery;
import fr.inria.corese.sparql.triple.parser.BasicGraphPattern;
import fr.inria.corese.sparql.triple.parser.Exp;
import fr.inria.corese.sparql.triple.parser.Expression;
import fr.inria.corese.sparql.triple.parser.Triple;
import fr.inria.corese.sparql.triple.parser.Variable;
import java.util.ArrayList;
import java.util.List;


public class SelectorFilter {
    ASTQuery ast;
    ArrayList<BasicGraphPattern> res;
    
    SelectorFilter(ASTQuery ast) {
        this.ast = ast;
        res = new ArrayList<>();
    }
    
    List<BasicGraphPattern> process() {
        process(ast.getBody());       
        return res;
    }
    
    void process(ASTQuery ast) {
        for (Expression exp : ast.getSelectFunctions().values()) {
            process(exp);
        }
        for (Expression exp : ast.getGroupBy()) {
            process(exp);
        }
        for (Expression exp : ast.getOrderBy()) {
            process(exp);
        }
        if (ast.getHaving() != null) {
            process(ast.getHaving());
        }
        process(ast.getBody());
    }
    
    
    void process(Exp body) {
        if (body.isBGP()) {
            processBGP(body);
        }
        else if (body.isQuery()) {
            process(body.getQuery());
        }
        else for (Exp exp : body) {
            process(exp);
        }
    }
    
    void processBGP(Exp body) {
        for (Exp exp : body) {
            if (exp.isFilter() || exp.isBind()) {
                process(exp.getFilter());
            }
            else if (exp.isTriple()) {
                process(exp.getTriple(), body);
            } else {
                process(exp);
            }
        }
    }
    
    void process (Expression exp) {
        if (exp.isTermExist()) {
            process(exp.getTerm().getExistBGP());
        }
        else if (exp.isTerm()) {
            for (Expression e : exp.getArgs()) {
                process(e);
            }
        }
    }
        
    void process(Triple t, Exp body) {
        BasicGraphPattern bgp = ast.bgp(t);
        res.add(bgp);
        List<Variable> list = t.getVariables();
        if (!list.isEmpty()) {
            for (Exp exp : body) {
                if (exp.isFilter() && !exp.getFilter().isTermExistRec()) {
                    if (exp.getFilter().isBound(list)) {
                        bgp.add(exp);
                    }
                }
            }
        }
    }
    
    
}
