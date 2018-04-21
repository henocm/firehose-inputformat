# firehose-inputformat
input format and record reader to correctly read records in JSON files produced by AWS Kinesis FireHose

### BUILD
```mkdir build 
javac  -classpath $(hadoop classpath) -sourcepath src/ -d build src/com/henoc/mapreduce/*.java
jar cf FireHoseTextInputFormat.jar -C build/ .
```

### Example Usage

```hive> ADD JAR s3://<mybucket>/path/to/FireHoseTextInputFormat.jar;
Added [/mnt/tmp/e72d38f0-9012-415a-afe3-cbc70f6ce7aa_resources/FireHoseTextInputFormat.jar] to class path
Added resources: [s3://<mybucket>/path/to/FireHoseTextInputFormat.jar]

hive> CREATE EXTERNAL TABLE `firehose_records`(
    >   `field` string)
    > ROW FORMAT SERDE 
    >   'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe' 
    > STORED AS INPUTFORMAT 
    >   'com.henoc.mapreduce.FireHoseTextInputFormat' 
    > OUTPUTFORMAT 
    >   'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
    > LOCATION
    >   's3://<mybucket>/path/to/firehosefiles';
OK
Time taken: 0.305 seconds

hive> SELECT * FROM firehose_records LIMIT 10;
OK
{"ticker_symbol":"QXZ","sector":"RETAIL","change":-1.24,"price":50.91}
{"ticker_symbol":"UHN","sector":"FINANCIAL","change":12.15,"price":556.12}
{"ticker_symbol":"BNM","sector":"TECHNOLOGY","change":-1.55,"price":174.61}
{"ticker_symbol":"JKL","sector":"TECHNOLOGY","change":0.11,"price":15.21}
{"ticker_symbol":"TBV","sector":"HEALTHCARE","change":4.45,"price":195.45}
{"ticker_symbol":"XTC","sector":"HEALTHCARE","change":6.71,"price":113.75}
{"ticker_symbol":"WFC","sector":"FINANCIAL","change":-0.9,"price":45.89}
{"ticker_symbol":"BFH","sector":"RETAIL","change":-0.1,"price":16.77}
{"ticker_symbol":"CRM","sector":"HEALTHCARE","change":-1.48,"price":28.14}
{"ticker_symbol":"PLM","sector":"FINANCIAL","change":0.08,"price":19.23}
Time taken: 1.432 seconds, Fetched: 10 row(s)

hive> SELECT COUNT(*) FROM firehose_records;
Query ID = hadoop_20180419043753_13e7816b-4eb6-4a64-a4d6-f7fb541240e3
Total jobs = 1
Launching Job 1 out of 1
Tez session was closed. Reopening...
Session re-established.
Status: Running (Executing on YARN cluster with App id application_1521008084692_0039)

----------------------------------------------------------------------------------------------
        VERTICES      MODE        STATUS  TOTAL  COMPLETED  RUNNING  PENDING  FAILED  KILLED  
----------------------------------------------------------------------------------------------
Map 1 .......... container     SUCCEEDED      1          1        0        0       0       0  
Reducer 2 ...... container     SUCCEEDED      1          1        0        0       0       0  
----------------------------------------------------------------------------------------------
VERTICES: 02/02  [==========================>>] 100%  ELAPSED TIME: 9.41 s     
----------------------------------------------------------------------------------------------
OK
560
Time taken: 16.818 seconds, Fetched: 1 row(s)
```
