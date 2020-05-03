package org.wikiwizard.papermc;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttManager implements CommandExecutor, MqttCallback {

	/** url to an mqtt server, e.g.
	 *  tcp://test.mosquitto.org:1883
	 */
	public static final String CONF_MQTT_SERVER = "mqtt-server";
	
	/** optional username: if username is set, password has to be set as well */
	public static final String CONF_MQTT_USERNAME = "mqtt-username";
	public static final String CONF_MQTT_PASSWORD = "mqtt-password";
	
	/** this is what gets set as topic on publish when you don't indicate
	 * it with mqttpub topic:message (so mqttpub message)
	 */
	public static final String CONF_MQTT_DEFAULT_TOPIC = "mqtt-default-topic";
	public static final String MQTT_DEFAULT_TOPIC = "heywiki/mqtt";
	
	/** Default quality of service a message is send */
	public static final int DEFAULT_QOS = 2;
	
	
	/** will send mqtt message to server configured in config.yml */
	public static String CMD_MQTTPUB = "mqttpub";
	
	/** displays the mqtt config in plugin.yml */
	public static String CMD_MQTTCONF = "mqttconf";
	
	HeywikiPlugin plugin;
	Logger log;
	
	MemoryPersistence persistence;
	MqttClient client;
	String defaultTopic;
	
	public MqttManager() {
		
		plugin = HeywikiPlugin.getPlugin(HeywikiPlugin.class);
		log = plugin.getLogger();
		
	    persistence = new MemoryPersistence();
		
	    FileConfiguration config = plugin.getConfig();
	    
		defaultTopic = config.getString(CONF_MQTT_DEFAULT_TOPIC);
	    if (defaultTopic == null) defaultTopic = MQTT_DEFAULT_TOPIC;
		
        try {
			client = new MqttClient(
					config.getString(CONF_MQTT_SERVER), 
					"standard", 
					persistence);
	        client.setCallback(this);
	        
	        MqttConnectOptions conOpts = new MqttConnectOptions();
	        conOpts.setCleanSession(true);   
	        
	        if (config.getString(CONF_MQTT_USERNAME) != null) {
	            conOpts.setUserName(config.getString(CONF_MQTT_USERNAME));
	            conOpts.setPassword(
	            		config.getString(CONF_MQTT_PASSWORD).toCharArray());
	        }
	        
	        log.info("Connecting mqtt client...");
	        client.connect(conOpts);
	        log.info("Connecting successful");
	        
        } catch (MqttException e) {
        	log.severe("Unable to connect mqtt client at "
        			+ config.getString("mqtt-server") + ":" + e);
        }
	}
	
	public void onDisable() {
		if (client == null) return;
		try {
			client.disconnect();
		} catch (Exception e) {
        	log.severe("Unable to disconnect mqtt client at "
        			+ plugin.getConfig().getString("mqtt-server") + ":" + e);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, 
			String label, String[] args) {

		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		
		
		if (command.getName().contentEquals(CMD_MQTTPUB)) {
			
			if (args.length < 1) {
				player.sendMessage("message required");
				return false;
			}

			String topic = getDefaultTopic();
			StringBuilder message = new StringBuilder();
			for (String s : args) {
				if (s.startsWith("topic:")) {
					topic = s.replaceFirst("topic:", "");
				}
				else {
					message.append(s).append(" ");
				}
			}
			
			player.sendMessage("publish " + topic + ":" + message);
			if (!publish(topic, message.toString())) {
				//admin must check logs
				player.sendMessage("There is something wrong, message could not be send.");				
			}
			
			return true;
		}	
		else if (command.getName().contentEquals(CMD_MQTTCONF)) {
			
			FileConfiguration config = plugin.getConfig();
			StringBuilder buf = new StringBuilder();
			buf.append(CONF_MQTT_SERVER + ": " 
					+ config.getString(CONF_MQTT_SERVER) + "\n");
			
			if (config.getString(CONF_MQTT_USERNAME) != null) {
				buf.append(CONF_MQTT_USERNAME + ": " 
						+ config.getString(CONF_MQTT_USERNAME) + "\n");
				buf.append(CONF_MQTT_PASSWORD + ": " + 
						MiscUtil.stars(config.getString(CONF_MQTT_PASSWORD).length())
						+ "\n");
			}
			
			player.sendMessage(buf.toString());
			return true;
		}

		return false;
	}

	/**
	 * Gets the default topic from puglin.yml or
	 * if not set from this class
	 * 
	 * @return
	 */
	public String getDefaultTopic() {
		return defaultTopic;
	}
	
	/**
	 * Publishes a mqtt message
	 * 
	 * @param topic
	 * @param message
	 * @return
	 */
	public boolean publish(String topic, String message) {
		
        MqttMessage mqttMsg = new MqttMessage(message.toString().getBytes());
        mqttMsg.setQos(DEFAULT_QOS);
        
        try {
            client.publish(topic, mqttMsg);
            log.info("SUCCESS: published to topic " + topic);
            return true;
		} catch (Exception e) {
			log.severe("ERR: unable to publish, exception is " + e);
			return false;
		}

	}
	
	/**
	 * Publishes message with default topic
	 */
	public boolean publish(String message) {
		return publish(getDefaultTopic(), message);
	}
	
	@Override
	public void connectionLost(Throwable cause) {

		//Bukkit.broadcastMessage(message);
		 plugin.getLogger().info("Mqtt connecting lost to server " + 
				 plugin.getConfig().getString(CONF_MQTT_SERVER));
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
}