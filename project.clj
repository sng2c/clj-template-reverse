(defproject template-reverse "0.1.0-SNAPSHOT"
  :description "A template generator getting different parts between pair of text"
  :url "https://metacpan.org/pod/Template::Reverse"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
  					[org.clojure/clojure "1.5.1"] 
  					[com.googlecode.java-diff-utils/diffutils "1.2.1"]
				]
  :main ^:skip-aot template-reverse.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
