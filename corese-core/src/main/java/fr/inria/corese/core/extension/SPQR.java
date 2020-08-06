package fr.inria.corese.core.extension;

import fr.inria.corese.sparql.api.IDatatype;
import fr.inria.corese.sparql.datatype.DatatypeMap;
import fr.inria.corese.sparql.triple.function.extension.*;


/**
 * Code generated by LDScript Java compiler for SPARQL extension functions 
 * new JavaCompiler("SHACL").compile("http://ns.inria.fr/sparql-template/function/datashape/main.rq") 
 * function called with: 
 * prefix java: <function://fr.inria.corese.core.extension.SHACL> 
 * java:sh_shacl() 
 *
 * Olivier Corby - Wimmics Inria I3S - Wed Oct 30 22:04:48 CET 2019 
 */
public class SPQR extends Core { 

static final IDatatype _cst_0 = DatatypeMap.newInstance("M");
static final IDatatype _cst_1 = DatatypeMap.newInstance("X");
static final IDatatype _cst_2 = DatatypeMap.newInstance("L");
static final IDatatype _cst_3 = DatatypeMap.newInstance("C");
static final IDatatype _cst_4 = DatatypeMap.newInstance("D");
static final IDatatype _cst_5 = DatatypeMap.newInstance("I");
static final IDatatype _cst_6 = DatatypeMap.newInstance("V");
static final IDatatype _cst_7 = DatatypeMap.newResource("http://www.w3.org/2001/XMLSchema#integer");
static final IDatatype _cst_8 = DatatypeMap.newInstance("");

void test(IDatatype dt) {
    strstarts(dt, dt);
}

public IDatatype spqr_romain(IDatatype n) {
  return spqr_spqr(n);
}

public IDatatype spqr_r1000(IDatatype n) {
  return spqr_rep(_cst_0, n);
}

public IDatatype spqr_digit(IDatatype r) {
  return spqr_parse(r);
}

public IDatatype spqr_r10(IDatatype n) {
  return spqr_num(n, _cst_1, _cst_2, _cst_3);
}

public IDatatype spqr_spqr(IDatatype n) {
  if (n.lt(DatatypeMap.newInteger(10)).booleanValue()) {
    return spqr_r1(n);
  }
  else if (n.lt(DatatypeMap.newInteger(100)).booleanValue()) {
    IDatatype cc = spqr_div(n, DatatypeMap.newInteger(10));
    IDatatype r = spqr_mod(n, DatatypeMap.newInteger(10));
    return concat(spqr_r10(cc), spqr_spqr(r));
  }
  else if (n.lt(DatatypeMap.newInteger(1000)).booleanValue()) {
    IDatatype cc = spqr_div(n, DatatypeMap.newInteger(100));
    IDatatype r = spqr_mod(n, DatatypeMap.newInteger(100));
    return concat(spqr_r100(cc), spqr_spqr(r));
  }
  else if (n.lt(DatatypeMap.newInteger(10000)).booleanValue()) {
    IDatatype cc = spqr_div(n, DatatypeMap.newInteger(1000));
    IDatatype r = spqr_mod(n, DatatypeMap.newInteger(1000));
    return concat(spqr_r1000(cc), spqr_spqr(r));
  }
  else {
    return n;
  }
}

public IDatatype spqr_r100(IDatatype n) {
  return spqr_num(n, _cst_3, _cst_4, _cst_0);
}

public IDatatype spqr_r1(IDatatype n) {
  return spqr_num(n, _cst_5, _cst_6, _cst_1);
}

public IDatatype xt_romain(IDatatype n) {
  return spqr_spqr(n);
}

public IDatatype spqr_parse(IDatatype s) {
  if (DatatypeMap.strlen(s).eq(DatatypeMap.ZERO).booleanValue()) {
    return DatatypeMap.ZERO;
  }
  else {
    IDatatype ff = substr(s, DatatypeMap.ONE, DatatypeMap.ONE);
    if (ff.eq(_cst_5).booleanValue()) {
      return spqr_step(s, _cst_5, _cst_6, _cst_1, DatatypeMap.ONE, DatatypeMap.FIVE, DatatypeMap.newInteger(10));
    }
    else if (ff.eq(_cst_6).booleanValue()) {
      return DatatypeMap.FIVE.plus(spqr_parse(substr(s, DatatypeMap.TWO)));
    }
    else if (ff.eq(_cst_1).booleanValue()) {
      return spqr_step(s, _cst_1, _cst_2, _cst_3, DatatypeMap.newInteger(10), DatatypeMap.newInteger(50), DatatypeMap.newInteger(100));
    }
    else if (ff.eq(_cst_2).booleanValue()) {
      return DatatypeMap.newInteger(50).plus(spqr_parse(substr(s, DatatypeMap.TWO)));
    }
    else if (ff.eq(_cst_3).booleanValue()) {
      return spqr_step(s, _cst_3, _cst_4, _cst_0, DatatypeMap.newInteger(100), DatatypeMap.newInteger(500), DatatypeMap.newInteger(1000));
    }
    else if (ff.eq(_cst_4).booleanValue()) {
      return DatatypeMap.newInteger(500).plus(spqr_parse(substr(s, DatatypeMap.TWO)));
    }
    else if (ff.eq(_cst_0).booleanValue()) {
      return DatatypeMap.newInteger(1000).plus(spqr_parse(substr(s, DatatypeMap.TWO)));
    }
    else {
      return DatatypeMap.ZERO;
    }
  }
}

public IDatatype xt_digit(IDatatype r) {
  return spqr_parse(r);
}

public IDatatype spqr_mod(IDatatype aa, IDatatype bb) {
  return aa.minus(bb.mult(spqr_div(aa, bb))).cast(_cst_7);
}

public IDatatype spqr_div(IDatatype aa, IDatatype bb) {
  return floor(aa.div(bb)).cast(_cst_7);
}

public IDatatype spqr_rep(IDatatype s, IDatatype n) {
  if (n.eq(DatatypeMap.ZERO).booleanValue()) {
    return _cst_8;
  }
  else if (n.eq(DatatypeMap.ONE).booleanValue()) {
    return s;
  }
  else {
    return concat(s, spqr_rep(s, n.minus(DatatypeMap.ONE)));
  }
}

public IDatatype spqr_num(IDatatype n, IDatatype u, IDatatype ff, IDatatype t) {
  if (n.le(DatatypeMap.THREE).booleanValue()) {
    return spqr_rep(u, n);
  }
  else if (n.eq(DatatypeMap.FOUR).booleanValue()) {
    return concat(u, ff);
  }
  else if (n.lt(DatatypeMap.NINE).booleanValue()) {
    return concat(ff, spqr_rep(u, n.minus(DatatypeMap.FIVE)));
  }
  else if (n.eq(DatatypeMap.NINE).booleanValue()) {
    return concat(u, t);
  }
  else {
    return _cst_8;
  }
}

public IDatatype spqr_step(IDatatype s, IDatatype su, IDatatype sc, IDatatype sd, IDatatype u, IDatatype cc, IDatatype dd) {
  if (DatatypeMap.strlen(s).eq(DatatypeMap.ONE).booleanValue()) {
    return u;
  }
  else {
    IDatatype r = substr(s, DatatypeMap.TWO, DatatypeMap.ONE);
    if (r.eq(sc).booleanValue()) {
      return cc.minus(u).plus(spqr_parse(substr(s, DatatypeMap.THREE)));
    }
    else if (r.eq(sd).booleanValue()) {
      return dd.minus(u).plus(spqr_parse(substr(s, DatatypeMap.THREE)));
    }
    else {
      return u.plus(spqr_parse(substr(s, DatatypeMap.TWO)));
    }
  }
}

}
