package fr.inria.corese.sparql.triple.function.template;

import fr.inria.corese.sparql.api.Computer;
import fr.inria.corese.sparql.api.IDatatype;
import fr.inria.corese.sparql.triple.function.term.Binding;
import fr.inria.corese.kgram.api.query.Environment;
import fr.inria.corese.sparql.exceptions.EngineException;
import fr.inria.corese.kgram.api.query.Producer;
import fr.inria.corese.sparql.api.TransformProcessor;
import fr.inria.corese.sparql.exceptions.SafetyException;
import fr.inria.corese.sparql.triple.parser.Access;
import fr.inria.corese.sparql.triple.parser.NSManager;

/**
 *
 * @author Olivier Corby, Wimmics INRIA I3S, 2018
 *
 */
public class ApplyTemplatesWith extends TemplateFunction {  
        
    public ApplyTemplatesWith(String name){
        super(name);
        setArity(1);
    }
    
    /**
     * st:apply-templates-with(st:turtle, ?x)
     */
    @Override
     public IDatatype eval(Computer eval, Binding b, Environment env, Producer p) throws EngineException {
        IDatatype[] param = evalArguments(eval, b, env, p, 0);
        if (param == null) {
            return null;
        }
        
        String uri = param[0].getLabel();
        
        if (reject(Access.Feature.LINKED_TRANSFORMATION, eval, b, env, p)
                && !NSManager.isPredefinedTransformation(uri)) {
            throw new SafetyException(LINKED_TRANSFORMATION_MESS);
        }

        TransformProcessor trans = eval.getTransformer(env, p, this, param[0], null);

        switch (param.length) {

            case 1:
                return trans.process(null, isAll(), getModality(), this, env);
            case 2:
                return trans.process(null, isAll(), getModality(), this, env, param[1], null);
            default:
                return trans.process(null, isAll(), getModality(), this, env, param[1], getParam(param, 1));
        }
    }
     
   
}

