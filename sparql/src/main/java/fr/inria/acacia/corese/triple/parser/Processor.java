package fr.inria.acacia.corese.triple.parser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import fr.inria.acacia.corese.api.IDatatype;
import fr.inria.acacia.corese.cg.datatype.function.SQLFun;
import fr.inria.acacia.corese.cg.datatype.function.VariableResolver;
import fr.inria.acacia.corese.cg.datatype.function.XPathFun;
import fr.inria.acacia.corese.triple.cst.RDFS;
import fr.inria.acacia.corese.triple.cst.KeywordPP;
import fr.inria.edelweiss.kgram.api.core.ExpPattern;
import fr.inria.edelweiss.kgram.api.core.Expr;
import fr.inria.edelweiss.kgram.api.core.ExprType;
import java.util.HashMap;

public class Processor {
	private static Logger logger = Logger.getLogger(Processor.class);

	static final String functionPrefix = KeywordPP.CORESE_PREFIX;
        static final String EXT      = NSManager.EXT;
        static final String EXT_PREF = NSManager.EXT_PREF + ":";
        static final String SPARQL   = NSManager.SPARQL;       
        static final String KGRAM    = NSManager.KGRAM;
        static final String KPREF    = NSManager.KPREF+":";
        static final String STL      = NSManager.STL;
        static final String CUSTOM   = NSManager.CUSTOM;
        
	public static final String BOUND    = "bound";
	public static final String COUNT    = "count";
	public static final String INLIST   = Term.LIST;
	public static final String XT_LIST     = EXT+"list";
	public static final String XT_IOTA     = EXT+"iota";
	public static final String XT_REVERSE  = EXT+"reverse";
	public static final String XT_APPEND   = EXT+"append";
	public static final String XT_SORT     = EXT+"sort";
        
	public static final String IN  	 = "in";

	private static final String MIN = "min";
	private static final String MAX = "max";
	private static final String SUM = "sum";
	private static final String AVG = "avg";
	private static final String ISURI = "isURI";
	private static final String ISIRI = "isIRI";
	private static final String ISBLANK = "isBlank";
	private static final String ISLITERAL = "isLiteral";
	private static final String ISNUMERIC = "isNumeric";
	private static final String LANG = "lang";
	private static final String REGEX = "regex";
        private static final String APPROXIMATE = "approximate";
        private static final String APP_SIM = "sim";
	public static  final String MATCH = "match";
	private static final String LANGMATCH = "langMatches";
	private static final String STRDT = "strdt";
	private static final String STRLANG = "strlang";
	public static final String IF = "if";
	public static final String COALESCE = "coalesce";
	public static final String BNODE = "bnode";
	public static final String GROUPCONCAT = "group_concat";
	static final String SEPARATOR = "; separator=";
	private static final String SAMPLE = "sample";
        
	private static final String FUNCALL  = "funcall";
	private static final String EVAL  = "eval";
	private static final String RETURN  = "return";
	public static final String SEQUENCE  = "sequence";
	public static final String SET     = "set";
	public static final String LET     = "let";
        static final String FOR             = "for";
	private static final String MAP     = "map";
	private static final String MAPLIST = "maplist";
	private static final String MAPMERGE = "mapmerge";
	private static final String MAPSELECT = "mapselect";
	private static final String APPLY   = "apply";
        
        private static final String XT_SELF     = EXT + "self";
        private static final String XT_FIRST    = EXT + "first";
        private static final String XT_REST     = EXT + "rest";
        private static final String XT_GET      = EXT + "get";
        private static final String XT_GEN_GET  = EXT + "gget";
        private static final String FUN_XT_GET  = EXT_PREF + "gget";
        private static final String XT_SET      = EXT + "set";
        private static final String XT_CONS     = EXT + "cons";        
        //private static final String XT_CONCAT   = EXT + "concat";
        // private static final String XT_COUNT    = EXT + "count";
        private static final String XT_SIZE     = EXT + "size";      
        private static final String XT_GRAPH    = EXT + "graph";
        private static final String XT_SUBJECT  = EXT + "subject";
        private static final String XT_PROPERTY = EXT + "property";
        private static final String XT_OBJECT   = EXT + "object";
        private static final String XT_VALUE    = EXT + "value";
        private static final String XT_INDEX    = EXT + "index";
        private static final String XT_REJECT   = EXT + "reject";
        private static final String XT_VARIABLES= EXT + "variables";
        private static final String XT_EDGE     = EXT + "edge";
        private static final String XT_TRIPLE   = EXT + "triple";
        static public final String XT_MAIN     = EXT + "main";
        static public final String FUN_XT_MAIN = EXT_PREF + "main";
       

	private static final String PLENGTH = "pathLength";
	private static final String KGPLENGTH = KGRAM + "pathLength";
	private static final String PWEIGHT = "pathWeight";
	private static final String KGPWEIGHT = KGRAM +"pathWeight";

	private static final String DATATYPE = "datatype";
	private static final String STR = "str";
	private static final String XSDSTRING = RDFS.XSD + "string";
	private static final String URI = "uri";
	private static final String IRI = "iri";

	private static final String SELF = "self";
	private static final String DEBUG = "trace";

	static final String EXTERN 	= "extern";
	public static final String XPATH= Term.XPATH;
	static final String SQL 	= "sql";
	static final String KGXPATH     = KGRAM + "xpath";
	static final String KGSQL 	= KGRAM + "sql";
	static final String PROVENANCE 	= KGRAM + "provenance";
	static final String INDEX 	= KGRAM + "index";
	static final String ID          = KGRAM + "id";
	static final String TIMESTAMP 	= KGRAM + "timestamp";
	static final String TEST 	= KGRAM + "test";
	static final String STORE 	= KGRAM + "store";
	public static final String DESCRIBE 	= KGRAM + "describe";
	public static final String QUERY 	= KGRAM + "query";
	public static final String EXTENSION 	= KGRAM + "extension";
	public static final String KGEXTENSION 	= KPREF + "extension";
	
	static final String READ 		= KGRAM + "read"; 
	static final String WRITE 		= KGRAM + "write"; 
	static final String PPRINT 		= KGRAM + "pprint"; 
	static final String PPRINTWITH 		= KGRAM + "pprintWith"; 
	static final String PPRINTALL		= KGRAM + "pprintAll"; 
	static final String PPRINTALLWITH	= KGRAM + "pprintAllWith"; 
	static final String TEMPLATE		= KGRAM + "template"; 
	static final String TEMPLATEWITH	= KGRAM + "templateWith"; 
	static final String KG_EVAL 		= KGRAM + "eval";
        static final String PROLOG 		= KGRAM + "prolog";

