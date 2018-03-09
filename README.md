# esunconsulting.clj
[![Open Source Love](https://badges.frapsoft.com/os/v3/open-source.svg?v=103)](https://github.com/coldnew/esunconsulting.clj)
[![AGPL License](http://img.shields.io/badge/license-AGPL%20v3-red.svg?style=flat)](http://opensource.org/licenses/AGPL-3.0)
[![Circle CI](https://circleci.com/gh/coldnew/esunconsulting.clj.svg?style=svg)](https://circleci.com/gh/coldnew/esunconsulting.clj)

A Clojure library designed to fetch report from [Esunconsulting](https://www.esunconsulting.com.tw/all_reports.asp) and send to telegram group.

[![Clojars Project](https://clojars.org/coldnew/left-pad/latest-version.svg)](http://clojars.org/coldnew/esunsonsulting)

[Latest codox API docs](https://coldnew.github.io/esunconsulting.clj/).

## Usage

```clojure
(ns esunconsulting-test.core
  (:require [esunconsulting :refer [get-report]))

;; get reports map from page 1
(get-report 1)
;; => [{:title "AAA" :link "http:/bbb.pdf" :date "20180309"} {:title "CCC" :link "http:/ddd.pdf" :date "20180309"}]

```
## Examples

- [esunconsulting-bot](https://github.com/coldnew/esunconsulting-bot)

  A real-life example use this library to fetch reports from [Esunconsulting](https://www.esunconsulting.com.tw/all_reports.asp) and send to telegram group.

## License

Copyright © 2018 Yen-Chin, Lee <<coldnew.tw@gmail.com>>

Distributed under the [GNU Affero General Public License 3.0 (AGPL-3.0)](https://www.gnu.org/licenses/agpl-3.0.en.html).
