package com.mls.kafka.consumer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.mls.kafka.demo.MerchantMsg;
import com.mls.kafka.util.ConfigUtil;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

public class KafkaConsumer {
	private String groupid;
	private String location;
	private String topic = "merchantaccount2";
	private int workerCountPerPartition = 5;
	private MessageExecutor<?> executor;

	private ExecutorService threadPool;
	private ConsumerConnector connector;
	private ConsumerZookeeper consumerZookeeper;

	public KafkaConsumer() {
	}

	public void init() throws Exception {
		if (executor == null) {
			throw new RuntimeException("KafkaConsumer, exectuor cant be null!");
		}
		Properties properties = ConfigUtil.loadProperties(location);
		if (groupid != null) {
			properties.put("groupid", groupid);
		}
		properties.put("consumer.id", "2");
		ConsumerConfig config = new ConsumerConfig(properties);
		connector = Consumer.createJavaConsumerConnector(config);

		Map<String, Integer> topics = new HashMap<String, Integer>();
		// topics.put(topic, consumerZookeeper.partitiomMap.get(topic).size());
		topics.put(topic, 3);
		StringDecoder decoder = new StringDecoder(new VerifiableProperties());
		Map<String, List<KafkaStream<String, String>>> streams = connector.createMessageStreams(topics, decoder, decoder);
		List<KafkaStream<String, String>> partitions = streams.get(topic);
		// threadPool =
		// Executors.newFixedThreadPool(consumerZookeeper.partitiomMap.get(topic).size()
		// * workerCountPerPartition);
		threadPool = Executors.newFixedThreadPool(3 * 1);

		for (KafkaStream<String, String> partition : partitions) {
			threadPool.execute(new MessageRunner(partition));
		}
	}

	public static void main(String[] args) throws Exception {
		KafkaConsumer consumer = new KafkaConsumer();
		consumer.setExecutor(new MessageExecutor<MerchantMsg>() {
			public MerchantMsg execute(String message) {
				System.out.println(message);
				return null;
			}
		});
		consumer.topic = "merchantaccount2";
		consumer.location = "kafaka-consumer.properties";
		consumer.init();
		consumer.threadPool.awaitTermination(100000, TimeUnit.MINUTES);
	}

	class MessageRunner implements Runnable {
		private KafkaStream<String, String> partition;

		MessageRunner(KafkaStream<String, String> partition) {
			this.partition = partition;
		}

		public void run() {
			ConsumerIterator<String, String> it = partition.iterator();
			while (it.hasNext()) {
				// connector.commitOffsets();手动提交offset,当autocommit.enable=false时使用
				MessageAndMetadata<String, String> item = it.next();
				try {
					executor.execute(item.message());// UTF-8,注意异常
				} catch (Exception e) {
					//
				}
			}
		}

		public String getContent(Message message) {
			ByteBuffer buffer = message.payload();
			if (buffer.remaining() == 0) {
				return null;
			}
			CharBuffer charBuffer = consumerZookeeper.charset.decode(buffer);
			return charBuffer.toString();
		}
	}

	public void close() {
		try {
			threadPool.shutdownNow();
		} catch (Exception e) {

		} finally {
			connector.shutdown();
		}

	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setExecutor(MessageExecutor<?> executor) {
		this.executor = executor;
	}
}