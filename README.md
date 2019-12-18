# notespace

Notebook experience in your Clojure namespace

[![Clojars Project](https://img.shields.io/clojars/v/scicloj/notespace.svg)](https://clojars.org/scicloj/notespace)

# What does it mean?
Stay tuned -- a screencast is coming soon.

[Example](https://pycloj.github.io/libpython-clj-examples/using-1.28/resources/public/basic-tests/keras-test/index.html)

# Status
Experimental

# Goals

* Use any Clojure namespace, in any Clojure editor, as a notebook
  * [x] editing is done in your beloved editor
  * [x] rendering is done at the browser
  * [ ] typical workflow has almost zero latency in namespace evaluation
* Live reload experience
  * [ ] re-rendering of relevant notebook part(s)
* Everything can be a note
  * [x] function definition
  * [ ] test
  * [x] code example
  * [x] data analysis
* But nothing *has* to be a note
* Different modes of refresh
  * [ ] Oz-like refresh-downwards-from-this-note
  * [x] refresh-this-note
  * [x] refresh-everything

## License

Copyright © 2019 Scicloj

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.