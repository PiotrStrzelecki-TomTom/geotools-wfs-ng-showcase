#!/bin/bash

foo () {
  curl -s "http://localhost:8181/getFeatures/sf:archsites/Polygon%20((587286%204923665,%20587235%204918284,%20595897%204917979,%20595833%204923576,%20587286%204923665))" > /dev/null
  curl -s "http://localhost:8181/getFeatures/sf:bugsites/Polygon%20((587286%204923665,%20587235%204918284,%20595897%204917979,%20595833%204923576,%20587286%204923665))" > /dev/null
}

for j in {1..1000}; do

  # run processes and store pids in array
  for i in {1..50}; do
      foo $i &
      pids[$i]=$!
  done

  # wait for all pids
  for pid in ${pids[*]}; do
      wait $pid
  done

  echo iteration $j

done