	public static final String AGGREGATE    = "aggregate"; 
	public static final String STL_AGGREGATE    = STL + "aggregate"; 
	public static final String STL_DEFAULT      = STL + "default"; 
	public static final String STL_PROCESS      = STL + "process"; 
	public static final String STL_PROCESS_URI  = STL + "processURI"; 
        static final String FOCUS_NODE              = STL + "getFocusNode";
        
        static final String APPLY_TEMPLATES         = STL + "apply-templates";
        static final String APPLY_TEMPLATES_WITH    = STL + "apply-templates-with";
        static final String ATW                     = STL + "atw";
  	static final String APPLY_TEMPLATES_ALL     = STL + "apply-templates-all";        
  	static final String APPLY_TEMPLATES_GRAPH   = STL + "apply-templates-graph";        
  	static final String APPLY_TEMPLATES_NOGRAPH = STL + "apply-templates-nograph";        
  	static final String APPLY_TEMPLATES_WITH_ALL= STL + "apply-templates-with-all";        
   	static final String APPLY_TEMPLATES_WITH_GRAPH= STL + "apply-templates-with-graph";        
   	static final String APPLY_TEMPLATES_WITH_NOGRAPH= STL + "apply-templates-with-nograph";        
       
        // deprecated:
        static final String APPLY_ALL_TEMPLATES     = STL + "apply-all-templates";
        static final String APPLY_ALL_TEMPLATES_WITH= STL + "apply-all-templates-with";
        
        static final String STL_TEMPLATE            = STL + "template";
        static final String CALL_TEMPLATE           = STL + "call-template";
        static final String CALL_TEMPLATE_WITH      = STL + "call-template-with";
        static final String CTW                     = STL + "ctw";
	static final String STL_TURTLE              = STL + "turtle"; 
	static final String STL_URI                 = STL + "uri"; 
	static final String STL_URILITERAL          = STL + "uriLiteral"; 
	static final String STL_XSDLITERAL          = STL + "xsdLiteral"; 
	static final String STL_PROLOG              = STL + "prolog"; 
	static final String STL_LEVEL               = STL + "level"; 
        static final String STL_DEFINE              = STL + "define";
        static final String DEFINE                  = "define";
        static final String FUNCTION                = "function";
        static final String PACKAGE                 = "package";
        static final String EXPORT                  = "export";
        static final String LAMBDA                  = "lambda";
        static final String ERROR                   = "error";
        static final String MAPANY                  = "mapany";
        static final String MAPEVERY                = "mapevery";
      
        static final String STL_PREFIX              = STL + "prefix";
 	static final String STL_INDENT              = STL + "indent";
 	static final String STL_SELF                = STL + "self";
 	static final String STL_LOAD                = STL + "load";
 	static final String STL_IMPORT              = STL + "import";
	static final String STL_ISSTART             = STL + "isStart"; 
	static final String STL_SET                 = STL + "set"; 
	static final String STL_GET                 = STL + "get"; 
	static final String STL_EXPORT              = STL + "export"; 
	static final String STL_VSET                = STL + "vset"; 
	static final String STL_VGET                = STL + "vget"; 
        static final String STL_VISIT               = STL + "visit"; 
	static final String STL_ERRORS              = STL + "errors";
	static final String STL_VISITED             = STL + "visited";        
	static final String STL_BOOLEAN             = STL + "boolean"; 
        
	public static final String STL_GROUPCONCAT  = STL + "group_concat"; 
	public static final String STL_CONCAT       = STL + "concat"; 
	public static final String STL_NL           = STL + "nl"; 
	public static final String STL_AGGAND       = STL + "agg_and";
	public static final String STL_AGGLIST      = STL + "agg_list";
	public static final String STL_AND          = STL + "and";
	public static final String STL_NUMBER       = STL + "number";
	public static final String STL_INDEX        = STL + "index";
	public static final String STL_FUTURE       = STL + "future";
        
	public static final String FUN_NUMBER       = NSManager.STL_PREF + ":"  + "_n_";
	public static final String FUN_NL           = NSManager.STL_PREF + ":" + "nl"; 
        public static final String FUN_PROCESS      = NSManager.STL_PREF + ":" + "process"; 
        public static final String FUN_PROCESS_URI  = NSManager.STL_PREF + ":" + "processURI"; 
	public static final String FUN_INDENT       = NSManager.STL_PREF + ":" + "indent"; 
	public static final String FUN_CONCAT       = NSManager.STL_PREF + ":" + "concat"; 
	public static final String FUN_GROUPCONCAT  = NSManager.STL_PREF + ":" + "group_concat"; 
	public static final String FUN_AGGREGATE    = NSManager.STL_PREF + ":" + "aggregate"; 
	public static final String FUN_TURTLE       = NSManager.STL_PREF + ":" + "turtle"; 

	       
	static final String QNAME 	= KGRAM + "qname"; 
	static final String TURTLE 	= KGRAM + "turtle"; 
	static final String LEVEL 	= KGRAM + "level"; 
	static final String INDENT 	= KGRAM + "indent"; 
	static final String PPURI 	= KGRAM + "uri"; 
	static final String URILITERAL 	= KGRAM + "uriLiteral"; 
	static final String VISITED     = KGRAM + "isVisited"; 
	static final String ISSKOLEM    = KGRAM + "isSkolem"; 
	static final String SKOLEM      = KGRAM + "skolem"; 

	static final String UNNEST = "unnest";
	static final String KGUNNEST = KGRAM +UNNEST;
	static final String SYSTEM = "system";
	static final String GROUPBY = "groupBy";

	static final String KG_SPARQL= KGRAM + "sparql";
	static final String SIMILAR  = KGRAM + "similarity";
	static final String CSIMILAR = KGRAM + "cSimilarity";
	static final String PSIMILAR = KGRAM + "pSimilarity";
	static final String ANCESTOR = KGRAM + "ancestor";
	static final String DEPTH    = KGRAM + "depth";
	static final String GRAPH    = KGRAM + "graph";
	static final String NODE     = KGRAM + "node";
	static final String GET_OBJECT      = KGRAM + "getObject";
	static final String SET_OBJECT      = KGRAM + "setObject";
	static final String GETP     = KGRAM + "getProperty";
	static final String SETP     = KGRAM + "setProperty";
	static final String LOAD     = KGRAM + "load";
	static final String NUMBER   = KGRAM + "number";
	static final String EVEN     = KGRAM + "even";
	static final String ODD      = KGRAM + "odd";
	static final String DISPLAY  = KGRAM + "display";
	static final String EXTEQUAL = KGRAM + "equals";
	static final String EXTCONT  = KGRAM + "contains";
	static final String PROCESS  = KGRAM + "process";
	static final String ENV  	 = KGRAM + "env";
	public static final String PATHNODE = KGRAM + "pathNode";
	static final String SLICE       = KGRAM + "slice";

