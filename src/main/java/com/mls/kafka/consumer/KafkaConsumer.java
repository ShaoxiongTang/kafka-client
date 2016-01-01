package com.mls.kafka.consumer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;
import kafka.message.MessageAndMetadata;

import com.mls.kafka.util.ConfigUtil;

public class KafkaConsumer {
	private String groupid;
	private String location;
	private String topic;
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
		ConsumerConfig config = new ConsumerConfig(properties);
		connector = Consumer.createJavaConsumerConnector(config);

		Map<String, Integer> topics = new HashMap<String, Integer>();
		topics.put(topic, consumerZookeeper.partitiomMap.get(topic).size());
		Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(topics);
		List<KafkaStream<byte[], byte[]>> partitions = streams.get(topic);
		threadPool = Executors.newFixedThreadPool(consumerZookeeper.partitiomMap.get(topic).size() * workerCountPerPartition);

		for (KafkaStream<byte[], byte[]> partition : partitions) {
			threadPool.execute(new MessageRunner(partition));
		}
	}

	
	class MessageRunner implements Runnable {
		private KafkaStream<byte[], byte[]> partition;

		MessageRunner(KafkaStream<byte[], byte[]> partition) {
			this.partition = partition;
		}

		public void run() {
			ConsumerIterator<byte[], byte[]> it = partition.iterator();
			while (it.hasNext()) {
				// connector.commitOffsets();手动提交offset,当autocommit.enable=false时使用
				MessageAndMetadata<byte[], byte[]> item = it.next();
				try {
					executor.execute(new String(item.message(), consumerZookeeper.charset));// UTF-8,注意异常
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