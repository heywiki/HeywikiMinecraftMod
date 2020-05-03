package org.wikiwizard.papermc;

import org.bukkit.block.Block;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class MiscUtil {

    /**
     * Useful to mask password output:
     * 
     * MiscUtil.stars(password.length())
     * 
     * @param chars
     * @param times
     * @return
     */
    public static final String stars(int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            s.append("*");
        }
        return s.toString();
    }
    
    /**
     * Mainly to debug and explore data,
     * but can also be used for transfer
     * 
     * Using json-simple (fast)
     * check also https://github.com/jdereg/json-io
     * 
     * @param block
     * @return
     */
    public static JSONObject getJSON(Block block) {
    	
    	JSONObject obj = new JSONObject();
    	JSONObject material = new JSONObject();
    	obj.put("material", material);

    	material.put("namespace", 
    			block.getBlockData().getMaterial().getKey().getNamespace());
    	material.put("namespaceKey", 
    			block.getBlockData().getMaterial().getKey().getKey());
    	
    	return obj;
    }
    
    /**
     * PrettyPrint json object
     * 
     * @param json
     * @return
     */
    public static String prettyPrint(JSONObject json) {
    	if (json == null) return null;
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	JsonParser jp = new JsonParser();
    	JsonElement je = jp.parse(json.toJSONString());
    	return gson.toJson(je);
    }
}