	static final String EXIST 	= Term.EXIST;
	static final String STRLEN 	= "strlen";
	static final String SUBSTR 	= "substr";
	static final String UCASE 	= "ucase";
	static final String LCASE 	= "lcase";
	static final String ENDS 	= "strends";
	static final String STARTS 	= "strstarts";
	static final String CONTAINS = "contains";
	static final String ENCODE 	= "encode_for_uri";
	public static final String CONCAT 	= "concat"; 
	static final String STRBEFORE 	= "strbefore"; 
	static final String STRAFTER 	= "strafter"; 
	static final String STRREPLACE 	= "replace"; 
	static final String UUID 		= "uuid"; 
	static final String STRUUID 	= "struuid"; 

	
	static final String RANDOM 	= "rand"; 
	static final String ABS 	= "abs"; 
	static final String CEILING = "ceil"; 
	static final String FLOOR 	= "floor"; 
	static final String ROUND 	= "round";
	static final String POWER 	= "power";
        

	static final String NOW 	= "now"; 
	static final String YEAR 	= "year"; 
	static final String MONTH 	= "month"; 
	static final String DAY 	= "day"; 
	static final String HOURS 	= "hours";
	static final String MINUTES = "minutes";
	static final String SECONDS = "seconds";
	static final String TIMEZONE = "timezone";
	static final String TZ 		= "tz";

	static final String MD5 	= "md5";
	static final String SHA1 	= "sha1";
	static final String SHA224 	= "sha224";
	static final String SHA256	= "sha256";
	static final String SHA384 	= "sha384";
	static final String SHA512 	= "sha512";
	
        static final String KG_POWER 	= KGRAM + "power";
        static final String KG_PLUS 	= KGRAM + "plus";
        static final String KG_MINUS 	= KGRAM + "minus";
        static final String KG_MULT 	= KGRAM + "mult";
        static final String KG_DIV 	= KGRAM + "div";
        static final String KG_CONCAT 	= KGRAM + "concat";
        static final String KG_APPEND 	= KGRAM + "append";
        static final String KG_AND 	= KGRAM + "and";
        static final String KG_OR 	= KGRAM + "or";
        static final String KG_NOT 	= KGRAM + "not";
        static final String KG_EQUAL 	= KGRAM + "equal";
        static final String KG_DIFF 	= KGRAM + "diff";
       
	static final String XT_POWER 	= EXT + "power";
        static final String XT_PLUS 	= EXT + "plus";
        static final String XT_MINUS 	= EXT + "minus";
        static final String XT_MULT 	= EXT + "mult";
        static final String XT_DIV 	= EXT + "divis";
        static final String XT_AND 	= EXT + "and";
        static final String XT_OR 	= EXT + "or";
        static final String XT_NOT 	= EXT + "not";
        static final String XT_EQUAL 	= EXT + "equal";
        static final String XT_DIFF 	= EXT + "diff";
        static final String XT_DISPLAY 	= EXT + "display";
        static final String XT_TUNE 	= EXT + "tune";
        
        public static final String[] aggregate = 
	{AVG, COUNT, SUM, MIN, MAX, SAMPLE, 
         GROUPCONCAT, STL_GROUPCONCAT, STL_AGGAND, STL_AGGLIST, STL_AGGREGATE, AGGREGATE};
        
        // do not generate xt:set for set
        static final HashMap<String, Boolean> fixed; 
	
	Term term;
	List<Expr> lExp;
        // function definition for UNDEF function call
	private Expr define;
	Pattern pat;
	Matcher match;
	XPathFun xfun;
	SQLFun sql;
	VariableResolver resolver;
	Object processor;
	Method fun;
	ExpPattern pattern;
	boolean isCorrect = true;
	public static int count = 0;
	private static final int IFLAG[] = {
		Pattern.DOTALL, Pattern.MULTILINE, Pattern.CASE_INSENSITIVE,
		Pattern.COMMENTS};
	static final String SFLAG[] = {"s", "m", "i", "x"};
        		
	public static HashMap<String, Integer> table;
	public static HashMap<Integer, String> tname, toccur;
        static ASTQuery ast;
    private String name;
    
     static {
            fixed = new HashMap();
            fixed.put(SET, true);
            init();
            deftable();
        }
     
     static void init(){
         ast = ASTQuery.create();
         ast.setBody(BasicGraphPattern.create());
     }
    
	Processor(){            
        }
        
	Processor(Term t){
		term = t;
	}
        
        public static Processor create(){
            return new Processor();
        }
	
	
	// Exp
	
	public List<Expr> getExpList(){
		return lExp;
	}
	
	// filter(exist {PAT})
	public ExpPattern getPattern(){
		return pattern;
	}
	
	public void setPattern(ExpPattern pat){
		 pattern = pat;
	}

	
	Expr getExp(int i){
		return lExp.get(i);
	}
        
        void setExp(int i, Expr e){
            if (i < lExp.size()){
                lExp.set(i, e);
            }
            else if (i == lExp.size()){
                lExp.add(i, e);
            }
        }
        
        void addExp(int i, Expr e){
            lExp.add(i, e);
        }

	
	void setArguments(){
		if (lExp == null){
			lExp = new ArrayList<Expr>();
			for (Expr e : term.getArgs()){
				lExp.add(e);
			}
		}
	}
	
	public int arity(){
		return lExp.size();
	}
	
	
	public int type(){
		return term.type();
	}
        
        public int oper(){
		return term.oper();
	}
        
        void setOper(int n){
            term.setOper(n);
        }
        
        void setType(int n){
            term.setType(n);
        }
	
