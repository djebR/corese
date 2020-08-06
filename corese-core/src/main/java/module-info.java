module fr.inria.corese.corese_core {
    requires fr.inria.corese.kgram;
    requires fr.inria.corese.sparql;
    requires org.slf4j;
    requires sesame.rio.api;
    requires java.xml;
    requires fr.inria.corese.compiler;
    requires jsonld.java;
    requires semargl.core;
    requires sesame.model;
    //requires fr.inria.corese.arp;
    requires arp;
    requires java.logging;
    requires java.sql;
    requires java.ws.rs;
    requires java.management;
    requires commons.lang;
    requires semargl.rdfa;
    requires jdk.management;

    exports fr.inria.corese.core.load;
    exports fr.inria.corese.core;
    exports fr.inria.corese.core.query;
    exports fr.inria.corese.core.rule;
    exports fr.inria.corese.core.workflow;
    exports fr.inria.corese.core.transform;
    exports fr.inria.corese.core.util;
    exports fr.inria.corese.core.print;
    exports fr.inria.corese.core.pipe;
    exports fr.inria.corese.core.api;
    exports fr.inria.corese.core.edge;
    exports fr.inria.corese.core.logic;
    exports fr.inria.corese.core.producer;

//    requires fr.inria.corese.compiler;
//    requires org.slf4j;
//    requires jsonld.java;
//    requires arp;
//    requires java.sql;
//    requires semargl.core;
//    requires semargl.rdfa;
//    requires fr.inria.corese.kgram;
//    requires fr.inria.corese.sparql;
//    requires sesame.model;
//    requires sesame.rio.api;
//    requires java.ws.rs;
//    requires commons.lang;
}
