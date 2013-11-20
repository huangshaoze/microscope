//************** trace related structs **************//

namespace java com.vipshop.microscope.thrift

// record system status
struct EndPoint {
   1: map<string, string> values
}

// annotation type
enum AnnotationType { CS, CR, SS, SR, KV }

// annotation means some kind event
struct Annotation {
  1: i64 timestamp                 // microseconds from epoch
  2: EndPoint endPoint,                     
  3: AnnotationType type           // event type?
}

// span means some kind method
struct Span {
  1: string app_name,                           // message head
  2: i64 trace_id                               // unique trace id, use for all spans in trace
  4: i64 id,                                    // unique span id, only used for this span
  5: optional i64 parent_id,                    // parent span id
  6: string name,                               // span name, rpc method for example
  7: string type                               // span type
  8: list<Annotation> annotations,              // list of all annotations/events that occured
  9: optional bool debug = 0,                   // if true, we DEMAND that this span passes all samplers
  10: i32 duration                              // how long did it take?
  11: i64 startstamp                            // start timestamp
  12: string resultCode                         // result
  13: optional string IPAddress                 // remote ip address
  14: optional string serverIP	                // romote server ip
  15: i32 contentSize                           // /request/response body size
}