	public void type(ASTQuery ast){
                if (type() != ExprType.UNDEF){
                    // already done
                }
                else if (term.isFunction()){
			setType(ExprType.FUNCTION);
			setOper(getOper());
                        preprocess(ast);
		}
		else if (term.isAnd()){
			setType(ExprType.BOOLEAN);
			setOper(ExprType.AND);
		}
		else if (term.isOr()){
			setType(ExprType.BOOLEAN);
			setOper(ExprType.OR);
		}
		else if (term.isNot()){
			setType(ExprType.BOOLEAN);
			setOper(ExprType.NOT);
		}
		else {
			setType(ExprType.TERM);
			setOper(getOper());
		}
		
		if (oper() == ExprType.UNDEF){
			if (term.isPathExp()){
				// Property Path Exp
			}
			else {
                            ast.undefined(term);
			}
		}
		
	}
                            
        void prepare(ASTQuery ast) {
            name = term.getLabel();
        if (term.isFunction()) {
            switch (oper()) {
                case ExprType.IN:
                    compileInList();
                    break;
                case ExprType.HASH:
                    compileHash();
                    break;
                case ExprType.URI:
                    compileURI(ast);
                    break;
                case ExprType.CAST:
                    compileCast();
                    break;
                case ExprType.REGEX:
                    compileRegex();
                    break;
                case ExprType.STRREPLACE:
                    compileReplace();
                    break;
                case ExprType.XPATH:
                    compileXPath(ast);
                    break;
                case ExprType.SQL:
                    compileSQL(ast);
                    break;
                case ExprType.EXTERNAL:
                    compileExternal(ast);
                    break;
                case ExprType.CUSTOM:
                    compileCustom(ast);
                    break;
            }
        }
        
        setArguments();		
        check(ast);
    }
        
	
	// TODO: error message
	void check(ASTQuery ast){
		if (term.isAggregate()){ 
                   if (term.getName().equalsIgnoreCase(COUNT)){
                        if (term.getArity() > 1){
                            ast.setCorrect(false);
                        }
                    }
                    else if (term.getArity() != 1){
                           ast.setCorrect(false);
                    }
                }
	}
	
	
	static void deftable(){
		table = new HashMap<String, Integer>();
		tname = new HashMap<Integer, String>();
		toccur = new HashMap<Integer, String>();

		defoper("<", 	ExprType.LT);
		defoper("<=", 	ExprType.LE);
		defoper("=", 	ExprType.EQ);
		defoper("!=", 	ExprType.NEQ);
		defoper(">", 	ExprType.GT);
		defoper(">=", 	ExprType.GE);
		defoper("~", 	ExprType.CONT);
		defoper(KGRAM+"tilda", 	ExprType.CONT);
		defoper("^", 	ExprType.START);
		defoper(IN, 	ExprType.IN);
		defoper("+", 	ExprType.PLUS);
		defoper("-", 	ExprType.MINUS);
		defoper("*", 	ExprType.MULT);
		defoper("/", 	ExprType.DIV);
				
		defoper(BOUND, ExprType.BOUND);
		defoper(COUNT, 	ExprType.COUNT);
		defoper(MIN, 	ExprType.MIN);
		defoper(MAX, 	ExprType.MAX);
		defoper(SUM, 	ExprType.SUM);
		defoper(AVG, 	ExprType.AVG);
		defoper(ISURI, 	ExprType.ISURI);
		defoper(ISIRI, 	ExprType.ISURI);
		defoper(ISBLANK, ExprType.ISBLANK);
		defoper(ISLITERAL, ExprType.ISLITERAL);
		defoper(ISNUMERIC, ExprType.ISNUMERIC);
		defoper(LANG, 	ExprType.LANG);
		defoper(LANGMATCH, ExprType.LANGMATCH);
		
		defoper(STRDT, 		ExprType.STRDT);
		defoper(STRLANG, 	ExprType.STRLANG);
		defoper(BNODE, 		ExprType.BNODE);
		defoper(PATHNODE, 	ExprType.PATHNODE);
		defoper(COALESCE, 	ExprType.COALESCE);
		defoper(IF, 		ExprType.IF);
		defoper(GROUPCONCAT,    ExprType.GROUPCONCAT);
		defoper(STL_AGGAND,     ExprType.AGGAND);
		defoper(STL_AGGLIST,    ExprType.AGGLIST);
		defoper(STL_AND,        ExprType.STL_AND);
		defoper(SAMPLE, 	ExprType.SAMPLE);
		defoper(INLIST,         ExprType.INLIST);
		defoper(ISSKOLEM,       ExprType.ISSKOLEM);
		defoper(SKOLEM,         ExprType.SKOLEM);
		defoper(RETURN,         ExprType.RETURN);
		defoper(SEQUENCE,       ExprType.SEQUENCE);
		defsysoper(LET,            ExprType.LET);
		defoper(SET,            ExprType.SET);
		defoper(XT_LIST,        ExprType.LIST);
		defoper(XT_IOTA,        ExprType.IOTA);
		defoper(XT_REVERSE,     ExprType.XT_REVERSE);
		defoper(XT_APPEND,      ExprType.XT_APPEND);
		defoper(XT_SORT,        ExprType.XT_SORT);
                
		defsysoper(FUNCALL,        ExprType.FUNCALL);                
		defsysoper(EVAL,           ExprType.EVAL);                
		defsysoper(APPLY,          ExprType.APPLY);
		defsysoper(MAP,            ExprType.MAP);
		defsysoper(FOR,            ExprType.FOR);
		defsysoper(MAPLIST,        ExprType.MAPLIST);
		defsysoper(MAPMERGE,       ExprType.MAPMERGE);
		defsysoper(MAPSELECT,      ExprType.MAPSELECT);
		defsysoper(MAPANY,         ExprType.MAPANY);
		defsysoper(MAPEVERY,       ExprType.MAPEVERY);
                
		//defoper(XT_CONCAT,      ExprType.XT_CONCAT);
//		defoper(XT_CONCAT,      ExprType.CONCAT);
		defoper(XT_CONS,        ExprType.XT_CONS);
		defoper(XT_FIRST,       ExprType.XT_FIRST);
		defoper(XT_REST,        ExprType.XT_REST);
		defoper(XT_SELF,        ExprType.SELF);
		defoper(XT_GET,         ExprType.XT_GET);
		defoper(XT_GEN_GET,     ExprType.XT_GEN_GET);
		defoper(XT_SET,         ExprType.XT_SET);
 		defoper(XT_REJECT,      ExprType.XT_REJECT);
               
		//defoper(XT_COUNT,        ExprType.XT_COUNT);
		defoper(XT_SIZE,         ExprType.XT_COUNT);		
		defoper(XT_GRAPH,        ExprType.XT_GRAPH);
		defoper(XT_SUBJECT,      ExprType.XT_SUBJECT);
		defoper(XT_PROPERTY,     ExprType.XT_PROPERTY);
		defoper(XT_OBJECT,       ExprType.XT_OBJECT);
		defoper(XT_VALUE,        ExprType.XT_VALUE);                
		defoper(XT_INDEX,        ExprType.XT_INDEX);
		defoper(XT_VARIABLES,    ExprType.XT_VARIABLES);
		defoper(XT_EDGE,         ExprType.XT_EDGE);
		defoper(XT_TRIPLE,       ExprType.XT_TRIPLE);
                
		defsysoper(REGEX, 		ExprType.REGEX);
                defoper(APPROXIMATE,	ExprType.APPROXIMATE);
                defoper(APP_SIM,	ExprType.APP_SIM);
		defoper(DATATYPE, 	ExprType.DATATYPE);
		defoper(STR, 		ExprType.STR);
		defoper(XSDSTRING, 	ExprType.XSDSTRING);
		defoper(URI, 		ExprType.URI);
		defoper(IRI, 		ExprType.URI);
		defoper(SELF, 		ExprType.SELF);
		defoper(DEBUG, 		ExprType.DEBUG);

		defoper(MATCH, 	ExprType.SKIP);
		defoper(PLENGTH, ExprType.LENGTH);
		defoper(KGPLENGTH, ExprType.LENGTH);
		defoper(KGPWEIGHT, ExprType.PWEIGHT);

		defsysoper(XPATH, 	ExprType.XPATH);
		defsysoper(KGXPATH, 	ExprType.XPATH);
		defsysoper(SQL, 	ExprType.SQL);
		defoper(KGSQL, 	ExprType.SQL);
		defoper(KG_SPARQL, ExprType.KGRAM);
		defoper(EXTERN, ExprType.EXTERN);
		defoper(UNNEST, ExprType.UNNEST);
		defoper(KGUNNEST, ExprType.UNNEST);
		defsysoper(EXIST,  ExprType.EXIST);
		defoper(SYSTEM, ExprType.SYSTEM);
		defoper(GROUPBY, ExprType.GROUPBY);
		
		defoper(READ,           ExprType.READ);
		defoper(WRITE,          ExprType.WRITE);
		defoper(QNAME,          ExprType.QNAME);
		defoper(PROVENANCE, 	ExprType.PROVENANCE);
 		defoper(INDEX,          ExprType.INDEX);
 		defoper(ID,             ExprType.ID);
 		defoper(TIMESTAMP,      ExprType.TIMESTAMP);
 		defoper(TEST,           ExprType.TEST);
 		defoper(DESCRIBE,       ExprType.DESCRIBE);
 		defoper(STORE,          ExprType.STORE);
 		defoper(QUERY,          ExprType.QUERY);
 		defoper(EXTENSION,      ExprType.EXTENSION);
               
		//defoper(PPRINT, 	ExprType.APPLY_TEMPLATES);
		defoper(KG_EVAL, 		ExprType.APPLY_TEMPLATES);
//		defoper(PPRINTWITH, 	ExprType.APPLY_TEMPLATES_WITH);
//		defoper(PPRINTALL, 	ExprType.APPLY_TEMPLATES_ALL);
//		defoper(PPRINTALLWITH, 	ExprType.APPLY_TEMPLATES_WITH_ALL);
		defoper(TEMPLATE, 	ExprType.CALL_TEMPLATE);
		defoper(TEMPLATEWITH, 	ExprType.CALL_TEMPLATE_WITH);
		defoper(TURTLE,         ExprType.TURTLE);                
                defoper(FOCUS_NODE,     ExprType.FOCUS_NODE);
                
                defoper(APPLY_TEMPLATES,            ExprType.APPLY_TEMPLATES);
		defoper(APPLY_TEMPLATES_ALL,        ExprType.APPLY_TEMPLATES_ALL);
		defoper(APPLY_TEMPLATES_GRAPH,      ExprType.APPLY_TEMPLATES_GRAPH);
		defoper(APPLY_TEMPLATES_NOGRAPH,    ExprType.APPLY_TEMPLATES_NOGRAPH);
		defoper(APPLY_TEMPLATES_WITH,       ExprType.APPLY_TEMPLATES_WITH);
		defoper(ATW,                        ExprType.APPLY_TEMPLATES_WITH);
		defoper(APPLY_TEMPLATES_WITH_ALL,   ExprType.APPLY_TEMPLATES_WITH_ALL);                
		defoper(APPLY_TEMPLATES_WITH_GRAPH, ExprType.APPLY_TEMPLATES_WITH_GRAPH);
		defoper(APPLY_TEMPLATES_WITH_NOGRAPH, ExprType.APPLY_TEMPLATES_WITH_NOGRAPH);
		defoper(CALL_TEMPLATE,              ExprType.CALL_TEMPLATE);
		defoper(CALL_TEMPLATE_WITH,         ExprType.CALL_TEMPLATE_WITH);
		defoper(STL_TEMPLATE,               ExprType.STL_TEMPLATE);
		defoper(CTW,                        ExprType.CALL_TEMPLATE_WITH);
                
                // 3 deprecated:
		defoper(APPLY_ALL_TEMPLATES, 	ExprType.APPLY_TEMPLATES_ALL);
		defoper(APPLY_ALL_TEMPLATES_WITH,ExprType.APPLY_TEMPLATES_WITH_ALL);
                
                defoper(STL_PROCESS,            ExprType.STL_PROCESS);
                defoper(STL_PROCESS_URI,        ExprType.STL_PROCESS_URI);
		defoper(STL_TURTLE,             ExprType.TURTLE);
                defoper(STL_URI,                ExprType.PPURI);
                defoper(STL_PROLOG,             ExprType.PROLOG);
                defoper(STL_PREFIX,             ExprType.STL_PREFIX);
		defoper(STL_INDENT,             ExprType.INDENT);
		defoper(STL_LEVEL,              ExprType.LEVEL);
		defoper(STL_NL,                 ExprType.STL_NL);
		defoper(STL_SELF,               ExprType.SELF);
		defoper(STL_URILITERAL, 	ExprType.URILITERAL);
		defoper(STL_XSDLITERAL,         ExprType.XSDLITERAL);
		defoper(STL_NUMBER,             ExprType.STL_NUMBER);
		defoper(STL_INDEX,              ExprType.STL_INDEX);
		defoper(STL_FUTURE,             ExprType.STL_FUTURE);
		defoper(STL_LOAD,               ExprType.STL_LOAD);
		defoper(STL_IMPORT,             ExprType.STL_IMPORT);
                defoper(STL_ISSTART,            ExprType.STL_ISSTART);
                defoper(STL_SET,                ExprType.STL_SET);
                defoper(STL_GET,                ExprType.STL_GET);
                defoper(STL_EXPORT,             ExprType.STL_EXPORT);
                defoper(STL_VSET,               ExprType.STL_VSET);
                defoper(STL_VGET,               ExprType.STL_VGET);
                defoper(STL_VISIT,              ExprType.STL_VISIT);
                defoper(STL_VISITED,            ExprType.STL_VISITED);
                defoper(STL_ERRORS,             ExprType.STL_ERRORS);
                defoper(STL_BOOLEAN,            ExprType.STL_BOOLEAN);

		defoper(LEVEL,          ExprType.LEVEL);
		defoper(INDENT,         ExprType.INDENT);
		defoper(PPURI,          ExprType.PPURI);
		defoper(URILITERAL, 	ExprType.URILITERAL);
		defoper(STL_XSDLITERAL, ExprType.XSDLITERAL);
		defoper(VISITED,        ExprType.VISITED);
		defoper(PROLOG,         ExprType.PROLOG);
		defoper(PACKAGE,        ExprType.PACKAGE);
		defoper(EXPORT,         ExprType.PACKAGE);
		defsysoper(STL_DEFINE,     ExprType.FUNCTION);
		defsysoper(FUNCTION,       ExprType.FUNCTION);
		defoper(DEFINE,         ExprType.STL_DEFINE);                
		defoper(LAMBDA,         ExprType.LAMBDA);
		defoper(ERROR,          ExprType.ERROR);
                defoper(STL_DEFAULT,    ExprType.STL_DEFAULT);
                defoper(STL_CONCAT,     ExprType.STL_CONCAT);
                defoper(STL_GROUPCONCAT, ExprType.STL_GROUPCONCAT);
                defoper(STL_AGGREGATE,   ExprType.STL_AGGREGATE);
                defsysoper(AGGREGATE,       ExprType.AGGREGATE);

		defoper(SIMILAR, ExprType.SIM);
		defoper(CSIMILAR, ExprType.SIM);
		defoper(PSIMILAR, ExprType.PSIM);
		defoper(ANCESTOR, ExprType.ANCESTOR);
		defoper(DEPTH,   ExprType.DEPTH);
		defoper(GRAPH,   ExprType.GRAPH);
		defoper(NODE,    ExprType.NODE);
		defoper(GET_OBJECT,     ExprType.GET_OBJECT);
		defoper(SET_OBJECT,     ExprType.SET_OBJECT);
		defoper(GETP,    ExprType.GETP);
		defoper(SETP,    ExprType.SETP);
		
		defoper(LOAD,    ExprType.LOAD);
		defoper(NUMBER,  ExprType.NUMBER);
		defoper(EVEN,  ExprType.EVEN);
		defoper(ODD,   ExprType.ODD);
                
//                defoper(KG_POWER,  ExprType.POWER);
//                defoper(KG_PLUS,   ExprType.PLUS);
//                defoper(KG_MULT,   ExprType.MULT);
//                defoper(KG_MINUS,  ExprType.MINUS);
//                defoper(KG_DIV,    ExprType.DIV);
//                defoper(KG_CONCAT, ExprType.CONCAT);
//                defoper(KG_APPEND, ExprType.XT_APPEND);
//                defoper(KG_AND,    ExprType.AND);
//                defoper(KG_OR,     ExprType.OR);
//                defoper(KG_NOT,    ExprType.NOT);
//                defoper(KG_EQUAL,  ExprType.EQ);
//                defoper(KG_DIFF,   ExprType.NEQ);
                
                //defoper(XT_POWER,  ExprType.POWER);
                
                defextoper(XT_PLUS,   ExprType.PLUS);
                defoper(XT_MULT,   ExprType.MULT);
                defoper(XT_MINUS,  ExprType.MINUS);
                defoper(XT_DIV,    ExprType.DIV);
                defoper(XT_AND,    ExprType.AND);
                defoper(XT_OR,     ExprType.OR);
                defoper(XT_NOT,    ExprType.NOT);
                defoper(XT_EQUAL,  ExprType.EQ);
                defoper(XT_DIFF,   ExprType.NEQ);  
                defoper(XT_DISPLAY,ExprType.XT_DISPLAY);  
                defoper(XT_TUNE,   ExprType.XT_TUNE);  
                
		defoper(DISPLAY, ExprType.DISPLAY);
		defoper(EXTEQUAL,ExprType.EXTEQUAL);
		defoper(EXTCONT, ExprType.EXTCONT);
		defoper(PROCESS, ExprType.PROCESS);
		defoper(ENV, 	 ExprType.ENV);
		defoper(SLICE, 	 ExprType.SLICE);

		defoper(STRLEN, ExprType.STRLEN);
		defoper(SUBSTR, ExprType.SUBSTR);
		defoper(UCASE, 	ExprType.UCASE);
		defoper(LCASE, 	ExprType.LCASE);
		defoper(ENDS, 	ExprType.ENDS);
		defoper(STARTS, ExprType.STARTS);
		defoper(CONTAINS, ExprType.CONTAINS);
		defoper("strcontains", ExprType.CONTAINS);
		defoper(ENCODE, ExprType.ENCODE);
		defoper(CONCAT, ExprType.CONCAT);
		defoper(STRBEFORE, ExprType.STRBEFORE);
		defoper(STRAFTER, ExprType.STRAFTER);
		defoper(STRREPLACE, ExprType.STRREPLACE);
		defoper(UUID, ExprType.FUUID);
		defoper(STRUUID, ExprType.STRUUID);

		
		defoper(POWER,  ExprType.POWER);
		defoper(RANDOM, ExprType.RANDOM);
		defoper(ABS, 	ExprType.ABS);
		defoper(FLOOR, 	ExprType.FLOOR);
		defoper(ROUND, 	ExprType.ROUND);
		defoper(CEILING, ExprType.CEILING);

		defoper(NOW, 	ExprType.NOW);
		defoper(YEAR, 	ExprType.YEAR);
		defoper(MONTH, 	ExprType.MONTH);
		defoper(DAY, 	ExprType.DAY);
		defoper(HOURS, 	ExprType.HOURS);
		defoper(MINUTES, ExprType.MINUTES);
		defoper(SECONDS, ExprType.SECONDS);
		defoper(TIMEZONE, ExprType.TIMEZONE);
		defoper(TZ, 	ExprType.TZ);


		defoper(MD5, 	ExprType.HASH);
		defoper(SHA1, 	ExprType.HASH);
		defoper(SHA224, ExprType.HASH);
		defoper(SHA256, ExprType.HASH);
		defoper(SHA384, ExprType.HASH);
		defoper(SHA512, ExprType.HASH);

	}
	
