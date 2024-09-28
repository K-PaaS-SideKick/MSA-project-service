//package kpaas.cumulonimbus.kpaas_project_service.kafka;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class KafkaRollback {
//
//    @KafkaListener(topics = "주문", groupId = "sidekick")
//    public void listen(ConsumerRecord<String, String> record) {
//        System.out.println(record.value());
//        log.info(record.toString());
//    }
//}
