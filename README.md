# General informations
Corese is a Semantic Web Factory (triple store & SPARQL endpoint) implementing RDF, RDFS, SPARQL 1.1 Query & Update.

This version is a fork with Uncertainty querying enabled (mUnc vocabulary)
* Contact: ahmed-el-amine at inria.fr
* Licence: Open source software with Licence CeCILL-C (aka LGPL).
* Main forge: https://github.com/Wimmics/corese

# Compilation from source
To download the source code:

    git clone https://github.com/djebR/corese.git

or

    git clone git@github.com:djebR/corese.git


It should create a corese directory.

    cd corese
    mvn -Dmaven.test.skip=true package

# Features:
* Distributed Query Processing
* Corese HTTP server
* SPARQL Inference Rules
* SPARQL Template Transformation Language
* SPARQL approximate search
* SPARQL Property Path extensions
* SPIN Syntax
* RDF Graph as Query Graph Pattern
* SQL extension function
* (TODO) Uncertainty vocabulary integration
* (TODO) Triple Stored Functions