	static void defoper(String key, int value){
            define(key, value);
        }
        
        static void defextoper(String key, int value){
            define(key, value);
            defExtension(key, 2);
        }
        
        static void defsysoper(String key, int value){
            define(key, value);
        }
        
	static void define(String key, int value){
                // isURI
		table.put(key.toLowerCase(), value);
                // rq:isURI
                table.put(SPARQL + key.toLowerCase(), value);                
		tname.put(value, key);  
	}
        
        static void defExtension(String key, int arity){
            String name = key.toLowerCase();
            if (! key.startsWith("http://")){
                name = SPARQL + key;
            }
            Function fun = ast.defExtension(name, key, arity);
            fun.setPublic(true);
        }
        
        public static ASTQuery getAST(){
            return ast;
        }
        
        boolean fixed(String name){
            return  fixed.containsKey(name);
        }
        
        public static void test(){
            for (String name : table.keySet()){
                if (! name.startsWith("http://")){
                    if (table.containsKey(KGRAM + name)){
                        System.out.println(name);
                    }
                }
            }
        }
	       
	
	int getOper(){
                return getOper(term.getLabel());
        }
                
        public int getOper(String name){
		Integer n = table.get(name.toLowerCase());
		if (n == null){
			if (name.startsWith(RDFS.XSDPrefix) || name.startsWith(RDFS.XSD) || 
				name.startsWith(RDFS.RDFPrefix) || name.startsWith(RDFS.RDF)){
				n = ExprType.CAST;
			}
                        else if (name.startsWith(CUSTOM)){
				n = ExprType.CUSTOM;
			}
			else if (name.startsWith(KeywordPP.CORESE_PREFIX)){
				n = ExprType.EXTERNAL;
			}
			else {
				n = ExprType.UNDEF;
			}
		}
		// draft: record occurrences during test case
		//toccur.put(n, name);
		return n;
	}
        	
