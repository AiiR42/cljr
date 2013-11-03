(defproject cljr "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1896"]
                 [ring "1.1.8"]
                 [compojure "1.1.5"]
                 [lib-noir "0.6.6"]
                 [jayq "2.3.0"]
                 [enfocus "1.0.1"]]
  :plugins [[lein-cljsbuild "0.3.2"]
            [lein-ring "0.8.3"]]
  :source-paths ["src/clj"]
  :cljsbuild { 
    :builds {
      :main {
        :source-paths ["src/cljs"]
        :compiler {:output-to "resources/public/js/cljs.js"
                   :optimizations :simple
                   :pretty-print true}
        :jar true}}}
  :main cljr.server
  :ring {:handler cljr.server/app})

