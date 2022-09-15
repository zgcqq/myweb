package cn.chun.projs;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author chun
 * @date 2022/9/15
 */
public class MyWebKafkaApp {

    /**
     * 生产者线程类
     */
    static class ProducerThread extends Thread {
        private Logger subLogger = LoggerFactory.getLogger(ProducerThread.class);
        private String topic;
        private Properties props;
        public ProducerThread(
                String topic,
                Properties props
        ){
            this.topic = topic;
            this.props = props;
        }
        public void run(){
            String threadId = Long.toString(Thread.currentThread().getId());
            subLogger.info("ThreadId: " + threadId + ", 异步ProducerThread, BEGIN...");

            Producer<String, String> producer = new KafkaProducer<String, String>(props);
            for (int i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<String, String>(this.topic, Integer.toString(i), Integer.toString(i)));
                try {
                    Thread.sleep(3000);
                } catch (Exception e){

                }
            }
            producer.close();

            subLogger.info("ThreadId: " + threadId + ", 异步ProducerThread, END.");
        }
    }

    /**
     * 消费者线程类
     */
    static class ConsumerThread extends Thread {
        private Logger subLogger = LoggerFactory.getLogger(ConsumerThread.class);
        private String topic;
        private Properties props;
        public ConsumerThread(
                String topic,
                Properties props
        ){
            this.topic = topic;
            this.props = props;
        }
        public void run(){
            String threadId = Long.toString(Thread.currentThread().getId());
            subLogger.info("ThreadId: " + threadId + ", 异步ConsumerThread, BEGIN...");

            props.put("group.id", "testGroup");

            KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
            TopicPartition topicPartition = new TopicPartition(this.topic,0);
            long offset = 0L;
            ArrayList<TopicPartition> topicPartitionList = new ArrayList<TopicPartition>();
            topicPartitionList.add(topicPartition);
//            consumer.seek(topicPartition, offset);
            ArrayList<String> topicList = new ArrayList<String>();
            topicList.add(this.topic);
            consumer.subscribe(topicList);
            consumer.beginningOffsets(topicPartitionList);

            int cnt = 100;
            while(cnt > 0){
                ConsumerRecords<String,String> consumerRecords = consumer.poll(1000);
                int getCnt = consumerRecords.count();
                subLogger.info(Integer.toString(getCnt));
                for(ConsumerRecord item:consumerRecords){
                    subLogger.info(Integer.toString(cnt) +": "+JSON.toJSONString(item));
                }
                cnt--;
            }
            consumer.close();

            subLogger.info("ThreadId: " + threadId + ", 异步ConsumerThread, END.");
        }
    }

    public static void main( String[] args ) {
        Logger logger = LoggerFactory.getLogger(MyWebKafkaApp.class);
        System.out.println("main....");
        Config config = new Config();
        Properties props = config.getProps();

        String topicName = "chunTopic1";

        // 生产
        ProducerThread producerThread = new ProducerThread(topicName, props);
        producerThread.start();
        // 消费
        ConsumerThread consumerThread = new ConsumerThread(topicName, props);
        consumerThread.start();

        logger.info("config props: " + JSON.toJSONString(props.toString()));
    }
}