	public static void finish(){
		for (Integer n : table.values()){
			if (! toccur.containsKey(n)){
				System.out.println("Missing test: " + tname.get(n));
			}
		}
	}
	
	
	/**
	 * xsd:integer(?x)
	 * ->
	 * cast(?x, xsd:integer, CoreseInteger)
	 */
	void compileCast(){
		// name = xsd:integer | ... | str
		String name = term.getName();
		Constant dt = Constant.createResource(name);
		dt.getDatatypeValue();
		// type = CoreseInteger
		Constant type = Constant.create(Constant.getJavaType(name), RDFS.xsdstring);
		type.getDatatypeValue();
		lExp = new ArrayList<Expr>();
		lExp.add(term.getArg(0));
		lExp.add(dt);
		lExp.add(type);
	}
        
        void preprocess(ASTQuery ast){
            switch (term.oper()){
                
                case ExprType.MAP:
                case ExprType.MAPLIST:
                case ExprType.MAPMERGE:
                case ExprType.MAPSELECT:
                case ExprType.MAPEVERY:
                case ExprType.MAPANY:
                case ExprType.APPLY:
                    processMap(ast);
                    break;
                    
                case ExprType.AGGREGATE:
                    processAggregate(ast);
                    break;
                                                      
                case ExprType.LET:
                    processLet(ast);
                    break;
                                  
                    
            }
        }
        
