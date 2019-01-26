       
Ivy vulnerability finder
========================

[![Build Status](https://travis-ci.org/wladimirgrf/ivy.svg?branch=master)](https://travis-ci.org/wladimirgrf/ivy)

What is Ivy?
------------

Ivy is an open source web application, focused on brute force test and page scan. Analyse data to find vulnerabilities and detect risks on web sites. Designed to help systems prevent attacks.

License
-------

Ivy is released under the terms of the MIT license. See [LICENSE](LICENSE) for more
information or see https://opensource.org/licenses/MIT.

Development Process
-------------------

The contribution workflow is described in [CONTRIBUTING.md](CONTRIBUTING.md).

Getting started
---------------

To get started, it is best to have the latest JDK and Maven installed. The HEAD of the `master` branch contains the latest development code and various production releases are provided on feature branches.

Change indexBase property from [persistence.xml](https://github.com/wladimirgrf/ivy/blob/master/src/main/resources/META-INF/persistence.xml) for a local path

```xml
<property name="hibernate.search.default.indexBase" value="{index_path}"/>
```

Create a MySQL database by following the [context.xml](https://github.com/wladimirgrf/ivy/blob/master/src/main/webapp/META-INF/context.xml).

#### Building from the command line

To perform a full build use
```
mvn clean compile war:exploded -X
```
The outputs are under the `target` directory.

IDE Support
-----------

If you would like to build and run from a Maven/Java Dynamic Web-project-capable IDE, such as Eclipse/IntelliJ, you may simply import "as a Maven Project" into your IDE, deploy the project to your IDE's embedded servlet container and define your application server following steps for [Eclipse](http://help.eclipse.org/kepler/index.jsp?topic=%2Forg.eclipse.jst.server.ui.doc.user%2Ftopics%2Ftomcat.html) or [IntelliJ](https://www.jetbrains.com/help/idea/defining-application-servers-in-intellij-idea.html).


