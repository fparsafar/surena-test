//package ir.surena.sample.serviceImpl;
//
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//@RequiredArgsConstructor
//public class AsyncService {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    private final RestTemplate restTemplate;
//
////    @Async("asyncExecutor")
////    public CompletableFuture<List<User>> getComments() {
////        log.info("getComments starts");
////        StopWatch stopWatch = new StopWatch();
////        stopWatch.start();
////        CommentDto[] comments = restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments", CommentDto[].class);
////        stopWatch.stop();
////        log.info("comments completed , took:{} ms", stopWatch.getTotalTimeMillis());
////        return CompletableFuture.completedFuture(Objects.nonNull(comments) ? Arrays.asList(comments) : new ArrayList<>());
////    }
////
//}