        /**
         * let (?x = exp, ?y = exp, exp)
         * ->
         * let (?x = exp, let (?y = exp, exp))
         * @param ast 
         */
        void processLet(ASTQuery ast){
            processMatch(ast);
        }
        
        
        /**
         * let (match(?x, ?p, ?y) = ?l) {}
         * ::=
         * let (?x = xt:get(?l, 0), ?p = xt:get(?l, 1), ?y = xt:get(?l, 2)) {} 
         * @param ast 
         */
       void processMatch(ASTQuery ast) {
            Expression match = term.getArg(0).getArg(0);
            Expression list  = term.getDefinition();

            if (match.isFunction() && match.getLabel().equals(Processor.MATCH)) {
                ExpressionList l = new ExpressionList();
                
                Variable var;
                if (list.isVariable()){
                    var = list.getVariable();                    
                }
                else {
                    // eval list exp once, store it in variable
                    var = Variable.create("?_var_let_");
                    l.add(ast.defLet(var, list));
                }
                
                int j = 0;
                for (Expression arg : match.getArgs()) {
                    Term fun = ast.createFunction(ast.createQName(FUN_XT_GET), var);
                    fun.add(Constant.createString(arg.getLabel()));
                    fun.add(Constant.create(j++));
                    Term t   = ast.defLet(arg.getVariable(), fun);
                    l.add(t);
                }
                
                Term let = ast.defineLet(l, term.getBody(), 0);
                term.setArgs(let.getArgs());          
            }
        }
        
        

        /**
         * map(xt:fun, ?list)
         * -> 
         * map(xt:fun(?x), ?list)
         * @param ast 
         */
      void processMap(ASTQuery ast) {
        if (term.getArgs().size() >= 2) {
            Expression fst = term.getArg(0);

            if (fst.isConstant()) {
                Term fun = ast.createFunction(fst.getConstant());
                int max = (term.oper() == ExprType.APPLY) ? 2 : term.getArgs().size() - 1;
                // create 2 variables for apply(kg:plus, ?list) for ?a + ?b
                for (int i = 0; i < max; i++) {
                    Variable var = ASTQuery.createVariable("?_map_var" + i);
                    fun.add(var);
                }
                term.setArg(0, fun);
            }
        }
    }
      
