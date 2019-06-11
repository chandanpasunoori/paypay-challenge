# Web Analytical System Design

## Requirements
### Functionality
  * have the ability to reprocess historical data in case of bugs in the processing logic
  * provide metrics to customers with at most one hour delay.
  * Javascript agents (JS, Angular, React, etc.) to automatically sending events from websites
  * Millions merchants want to get insight about their business.
  * Read/Query patterns are time-series related metrics.
### Infrastructure
  * run with minimum downtime.
  * handle large write/query volume: Billions write events per day.
  * horizontal scalability
  * multi data center availability
  * Edge CDN with flexible purging options
  * Load Balancer with flexible switch over

## Observations
  For to support high volume of write events without performance effect on client websites, request size should
  minimal and persistent connection will help a lot.
  
   
  
## JS Agents

  We can choose between GRPC Web or **Websockets** for js agents, with **protocol buffers**( we can avoid json generation, 
  serializing, parsing.
  
  protocol buffers will give good backward compatibility in case of any schema changes and its binary encoding will 
  reduce the size of request
  
## Backend
  Opinion: *While I'm good at Java and Scala, I would like to choose Golang or Rustlang for implementing this
  for low resource requirement (memory), as we need to scale horizontally with best ROI.*

  GRPC with two streams, will enable us to get requests and responding asynchronously, this will reduce latency
  between clients and servers.
  
  **GRPC** does not have built it request caching(anyway we don't require in incoming requests), because it http2 with POST, 
  so we will use this for client agents and servers and between internal service to service communication, we will go 
  with micro service architecture.
  
  **Kubernetes** is with **Istio** service mess will help us in many ways, like 
    
   * live and canary deployments
   * version based routing
   * tracing with **zipkin**, **jaeger** or **opentracing**
   * metric collection by **prometheus**, **grafana**
   * **service graph**
   
  Data layer is critical for this kind of workloads, and use cases focused on time series data so we can
  use **columnar databases**, as clients will have access to only their data, so we can **partition data by merchant id, date**.
  
  As we need to support high volume of incoming events and also unexpected spikes, we should push all request from 
  agents to **Kafka (properly configured no of shards by merchant id and data, with at least replication factor 3)**, on 
  consumer side we ingest data into **cassandra**(hotspot/hot nodes should be taken care if we do partition by time).
  We should separate write heavy tables and read heavy tables to support multi million active users, because we 
  data partition by merchant id and time, cassandra will handle traffic if it properly scaled with optimized replication
  factor and strategy for multi data center. **Spark/Hadoop/Apache Beam** can be used for analysis workloads, but spark will 
  be faster, we can use windowing strategies to work with time series data, data can be aggregated to seconds, minutes, 
  hours from origin events to support for longer time (10+ years of historical data) with better ROI. 
  
  In any analytical use cases sub second precision is not much preferred, so if we aggregate to seconds or minutes 
  based on use cases, we can actually reduce storage size to very reasonable amount.     
  
  Because clients wants analyzed data and not realtime, we can aggressively cache data at edge cdn nodes by not more than 
  1 hour, if we have predefined analyzing configuration for each client, we can aggregate upfront and can cache so client 
  can access faster.
  
  In Kafka we need to use **avro** with **snappy** codec for better compression without performance/speed impact.
  
## Basic Features
  * oauth2 authentication
  * multi tenant support
  * request validation
  * WAF layer, circuit breakers to sustain DDOS, and wide range of http attacks
  
  