      // aggregate(?x, xt:mediane)
      void processAggregate(ASTQuery ast) {
        if (term.getArgs().size() == 2) {
            Expression rst = term.getArg(1);
            if (rst.isConstant()) {
                Term fun = ast.createFunction(rst.getConstant());
                Variable var = ASTQuery.createVariable("?_agg_var");
                fun.add(var);
                term.setArg(1, fun);
            }
        }
    } 

                     
        Term cstToFun(ASTQuery ast, Constant cst) {
            Variable var = ASTQuery.createVariable("_map_");
            Term fun = ast.createFunction(cst, var);
            return fun;
        }
	
	/**
	 * sha256(?x) ->
	 * hash("SHA-256", ?x)
	 */
	void compileHash(){
		String name = term.getName();
		if (name.startsWith("sha") || name.startsWith("SHA")){
			name = "SHA-" + name.substring(3);
		}
		term.setModality(name);
	}
	
	void compileURI(ASTQuery ast){
		String base = ast.getNSM().getBase();
		if (base!=null && base!=""){
			term.setModality(ast.getNSM().getBase());
		}
	}
	
	void compileInList(){
		// ?x in (a b)
		
	}

	
	/**
	 * term = regex(?x,  ".*toto",  ["i"])
	 * match.reset(string);
	 * boolean res = match.matches();
	 */
	void compileRegex(){
		String sflag = null;
                if (term.getArity() == 3){
			sflag = term.getArg(2).getName();
		}		
                if (term.getArg(1).isConstant()){
			compilePattern(term.getArg(1).getName(), sflag, true);
		}
	}
	
	
	void compilePattern(String patdtvalue, String sflag, boolean regex){		
		int flag = 0;
		if (sflag != null){ // flag argument "smix"
			for (int i = 0; i < IFLAG.length; i++){
				if (sflag.indexOf(SFLAG[i]) != -1){ //is flag[i] present
					flag =  flag | IFLAG[i]; // add the corresponding int flag
				}
			}
		}

		if (regex && 
                        !patdtvalue.startsWith("^") && !patdtvalue.startsWith(".*")){
			patdtvalue = ".*"+patdtvalue;
                }
		if (regex && 
                        !patdtvalue.endsWith("$") && !patdtvalue.endsWith(".*")){
			patdtvalue = patdtvalue+".*";
                }
		if (flag == 0){
			pat = Pattern.compile(patdtvalue);
                }
		else {
                    pat = Pattern.compile(patdtvalue, flag);
                }
		
		match = pat.matcher("");
	}
	
	void compileReplace(){
		if (term.getArg(1).isConstant()){
                    String sflag = null;
                    if (term.getArity() == 4){
                            sflag = term.getArg(3).getName();
                    }                    
                    compilePattern(term.getArg(1).getName(), sflag, false);
		}
	}
	
	// TODO: test if constant
	void compileReplace(String str){
		pat = Pattern.compile(str);
		match = pat.matcher("");
	}
	
	// replace('%abc@def#', '[^a-z0-9]', '-')
	public String replace(String str, String rep){           
		match.reset(str);
		String res = match.replaceAll(rep);
		return res;
	}	
	
	public boolean regex(String str, String exp){
		if (term.getArg(1).isVariable()){
                    String sflag = null;
                    if (term.getArity() == 3){
                            sflag = term.getArg(2).getName();
                    }	
                    compilePattern(exp, sflag, true);
		}
		match.reset(str);
		return match.matches();
	}
	
	void compileSQL(ASTQuery ast){
		//sql = new SQLFun();
	}
	
	// @deprecated
	public ResultSet sql(IDatatype uri, IDatatype login, IDatatype passwd, IDatatype query){
		return null; //return sql.sql(uri, login, passwd, query);
	}
	
	public ResultSet sql(IDatatype uri, IDatatype driver, IDatatype login, IDatatype passwd, IDatatype query){
		return null; //return sql.sql(uri, driver, login, passwd, query);
	}
	
	/**
	 * xpath(?g, '/book/title')
	 */
	void compileXPath(ASTQuery ast){
		xfun = new XPathFun();
		if (ast == null) ast = ASTQuery.create();
		xfun.init(ast.getNSM(),  !true);
	}
	
	public XPathFun getXPathFun(){
		return xfun;
	}
	
	public IDatatype xpath(IDatatype doc, IDatatype exp){
		try {
		IDatatype dt = xfun.xpath(doc, exp);
		return dt;
		}
		catch (RuntimeException e){
			logger.error("XPath error: " + e.getMessage());
		}
		return null;
	}
	
	public VariableResolver getResolver(){
		return xfun.getResolver();
	}
	
	public void setResolver(VariableResolver res){
		xfun.set(res);
	}
	

	public void setProcessor(Object obj){
		processor = obj;
	}
	
	public void setMethod(Method m){
		fun = m;
	}
	
	public Method getMethod() {
		return fun;
	}
	
	
	/**
	 * Load external method definition for ext:fun
	 * prefix ext: <function://package.className>
	 * ext:fun() 
	 */
	void compileExternal(ASTQuery ast)  {
		String oper = term.getLabel();
		String p ;
		String path ;
		Class c = null;  
		isCorrect = false;
		try {
			if (! oper.startsWith(functionPrefix)) {
				String message = "Undefined function: "+oper;
				if (oper.contains("://")) 
					message += "\nThe prefix should start with \""+functionPrefix+"\"";
				logger.warn(message);
				return ;
			}
			int lio = oper.lastIndexOf(".");
			if (lio == -1){
				logger.error("Undefined function: "+oper);
				return;
			}
			p = oper.substring(0, lio);
			path = p.substring(functionPrefix.length(),p.length());
			oper = oper.substring(p.length() + 1, oper.length());
			
			ClassLoader cl = getClass().getClassLoader(); 
			c = cl.loadClass(path);
			
			Class<Object>[] aclasses = new Class[term.getArity()];
			for (int i = 0; i < aclasses.length; i++) {
				aclasses[i] = Object.class;
			}
			
			setProcessor(c.newInstance());  
			setMethod(c.getMethod(oper, aclasses));
			isCorrect = true;
		} 
		
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * Eval external method
	 */
	public Object eval(Object[] args){
		if (! isCorrect) return null;
		try {
			return fun.invoke(processor, args);
		} 
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
        
        void compileCustom(ASTQuery ast){
            name = term.getLabel().substring(CUSTOM.length());
        }
        
        // for custom extension function 
        String getShortName(){
            return name;
        }

    /**
     * @return the define
     */
    public Expr getDefine() {
        return define;
    }

    /**
     * @param define the define to set
     */
    public void setDefine(Expr define) {
        this.define = define;
    }


}
